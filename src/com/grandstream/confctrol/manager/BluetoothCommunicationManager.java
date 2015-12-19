package com.grandstream.confctrol.manager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.grandstream.confctrol.BluetoothHandingService;
import com.grandstream.confctrol.R;
import com.grandstream.confctrol.entity.DeviceInfo;
import com.grandstream.confctrol.function.ConnectStateChangeListener;
import com.grandstream.confctrol.function.DeviceFind;
import com.grandstream.confctrol.utils.Assert;
import com.grandstream.confctrol.utils.Constants;
import com.grandstream.confctrol.utils.LogUtils;
import com.grandstream.confctrol.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhyjiang on 12/16/15.
 */
public class BluetoothCommunicationManager {

    private final String TAG = "BluetoothCommunicationManager";

    static  private BluetoothCommunicationManager mBtMngr = null;
    static  private Context mContext;

    private BluetoothHandingService mChatService;
    private BluetoothAdapter mBTAdapter;
    private BluetoothDevice mDevice;
    private String mAddress = "";
    private DeviceInfo mDeviceInfo;

    private List<ConnectStateChangeListener> mListeners;
    private List<DeviceFind> mDeviceFinds;
    private BtScanReciver mBtScanReciver;
    private boolean isConnect = false;
    private boolean isConnecting = false;

    //prepare to connect
    public static final int CONNECT_PRE = 0x00000100;

    //connect doing
    public static final int CONNECT_DOING_START = 0x00000101;

    //connect doing start
    public static final int CONNECT_DOING_END = 0x00000102;

    //finish connect end
    public static final int CONNECT_DONE = 0x00000103;

    //unable to connect
    public static final int CONNECT_FAIL = 0x00000104;

    //the connection is lost
    public static final int CONNECT_LOST = 0x00000105;

    //disconnect
    public static final int CONNECT_DISCONNECTED = 0x00000106;

    //connect none
    public static final int CONNECT_NONE = 0x00000107;


    /**
     * Bluetooth open state
     */
    protected static final int ACTION_STATE_CHANGED = 0x0011;

    /**
     * Bluetooth find state
     */
    protected static final int ACTION_FOUND = 0x0012;

    /**
     * Bluetooth local connect state
     */
    protected static final int ACTION_CONNECTION_STATE_CHANGED = 0x0013;
    /**
     * Bluetooth local bond state
     */
    protected static final int ACTION_BOND_STATE_CHANGED = 0x0014;
    /**
     * Bluetooth real connect state connected
     */
    protected static final int ACTION_ACL_CONNECTED = 0x0015;
    /**
     * Bluetooth real connect state disconnected
     */
    protected static final int ACTION_ACL_DISCONNECTED = 0x0016;
    /**
     * Bluetooth real pair state cancel
     */
    protected static final int ACTION_PAIR_CANCEL = 0x0017;
    /**
     * Bluetooth scan start
     */
    protected static final int ACTION_SCAN_START = 0x0018;
    /**
     * Bluetooth scan finish
     */
    protected static final int ACTION_SCAN_FINISH = 0x0019;
    /**
     * remote device rename
     */
    protected static final int ACTION_NAME_CHANGE = 0X0020;


    static public void setContext(Context context ) {
        if (mContext == null){
            mContext = context;
        } else {
            throw new RuntimeException(" BluetoothCommunicationManager context has been assigned already !! ");
        }
    }

    static public BluetoothCommunicationManager instance() {
        if (mContext == null) {
            throw new RuntimeException(" BluetoothCommunicationManager context has not been assigned yet !!");
        }

        if (mBtMngr == null) {
            mBtMngr = new BluetoothCommunicationManager();
        }

        return mBtMngr;
    }

    public boolean ensureBound(String address) {
        Assert.debug(address);
        BluetoothDevice device  = mBTAdapter.getRemoteDevice(address);
        return ensureBound(device);
    }

    public boolean ensureBound(BluetoothDevice device) {
        Assert.debug(device);
        int bondState = device.getBondState();
        switch (bondState){
            case BluetoothDevice.BOND_NONE:
                return device.createBond();
            case BluetoothDevice.BOND_BONDED:
                return true;
        }
        return false;
    }


