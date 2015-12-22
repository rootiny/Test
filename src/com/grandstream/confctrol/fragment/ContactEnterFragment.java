package com.grandstream.confctrol.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.grandstream.confctrol.R;
import com.grandstream.confctrol.activity.ContactAcitivity;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhyjiang on 12/21/15.
 */
public class ContactEnterFragment extends Fragment {


    protected QuickAdapter<String> mAdapter;
    protected ListView mListView;
    protected List<String> mDateList = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View linearLayout = (View) inflater
                .inflate(R.layout.contactenter_fragment_main_layout, null);
        mListView = (ListView)linearLayout.findViewById(R.id.list);
        return linearLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDateList.clear();
        mDateList.add(getString(R.string.str_local));
        mDateList.add(getString(R.string.str_ldap));

        if (mAdapter == null)
            mAdapter = new QuickAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                    mDateList) {
                @Override
                protected void convert(BaseAdapterHelper helper, String string) {
                    helper.setText(android.R.id.text1, string);
                }
            };
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((ContactAcitivity)getActivity()).starFrament(ContactAcitivity.ContactsFragment.LOCAL_FRAGMENT);
            }
        });

    }
}
