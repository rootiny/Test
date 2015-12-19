package com.grandstream.confctrol.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.grandstream.confctrol.R;
import com.grandstream.confctrol.utils.LogUtils;

import java.util.Stack;

/**
 * Created by zhyjiang on 12/15/15.
 */
public class ContactAcitivity extends Activity {

    final private String TAG = "ContactAcitivity";

    FragmentManger mFragmentManger;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LogUtils.printLog(TAG," onClick " + (String)v.getTag() );
            if ("fragment1".equals((String)v.getTag())){
                mFragmentManger.addFragment(R.id.fragment_container, fragment2);
            } else if ("fragment2".equals((String)v.getTag())){
                mFragmentManger.addFragment(R.id.fragment_container, fragment3);
            } else if ("fragment3".equals((String)v.getTag())){
                mFragmentManger.removeFrament();
            }
        }
    };

    Fragment  fragment1 = new Fragment(){
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            TextView textView = new TextView(ContactAcitivity.this);
            textView.setText("fragment1");
            textView.setTag("fragment1");
            textView.setOnClickListener(onClickListener);
            return textView;
        }
    };


    Fragment  fragment2 = new Fragment(){
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            TextView textView = new TextView(ContactAcitivity.this);
            textView.setText("fragment2");
            textView.setTag("fragment2");
            textView.setOnClickListener(onClickListener);
            return textView;
        }
    };

    Fragment  fragment3 = new Fragment(){
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            TextView textView = new TextView(ContactAcitivity.this);
            textView.setText("fragment3");
            textView.setTag("fragment3");
            textView.setOnClickListener(onClickListener);
            return textView;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity_main_layout);
        mFragmentManger = new FragmentManger();
        mFragmentManger.addFragment(R.id.fragment_container, fragment1);
    }

    @Override
    public void onBackPressed() {
        if (!mFragmentManger.isLast()){
            mFragmentManger.removeFrament();
        } else {
            mFragmentManger.removeFrament();
            super.onBackPressed();
        }
    }


    public class FragmentManger {
        Stack<Fragment> fragmentStack;

        public FragmentManger() {
            fragmentStack = new Stack<Fragment>();
        }

        public void addFragment(int resId, Fragment fragment) {
            if (!fragmentStack.contains(fragment)){
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(resId, fragment);
                transaction.commit();
                fragmentStack.push(fragment);
            }
        }

        public void removeFrament(){
            if (!fragmentStack.isEmpty()){
                Fragment fragment = fragmentStack.pop();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
            }
        }

        public boolean isLast(){
            return fragmentStack.size() <= 1;
        }

    }
}
