package com.grandstream.confctrol.function;

import com.grandstream.confctrol.entity.DeviceInfo;

/**
 * Created by zhyjiang on 12/17/15.
 */
public interface DeviceFind {
    public void onDeviceFind(DeviceInfo deviceInfo);
    public void onDeviceLoss(DeviceInfo deviceInfo);
}
