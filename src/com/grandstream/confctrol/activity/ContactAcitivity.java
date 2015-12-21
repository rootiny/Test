package com.grandstream.confctrol.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.grandstream.confctrol.R;
import com.grandstream.confctrol.fragment.ContactEnterFragment;

import java.util.Stack;

/**
 * Created by zhyjiang on 12/15/15.
 */
public class ContactAcitivity extends Activity {

    final private String TAG = "ContactAcitivity";

    FragmentManger mFragmentManger;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity_main_layout);
        mFragmentManger = new FragmentManger();
        ContactEnterFragment contactEnter = new ContactEnterFragment();
        mFragmentManger.addFragment(R.id.fragment_container, contactEnter);
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
