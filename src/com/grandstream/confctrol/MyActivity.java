package com.grandstream.confctrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.grandstream.confctrol.activity.*;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.button_divice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, GetDeviceActivity.class);
                MyActivity.this.startActivity(intent);

            }
        });

        findViewById(R.id.button_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, MainActivity.class);
                MyActivity.this.startActivity(intent);

            }
        });

        findViewById(R.id.button_conference).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, ConferenceActivity.class);
                MyActivity.this.startActivity(intent);

            }
        });

        findViewById(R.id.button_callordial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, CallDialActivity.class);
                MyActivity.this.startActivity(intent);

            }
        });

        findViewById(R.id.button_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, ContactAcitivity.class);
                MyActivity.this.startActivity(intent);

            }
        });

    }
}
