package com.grandstream.confctrol.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.grandstream.confctrol.R;

/**
 * Created by zhyjiang on 12/15/15.
 */
public class CallDialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calldial_activity_main_layout);


    }

    @Override
    protected void onResume() {
        // 横屏
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();

    }
}