    /**
     * connect to a bluetooth device
     *
     * @param address bluetooth address void
     */
    public void connect(String address) {
        if (TextUtils.isEmpty(address) || !BluetoothAdapter.checkBluetoothAddress(address)) {
            throw new RuntimeException(" BluetoothCommunicationManager" +
                    "  connect address is : [ " + address  + " ] invald !! ");
        }
        mAddress = address;
        if (enableLocalBluetooth()) {
            mDevice  = mBTAdapter.getRemoteDevice(address);
            connect(mDevice);
        } else {
//            CustomMessage.showToast(mContext, mContext.getString(R.string.hint_bluetooth_dont_opened), Gravity.CENTER, 0);
            ToastUtils.showToast(mContext, R.string.toast_bluetooth_dont_opened);
        }
    }


    /**
     * @param data void
     */
    public boolean sendData(byte[] data) {
        LogUtils.printLog(TAG, "==============sendData()===========");
        LogUtils.printLog(TAG, "sendData()    isConnect= " + isConnect);
        LogUtils.printLog(TAG, "sendData()    isConnecting= " + isConnecting);

        if (mBTAdapter == null) {
            LogUtils.printLog(TAG, "sendData()    mBTAdapter==null ");
//            CustomMessage.showToast(mContext, mContext.getString(R.string.hint_dont_support_bluetooth), Gravity.CENTER, 0);
            ToastUtils.showToast(mContext, R.string.toast_dont_support_bluetooth);
            return false;
        }

        if (!mBTAdapter.isEnabled()) {
            LogUtils.printLog(TAG, "sendData()    !mBTAdapter.isEnabled() ");
//            CustomMessage.showToast(mContext, mContext.getString(R.string.hint_bluetooth_openning), Gravity.CENTER, 0);
            ToastUtils.showToast(mContext, R.string.toast_bluetooth_openning);
            LogUtils.printLog(TAG, "sendData()    mBTAdapter.getState()=" + mBTAdapter.getState());
            if (mBTAdapter.getState() == BluetoothAdapter.STATE_OFF)
                mBTAdapter.enable();
            return false;
        }

        if (!isConnect) {
            LogUtils.printLog(TAG, "sendData()    !isConnect ");
            if (!TextUtils.isEmpty(mAddress) && !isConnecting) {
                LogUtils.printLog(TAG, "sendData()   mDevice!=null&&!isConnecting ");
                mDevice = mBTAdapter.getRemoteDevice(mAddress);
                if (mBTAdapter.isEnabled()) {
                    LogUtils.printLog(TAG, "sendData()   mBTAdapter.isEnabled() ");
                    mChatService.connect(mDevice, true);
                }
            }
            return false;
        }

        LogUtils.printLog(TAG, "sendData()    write ...  ");
        mChatService.write(data);
        return true;
    }

    /**
     * @param str void
     */
    public boolean sendData(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return sendData(str.getBytes());
    }

    /**
     * shut down bluetooth connect
     * <p/>
     * void
     */
    public void disconnect() {

        if (mDevice != null) {
            mDevice = null;
        }
        if (mChatService != null) {
            mChatService.stop();
        }
        isConnect = false;
        sendBtMessage(CONNECT_DISCONNECTED, null);
    }


    private void connect(BluetoothDevice device) {
        mChatService.connect(device, true);
    }

    /**
     * open bluetooth device
     *
     * @return boolean success is true
     */
    private boolean enableLocalBluetooth() {
        boolean flag = false;
        if (null != mBTAdapter) {
            if (!mBTAdapter.isEnabled()) {
                if (mBTAdapter.getState() == BluetoothAdapter.STATE_OFF) {
                    flag = mBTAdapter.enable();
                }
            } else {
                flag = true;
            }
        }
        return flag;
    }

