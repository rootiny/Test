package com.grandstream.confctrol.manager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.grandstream.confctrol.BluetoothHandingService;
import com.grandstream.confctrol.R;
import com.grandstream.confctrol.entity.BluetoothDeviceInfo;
import com.grandstream.confctrol.function.ConnectStateChangeListener;
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
    private BluetoothDeviceInfo mDeviceInfo;

    private List<ConnectStateChangeListener> mListeners;

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

    static public void setContext(Context context ) {
        if (mContext == null){
            mContext = context;
        } else {
            throw new RuntimeException(" BluetoothCommunicationManager context has been assigned already !! ");
        }
    }

    static public BluetoothCommunicationManager instance() {
        if (mContext == null){
            throw new RuntimeException(" BluetoothCommunicationManager context has not been assigned yet !!");
        }

        if (mBtMngr == null) {
            mBtMngr = new BluetoothCommunicationManager();
        }

        return mBtMngr;
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
            mDevice = mBTAdapter.getRemoteDevice(address);
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
                                mDeviceInfo = new BluetoothDeviceInfo();
                                mDeviceInfo.mDeviceName = mDevice.getName();
                                mDeviceInfo.mEnable = true;
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


    public void setListeners(ConnectStateChangeListener connectStateChangeListener){
        if (connectStateChangeListener == null){
            return;
        }
        if (!mListeners.contains(connectStateChangeListener)){
            mListeners.add(connectStateChangeListener);
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

}
