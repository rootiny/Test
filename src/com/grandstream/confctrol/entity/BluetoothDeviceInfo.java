package com.grandstream.confctrol.entity;

import java.io.Serializable;

/**
 * Created by zhyjiang on 12/15/15.
 */
public class BluetoothDeviceInfo implements Serializable {


    private static final long serialVersionUID = 2198780216136180013L;

    public String mDeviceName;

    public String mMacAddress;

    /**
     * device state,
     * <br/>
     * <p>Possible return values are BluetoothAdapter
     * {@link #STATE_OFF},
     * {@link #STATE_TURNING_ON},
     * {@link #STATE_ON},
     * {@link #STATE_TURNING_OFF}.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH}
     */

    public int mState;


    /**
     * Bluetooth signal strength
     * <p/>
     * standard is -100 ~0
     */

    public int mRssi;

    public boolean mEnable;
}