    private BluetoothCommunicationManager() {
        mChatService = new BluetoothHandingService(mContext, mHandler);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        mListeners = new ArrayList<ConnectStateChangeListener>();
        mDeviceFinds = new ArrayList<DeviceFind>();
        registBluetoothReciver();
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    LogUtils.printLog(TAG, "handleMessage()    =====MESSAGE_STATE_CHANGE==== ");
                    isConnect = false;
                    switch (msg.arg1) {
                        case BluetoothHandingService.STATE_CONNECTED:
                            LogUtils.printLog(TAG, "handleMessage()    STATE_CONNECTED ");
                            isConnect = true;
                            isConnecting = false;
                            if (mDevice != null) {
                                mDeviceInfo = new DeviceInfo();
                                mDeviceInfo.mDeviceName = mDevice.getName();
                                mDeviceInfo.IfEnable = true;
                                mDeviceInfo.mState = mDevice.getBondState();
                                mDeviceInfo.mMacAddress = mDevice.getAddress();
//                                StateDataHelper.keepLastDeviceInfo(mContext, mAddress);
//                                StateDataHelper.keepLastDeviceName(mContext, mDeviceInfo.mDeviceName);
                                sendBtMessage(CONNECT_DONE, isConnect);
                            }
                            break;
                        case BluetoothHandingService.STATE_CONNECTING:
                            LogUtils.printLog(TAG, "handleMessage()    STATE_CONNECTING ");
                            isConnecting = true;
                            sendBtMessage(CONNECT_PRE, null);
                            break;
                        case BluetoothHandingService.STATE_LISTEN:
                            LogUtils.printLog(TAG, "handleMessage()    STATE_LISTEN ");
                            break;
                        case BluetoothHandingService.STATE_NONE:
                            LogUtils.printLog(TAG, "handleMessage()    STATE_NONE ");
                            isConnect = false;
                            isConnecting = false;
                            sendBtMessage(CONNECT_NONE, null);
                            break;
                        case BluetoothHandingService.STATE_CONNECT_FAIL:
                            LogUtils.printLog(TAG, "handleMessage()    STATE_CONNECT_FAIL ");
                            isConnect = false;
                            isConnecting = false;
                            sendBtMessage(CONNECT_FAIL, null);
                            break;
                        case BluetoothHandingService.STATE_CONNECTED_LOST:
                            LogUtils.printLog("BTR", "handleMessage()    STATE_CONNECTED_LOST ");
                            isConnect = false;
                            isConnecting = false;
                            sendBtMessage(CONNECT_LOST, null);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    LogUtils.printLog(TAG, "handleMessage()    MESSAGE_WRITE ");
                    break;
                case Constants.MESSAGE_READ:
                    LogUtils.printLog(TAG, "handleMessage()    MESSAGE_READ ");
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    LogUtils.printLog(TAG, "handleMessage()    MESSAGE_DEVICE_NAME ");
                    break;
                case Constants.MESSAGE_TOAST:
                    LogUtils.printLog(TAG, "handleMessage()    MESSAGE_TOAST ");
                    break;
            }
        }
    };


    public boolean startDiscovery() {
        boolean enable =  enableLocalBluetooth();
        if (enable){
            mBTAdapter.startDiscovery();
        }
        return enable;
    }

    public void stopDiscovery(){
        if (mBTAdapter.isDiscovering()){
            mBTAdapter.cancelDiscovery();
        }
    }


    public void setListeners(ConnectStateChangeListener connectStateChangeListener){
        if (connectStateChangeListener == null){
            return;
        }
        if (!mListeners.contains(connectStateChangeListener)){
            mListeners.add(connectStateChangeListener);
        }
    }

    public void removeListener(ConnectStateChangeListener connectStateChangeListener){
        if (connectStateChangeListener == null){
            return;
        }
        if (mListeners.contains(connectStateChangeListener)){
            mListeners.remove(connectStateChangeListener);
        }
    }

    public void setDeviceFinders(DeviceFind deviceFind){
        if (deviceFind == null){
            return;
        }
        if (!mDeviceFinds.contains(deviceFind)){
            mDeviceFinds.add(deviceFind);
        }
    }

    public void removeFinder(DeviceFind deviceFind){
        if (deviceFind == null){
            return;
        }
        if (mDeviceFinds.contains(deviceFind)){
            mDeviceFinds.remove(deviceFind);
        }
    }

    private void deviceFinderSendMessage(int action,  DeviceInfo deviceInfo){
        for (DeviceFind deviceFind:mDeviceFinds){
            if (action == ACTION_FOUND){
                deviceFind.onDeviceFind(deviceInfo);
            } else {
                // to do
            }
        }
    }

    /**
     * send a message to handle
     *
     * @param what message what
     * @param data message boj,is a Intent; void
     */
    private void sendBtMessage(int what, Object data) {
//        if (mHandlers != null && !mHandlers.isEmpty()) {
//            for (Handler handler : mHandlers) {
//                Message msg = handler.obtainMessage();
//                msg.what = what;
//                msg.obj = data;
//                handler.sendMessage(msg);
//            }
//        }

        for (ConnectStateChangeListener connectStateChangeListener:mListeners){
            if (connectStateChangeListener != null){
                connectStateChangeListener.onConnectStateChanged(what, data);
            }
        }
    }

    private void registBluetoothReciver() {
        mBtScanReciver = new BtScanReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction("android.bluetooth.device.action.PAIRING_CANCEL");
        filter.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
        mContext.registerReceiver(mBtScanReciver, filter);
    }


    class BtScanReciver extends BroadcastReceiver {

        /* (non-Javadoc)
         * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.printLog(TAG, "BtScanReciver        onReceive ");
            String action = intent.getAction();
            LogUtils.printLog(TAG, "onReceive   intent action= " + action);
            LogUtils.printLog(TAG, "onReceive   intent = " + intent);
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
//                sendMessage(ACTION_STATE_CHANGED, intent);
                deviceFinderSendMessage(ACTION_STATE_CHANGED, null);
            } else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                int blueconState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, 0);
                int prvState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE, 0);
                LogUtils.printLog(TAG, "BtScanReciver          prvState =" + prvState);
                LogUtils.printLog(TAG, "BtScanReciver          blueconState =" + blueconState);
//                sendMessage(ACTION_CONNECTION_STATE_CHANGED, intent);
                deviceFinderSendMessage(ACTION_CONNECTION_STATE_CHANGED, null);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                sendMessage(ACTION_FOUND, intent);
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device != null){
                    final DeviceInfo deviceInfo = new DeviceInfo();
                    deviceInfo.mDeviceName = device.getName();
                    deviceInfo.mMacAddress = device.getAddress();
                    deviceInfo.mState = device.getBondState();
                    int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
//                    deviceInfo.mRssi = Math.min(rssi, 0);
                    deviceInfo.mRssi = rssi;
                    deviceFinderSendMessage(ACTION_FOUND, deviceInfo);

                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
//                sendMessage(ACTION_BOND_STATE_CHANGED, intent);
                deviceFinderSendMessage(ACTION_BOND_STATE_CHANGED, null);
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                LogUtils.printLog(TAG, "BtScanReciver          ACTION_ACL_CONNECTED ");
                BluetoothDevice d = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                LogUtils.printLog(TAG, "BtScanReciver          BOND  state =" + d.getBondState());
//                sendMessage(ACTION_ACL_CONNECTED, intent);
                deviceFinderSendMessage(ACTION_ACL_CONNECTED, null);
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                LogUtils.printLog(TAG, "BtScanReciver          ACTION_ACL_DISCONNECTED ");
                BluetoothDevice d = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                LogUtils.printLog(TAG, "BtScanReciver          BOND  state =" + d.getBondState());
//                sendMessage(ACTION_ACL_DISCONNECTED, intent);
                deviceFinderSendMessage(ACTION_ACL_DISCONNECTED, null);
            } else if ("android.bluetooth.device.action.PAIRING_CANCEL".equals(action)) {
//                sendMessage(ACTION_PAIR_CANCEL, intent);
                deviceFinderSendMessage(ACTION_PAIR_CANCEL, null);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
//                sendMessage(ACTION_SCAN_START, intent);
                deviceFinderSendMessage(ACTION_SCAN_START, null);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//                sendMessage(ACTION_SCAN_FINISH, intent);
                deviceFinderSendMessage(ACTION_SCAN_FINISH, null);
            } else if (BluetoothDevice.ACTION_NAME_CHANGED.equals(action)) {
//                sendMessage(ACTION_NAME_CHANGE, intent);
                deviceFinderSendMessage(ACTION_NAME_CHANGE, null);
            }
        }

    }



}
