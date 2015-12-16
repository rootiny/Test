package com.grandstream.confctrol;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import com.grandstream.confctrol.entity.BluetoothDeviceInfo;
import com.grandstream.confctrol.manager.BluetoothCommunicationManager;
import com.grandstream.confctrol.utils.Constants;
import com.grandstream.confctrol.utils.LogUtils;

/**
 * Created by zhyjiang on 12/16/15.
 */
public class ConfTrolApplication extends Application {

    public static final String TAG = "ConfTrolApplication";

    private Context mContext;

    BluetoothCommunicationManager mBtMngr;

    /**
     * current connected device
     */
    public BluetoothDevice mDevice;


    /**
     * prepare to connect
     */
    public static final int CONNECT_PRE = 0x00000100;
    /**
     * connect doing
     */
    public static final int CONNECT_DOING_START = 0x00000101;
    /**
     * connect doing start
     */
    public static final int CONNECT_DOING_END = 0x00000102;
    /**
     * finish connect end
     */
    public static final int CONNECT_DONE = 0x00000103;

    /**
     * unable to connect
     */
    public static final int CONNECT_FAIL = 0x00000104;

    /**
     * the connection is lost
     */
    public static final int CONNECT_LOST = 0x00000105;

    /**
     * disconnect
     */
    public static final int CONNECT_DISCONNECTED = 0x00000106;

    /**
     * connect none
     */
    public static final int CONNECT_NONE = 0x00000107;

    /**
     * is or no connected
     */
    public boolean isConnect = false;

    public boolean isConnecting = false;


    public BluetoothDeviceInfo mDeviceInfo;

    private BluetoothHandingService mChatService;

    /**
     * The Handler that gets information back from the BluetoothChatService
     */



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        BluetoothCommunicationManager.setContext(mContext);
        mBtMngr = BluetoothCommunicationManager.instance();


    }











}
