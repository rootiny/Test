package com.grandstream.confctrol.activity;

import android.app.Activity;
import android.os.Bundle;
import com.grandstream.confctrol.R;
import com.grandstream.confctrol.entity.DeviceInfo;
import com.grandstream.confctrol.function.ConnectStateChangeListener;
import com.grandstream.confctrol.function.DeviceFind;
import com.grandstream.confctrol.manager.BluetoothCommunicationManager;
import com.grandstream.confctrol.utils.LogUtils;
import com.grandstream.confctrol.utils.ToastUtils;

/**
 * Created by zhyjiang on 12/15/15.
 */
public class GetDeviceActivity extends Activity {

    private final String TAG = "GetDeviceActivity";
    private BluetoothCommunicationManager mBCM;
    private ConnectStateChangeListener mConnectStateChangeListener;
    private DeviceFind deviceFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_device_main_layout);
        mBCM = BluetoothCommunicationManager.instance();
        deviceFinder = new DeviceFind() {
            @Override
            public void onDeviceFind(DeviceInfo deviceInfo) {
                ToastUtils.showToast(GetDeviceActivity.this, R.string.app_name);
                LogUtils.printLog(TAG, "sdfsdfasdfasdfsfadfa");
            }

            @Override
            public void onDeviceLoss(DeviceInfo deviceInfo) {

            }
        };
        mBCM.setDeviceFinders(deviceFinder);
        if (! mBCM.startDiscovery()) {
//            ToastUtils.showToast(this, 100);
            // to do
        }


        init();




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init(){




    }



}
