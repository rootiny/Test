package com.grandstream.confctrol.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.grandstream.confctrol.R;
import com.grandstream.confctrol.adapter.ContactsAdapter;
import com.grandstream.confctrol.utils.LogUtils;
import com.grandstream.confctrol.widget.BladeView;
import com.grandstream.confctrol.widget.PinnedHeaderListView;

import java.util.*;

/**
 * Created by zhyjiang on 12/22/15.
 */
public class ContactFrangment extends BaseFragment {

    private static final String TAG = "ContactFrangment";
    private static final String FORMAT = "^[a-z,A-Z].*$";
    private PinnedHeaderListView mListView;
    private BladeView mLetter;
    private ContactsAdapter mAdapter;
    private String[] datas;
    // 首字母集
    private List<String> mSections;
    // 根据首字母存放数据
    private Map<String, List<String>> mMap;
    // 首字母位置集
    private List<Integer> mPositions;
    // 首字母对应的位置
    private Map<String, Integer> mIndexer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_frament_main_layout, null);
        initData();
        initView(view);
        LogUtils.printLog(TAG, " onCreateView " + toString());
        return view;
    }

    private void initData() {
        datas = getResources().getStringArray(R.array.countries);
        mSections = new ArrayList<String>();
        mMap = new HashMap<String, List<String>>();
        mPositions = new ArrayList<Integer>();
        mIndexer = new HashMap<String, Integer>();

        for (int i = 0; i < datas.length; i++) {
            String firstName = datas[i].substring(0, 1);
            if (firstName.matches(FORMAT)) {
                if (mSections.contains(firstName)) {
                    mMap.get(firstName).add(datas[i]);
                } else {
                    mSections.add(firstName);
                    List<String> list = new ArrayList<String>();
                    list.add(datas[i]);
                    mMap.put(firstName, list);
                }
            } else {
                if (mSections.contains("#")) {
                    mMap.get("#").add(datas[i]);
                } else {
                    mSections.add("#");
                    List<String> list = new ArrayList<String>();
                    list.add(datas[i]);
                    mMap.put("#", list);
                }
            }
        }

        Collections.sort(mSections);
        int position = 0;
        for (int i = 0; i < mSections.size(); i++) {
            mIndexer.put(mSections.get(i), position);// 存入map中，key为首字母字符串，value为首字母在listview中位置
            mPositions.add(position);// 首字母在listview中位置，存入list中
            position += mMap.get(mSections.get(i)).size();// 计算下一个首字母在listview的位置
        }



    }

    private void initView(View view) {
        // TODO Auto-generated method stub
        mListView = (PinnedHeaderListView) view.findViewById(R.id.friends_display);
        mLetter = (BladeView) view.findViewById(R.id.friends_myletterlistview);
        mLetter.setOnItemClickListener(new BladeView.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                if (mIndexer.get(s) != null) {
                    mListView.setSelection(mIndexer.get(s));
                }
            }
        });
        mAdapter = new ContactsAdapter(datas, mSections, mPositions, getActivity().getLayoutInflater());
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(mAdapter);
        mListView.setPinnedHeaderView(LayoutInflater.from(getActivity()).inflate(
                R.layout.contact_fragment_spinlistview_headitem_layout, mListView, false));
    }


}
