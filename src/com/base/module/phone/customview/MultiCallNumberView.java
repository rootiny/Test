/****************************************************************************
 *
 * FILENAME:        com.test.ringtone.MultiCallNumberView.java
 *
 * LAST REVISION:   $Revision: 1.0
 * LAST MODIFIED:   $Date: 2013/01/22 02:14:08 2015-6-18
 *
 *
 * vi: set ts=4:
 *
 * Copyright (c) 2009-2013 by Grandstream Networks, Inc.
 * All rights reserved.
 *
 * This material is proprietary to Grandstream Networks, Inc. and,
 * in addition to the above mentioned Copyright, may be
 * subject to protection under other intellectual property
 * regimes, including patents, trade secrets, designs and/or
 * trademarks.
 *
 * Any use of this material for any purpose, except with an
 * express license from Grandstream Networks, Inc. is strictly
 * prohibited.
 *
 ***************************************************************************/
package com.base.module.phone.customview;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.grandstream.confctrol.R;

public class MultiCallNumberView extends HorizontalScrollView {
    private static final String TAG = "MultiCallNumberView";
    private static final boolean DBG = false;

    private Context mContext;
    private LinearLayout mContainer;
    private EditText mInputEditText;
    private OnCallNumberItemRemoved mItemRemovedListener;

    public MultiCallNumberView(Context context) {
        this(context, null);
    }

    public MultiCallNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mContainer = (LinearLayout) findViewById(R.id.layout_container);
    }

    private void updateChildViewLayout(int widthParant, int heightParant) {
        LayoutParams params = (LayoutParams) mContainer.getLayoutParams();
        params.width = widthParant;
        updateViewLayout(mContainer, params);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {

        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            updateChildViewLayout(right-left, bottom -top);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (DBG || Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.i(TAG, "onMeasure("+widthMeasureSpec+")");
        }

        if (mContainer.getChildCount() > 0) {
            int otherWidth = 0;
            for (int i = 0; i < mContainer.getChildCount() - 1; i++) {
                final View v = mContainer.getChildAt(i);
                otherWidth += v.getWidth();
            }
            final View child = mContainer.getChildAt(mContainer.getChildCount() - 1);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();
            int minWidth = getWidth() - otherWidth;
            if (DBG || Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.i(TAG, "getWidth="+getWidth()+", otherWidth="+otherWidth);
            }
            if (minWidth < 200) {
                minWidth = 200;
            }
            child.setMinimumWidth(minWidth);
            params.width = minWidth;
            mContainer.updateViewLayout(child, params);
        }
    }

    public EditText addEditText(int inputLayout) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInputEditText = (EditText) inflater.inflate(inputLayout, null);
        final EditText et = mInputEditText;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mContainer.addView(et, params);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ViewGroup)mContainer.getParent()).requestLayout();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN &&
                        TextUtils.isEmpty(mInputEditText.getText().toString()) && mContainer.getChildCount() > 1) {
                    mInputEditText.requestFocus();
                    View view = mContainer.getChildAt(mContainer.getChildCount() - 2);
                    if (mItemRemovedListener != null) {
                        mItemRemovedListener.onRemoved(view);
                    }
                    mContainer.removeViewAt(mContainer.getChildCount() - 2);
                    return false;
                }
                return false;
            }
        });

        return mInputEditText;
    }

    public void addSavedNumberView(LinearLayout layout) {
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemRemovedListener != null) {
                    mItemRemovedListener.onRemoved(v);
                }
                mContainer.removeView(v);
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //params.width = 100;
        if (mInputEditText != null) {
            mContainer.addView(layout, mContainer.getChildCount() - 1, params);
        } else {
            mContainer.addView(layout, params);
        }
    }

    public boolean removeAllCallNumber() {
        mContainer.removeViews(0, mContainer.getChildCount() - 1);
        return true;
    }

    public interface OnCallNumberItemRemoved {
        public void onRemoved(View v);
    }

    public void setOnItemRemoved(OnCallNumberItemRemoved l) {
        mItemRemovedListener = l;
    }
}

