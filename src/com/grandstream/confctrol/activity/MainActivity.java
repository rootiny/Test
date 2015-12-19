package com.grandstream.confctrol.activity;

import android.app.Activity;
import android.os.Bundle;
import com.grandstream.confctrol.R;

/**
 * Created by zhyjiang on 12/15/15.
 */
public class MainActivity extends Activity {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main_layout);
    }
}
