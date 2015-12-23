package com.grandstream.confctrol.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.grandstream.confctrol.R;
import com.grandstream.confctrol.utils.LogUtils;
import com.grandstream.confctrol.widget.CanScrollViewPager;

/**
 * Created by zhyjiang on 12/21/15.
 */
public class LocalContacFragment extends BaseFragment {

    private static final String TAG = "LocalContacFragment";
    private CanScrollViewPager mViewPager = null;
    private int TAB_INDEX_COUNT = 2;
    private Button button1;
    private Button button2;
    ContactFrangment contactFrangment1;
    ContactFrangment contactFrangment2;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.localcontact_fragment_main_layout, null);
        initViewPager(view);
        LogUtils.printLog(TAG, " onCreateView " + toString());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        android.support.v4.app.FragmentTransaction fragmentTransaction
                = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(contactFrangment1);
        fragmentTransaction.remove(contactFrangment2);
        fragmentTransaction.commit();
        LogUtils.printLog(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.printLog(TAG, "onDetach");
    }



    private void initViewPager(View view){
        mViewPager = (CanScrollViewPager)view.findViewById(R.id.page);
        mViewPager.setOnPageChangeListener(new PagerChaneListener());
        mViewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        mViewPager.setOffscreenPageLimit(TAB_INDEX_COUNT);
        button1 = (Button)view.findViewById(R.id.button);
        button2 = (Button)view.findViewById(R.id.button2);
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            if (i == 0){
                if (contactFrangment1 == null){
                    contactFrangment1 = new ContactFrangment();
                }
                return contactFrangment1;
            } else {
                if (contactFrangment2 == null){
                    contactFrangment2 = new ContactFrangment();
                }
                return contactFrangment2;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    class PagerChaneListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.button == v.getId()){
                if(mViewPager.getCurrentItem() != 0 ){
                    mViewPager.setCurrentItem(0, true);
                }
            }else {
                if(mViewPager.getCurrentItem() != 1 ){
                    mViewPager.setCurrentItem(1, true);
                }
            }

        }
    };



}
