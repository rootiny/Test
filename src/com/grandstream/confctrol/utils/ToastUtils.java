package com.grandstream.confctrol.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by zhyjiang on 12/16/15.
 */
public class ToastUtils {
    private static Toast toast = null;

    public static void showToast(Context context, int id){
        showToast(context, context.getString(id), Gravity.CENTER, 0, 0, 0);
    }

    public static void showToast(Context context, String txt){
        showToast(context, txt, Gravity.CENTER, 0, 0, 0);
    }

    private static void showToast(Context context, String txt, int gravity, int duration, int xOffset, int yOffset) {
        if (toast == null) {
            toast = Toast.makeText(context, txt, duration);
        }
        toast.setText(txt);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.setDuration(duration);
        toast.show();
    }
}
