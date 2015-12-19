package com.grandstream.confctrol.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import com.grandstream.confctrol.R;
import com.grandstream.confctrol.entity.DeviceInfo;
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
public class ConferenceActivity extends Activity {
    private String[] mSimpleListValues = new String[] { "Android",
            "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux",
            "OS/2" };
    private List<String> list = new ArrayList<String>();
    QuickAdapter<String>  adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conference_acitivtiy_main_layout);
        for (String str:mSimpleListValues){
            list.add(str);
        }

        adapter = new QuickAdapter<String>(ConferenceActivity.this,
                R.layout.conferece_activtiy_mid_item_layout, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, String string) {
                helper.setTag(R.id.conf_member, R.id.conf_member ,string);
                helper.setOnClickListener(R.id.conf_member, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addOneNumber();
                    }
                });

                helper.setOnLongClickListener(R.id.conf_member, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        removeOneNumber((String) v.getTag(R.id.conf_member));
                        return false;
                    }
                });
            }
        };

        HorizontalListView tmpListView = (HorizontalListView)findViewById(R.id.horizontalListView);
        tmpListView.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    private void addOneNumber(){
        ToastUtils.showToast(ConferenceActivity.this, "addone");
        adapter.add("hao");
    }

    private void removeOneNumber(String string){
        ToastUtils.showToast(ConferenceActivity.this, string);
        adapter.remove(string);
    }
}
