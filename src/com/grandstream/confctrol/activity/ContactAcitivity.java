package com.grandstream.confctrol.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.grandstream.confctrol.R;
import com.grandstream.confctrol.fragment.ContactEnterFragment;
import com.grandstream.confctrol.fragment.LocalContacFragment;
import com.grandstream.confctrol.utils.LogUtils;

import java.util.Stack;

/**
 * Created by zhyjiang on 12/15/15.
 */
public class ContactAcitivity extends FragmentActivity {

    final private String TAG = "ContactAcitivity";

    public enum ContactsFragment {
        ENTER_FRAGMENT,
        LOCAL_FRAGMENT,
        LDAP_FRAGMENT
    }
    ContactEnterFragment mContactEnterFragment;
    LocalContacFragment mLocalFragment;
    FragmentManger mFragmentManger;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        LogUtils.printLog(TAG, " onAttachFragment " + fragment.toString());
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity_main_layout);
        mFragmentManger = new FragmentManger();
        starFrament(ContactsFragment.ENTER_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        LogUtils.printLog(TAG, " onBackPressed  mFragmentManger.isLast() :" + mFragmentManger.isLast());
        if (!mFragmentManger.isLast()){
            mFragmentManger.removeFrament();
        } else {
            mFragmentManger.removeFrament();
            super.onBackPressed();
        }
    }

    public void starFrament(ContactsFragment contactsFragment){
        mFragmentManger.addFragment(R.id.fragment_container, instanceFragment(contactsFragment));
    }

    private Fragment instanceFragment(ContactsFragment contactsFragment){
        switch (contactsFragment){
            case LOCAL_FRAGMENT:
                if (mLocalFragment == null){
                    mLocalFragment = new LocalContacFragment();
                }
                return mLocalFragment;

            case LDAP_FRAGMENT:
                return null;

            default:
                if (mContactEnterFragment == null){
                    mContactEnterFragment = new ContactEnterFragment();
                }
                return mContactEnterFragment;
        }
    }

    public class FragmentManger {
        Stack<Fragment> fragmentStack;

        public FragmentManger() {
            fragmentStack = new Stack<Fragment>();
        }

        public void addFragment(int resId, Fragment fragment) {
            if (!fragmentStack.contains(fragment)){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(resId, fragment);
                transaction.commit();
                fragmentStack.push(fragment);
            }
        }

        public void removeFrament(){
            LogUtils.printLog(TAG, " removeFrament  fragmentStack.isEmpty() :" + fragmentStack.isEmpty());
            if (!fragmentStack.isEmpty()){
                Fragment fragment = fragmentStack.pop();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
            }
        }

        public boolean isLast(){
            return fragmentStack.size() <= 1;
        }

    }
}
