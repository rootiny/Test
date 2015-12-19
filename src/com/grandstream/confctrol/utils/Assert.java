package com.grandstream.confctrol.utils;

/**
 * Created by zhyjiang on 12/18/15.
 */
public class Assert {
    final static Boolean DEBUG = true;
    public static void debug (Object object) {
        if (!DEBUG){
            return;
        }
        if (object == null){
            throw new NullPointerException(" null point ");
        }
    }
}
