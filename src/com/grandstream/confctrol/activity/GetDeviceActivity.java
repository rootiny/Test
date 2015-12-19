package com.grandstream.confctrol.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.grandstream.confctrol.R;
import com.grandstream.confctrol.entity.DeviceInfo;
import com.grandstream.confctrol.function.ConnectStateChangeListener;
import com.grandstream.confctrol.function.DeviceFind;
import com.grandstream.confctrol.manager.BluetoothCommunicationManager;
import com.grandstream.confctrol.utils.LogUtils;
import com.grandstream.confctrol.utils.ToastUtils;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.meetme.android.horizontallistview.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhyjiang on 12/15/15.
 */
public class GetDeviceActivity extends Activity {

    private final String TAG = "GetDeviceActivity";
    private BluetoothCommunicationManager mBCM;
    private ConnectStateChangeListener mConnectStateChangeListener;
    private DeviceFind deviceFinder;
    protected QuickAdapter<DeviceInfo> mAdapter;
    private ListView mListView;
    private List<DeviceInfo> mDeviceDateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_device_main_layout);
        init();
        mBCM = BluetoothCommunicationManager.instance();
        deviceFinder = new DeviceFind() {
            @Override
            public void onDeviceFind(DeviceInfo deviceInfo) {
                if (deviceInfo == null){
                    return;
                }
                ToastUtils.showToast(GetDeviceActivity.this, deviceInfo.mDeviceName + deviceInfo.mMacAddress);
                LogUtils.printLog(TAG, deviceInfo.mDeviceName + deviceInfo.mMacAddress);
                mAdapter.add(deviceInfo);
            }

            @Override
            public void onDeviceLoss(DeviceInfo deviceInfo) {

            }
        };

        mBCM.setDeviceFinders(deviceFinder);
        if (! mBCM.startDiscovery()) {
            // ToastUtils.showToast(this, 100);
            // to do
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBCM.stopDiscovery();
        mBCM.removeFinder(deviceFinder);
    }

    private void init(){


        mListView = (ListView)findViewById(R.id.devicelist);
        mDeviceDateList = new ArrayList<DeviceInfo>();
        if (mAdapter == null)
            mAdapter = new QuickAdapter<DeviceInfo>(GetDeviceActivity.this, R.layout.device_list_item_layout,
                    mDeviceDateList) {
                @Override
                protected void convert(BaseAdapterHelper helper, DeviceInfo deviceInfo) {
                    helper.setText(R.id.deviceName, deviceInfo.mDeviceName);
                    helper.setText(R.id.cnnectOrNotBtn,
                            deviceInfo.IfPared?getString(R.string.app_name):getString(R.string.app_name));
                    helper.setTag(R.id.cnnectOrNotBtn, R.string.app_name, deviceInfo);
                    helper.setOnClickListener(R.id.cnnectOrNotBtn, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DeviceInfo deviceInfo = (DeviceInfo)v.getTag(R.string.app_name);
                            mBCM.stopDiscovery();
                            LogUtils.printLog(TAG, deviceInfo.mDeviceName + deviceInfo.mMacAddress);
                            if (mBCM.ensureBound(deviceInfo.mMacAddress)){
                                mBCM.connect(deviceInfo.mMacAddress);
                            }
                        }
                    });
                }
            };
        mListView.setAdapter(mAdapter);



    }



}
