package com.grandstream.confctrol.utils;

import android.util.Log;

/**
 * Created by zhyjiang on 12/16/15.
 */
public class LogUtils  {

    public static final boolean IS_DEBUG = false;

    public static void printLog(String TAG, String msg){
        if (IS_DEBUG) {
            Log.i(TAG, msg);
        }
    }
}
