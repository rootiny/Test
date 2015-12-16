package com.grandstream.confctrol.function;

/**
 * Created by zhyjiang on 12/16/15.
 */
public interface ConnectStateChangeListener {

    /**
     *
     * @param messageType
     * {@link com.grandstream.confctrol.utils.Constants.MESSAGE_STATE_CHANGE}
     * {@link com.grandstream.confctrol.utils.Constants.MESSAGE_READ}
     * {@link com.grandstream.confctrol.utils.Constants.MESSAGE_WRITE}
     * {@link com.grandstream.confctrol.utils.Constants.MESSAGE_DEVICE_NAME}
     * {@link com.grandstream.confctrol.utils.Constants.MESSAGE_TOAST}
     *
     * @param state
     * when {@param messageType} == {@link com.grandstream.confctrol.utils.Constants.MESSAGE_STATE_CHANGE}
     * {@link com.grandstream.confctrol.BluetoothHandingService.STATE_NONE}
     * {@link com.grandstream.confctrol.BluetoothHandingService.STATE_LISTEN}
     * {@link com.grandstream.confctrol.BluetoothHandingService.STATE_NONE}
     * {@link com.grandstream.confctrol.BluetoothHandingService.STATE_CONNECTING}
     * {@link com.grandstream.confctrol.BluetoothHandingService.STATE_CONNECTED}
     * {@link com.grandstream.confctrol.BluetoothHandingService.STATE_CONNECTED_LOST}
     */

    void onConnectStateChanged(int messageType, Object state );

}
