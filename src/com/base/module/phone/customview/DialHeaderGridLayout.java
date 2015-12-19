/****************************************************************************
 *
 * FILENAME:        com.base.module.coretest.DialPadGridLayout.java
 *
 * LAST REVISION:   $Revision: 1.0
 * LAST MODIFIED:   $Date: 2013/01/22 02:14:08 2015-4-15
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
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
//import com.base.module.call.CallManager;
//import com.base.module.call.account.CallAccountManager;
//import com.base.module.call.line.LineObjManager;
//import com.base.module.call.settings.CallSettingsManager;
//import com.base.module.phone.PhoneApplication;
import com.base.module.phone.customview.MultiCallNumberView.OnCallNumberItemRemoved;
import com.grandstream.confctrol.R;
//import com.base.module.phone.logic.IDialLogic;
//import com.base.module.phone.utils.GetContactsData;
//import com.base.module.phone.utils.GlobalUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DialHeaderGridLayout extends GridLayout {
    private static final String TAG = "DialHeaderGridLayout";
    private static final boolean DBG = false;
//    private LineObjManager mLineManager;
    private LayoutInflater mInflater;
    private Button mCallButton;

    private MultiCallNumberView mCallNumberView;
    private List<LinearLayout> mAvailableItems = new ArrayList<LinearLayout>();
    private List<LinearLayout> mUsingItems = new ArrayList<LinearLayout>();

    private RelativeLayout mCallNumberContainerLayout;
    private EditText mCallNumberEditText;
    private ImageButton mDelButton;
    private Context mContext;
//    private IDialLogic mLogic;

//    public static int MAX_NUMBERS = LineObjManager.MAXCONFSEAT;

//    CallAccountManager mCallAccountManager;
//    CallSettingsManager mCallSettingsManager;
//    CallManager mCallManager;
    public DialHeaderGridLayout(Context context) {
        this(context, null);
    }

    public DialHeaderGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mLineManager = (LineObjManager) context.getSystemService(Context.CALL_LINE_SERVICE);
//        mCallManager = (CallManager) context.getSystemService(Context.CALL_MANAGER_SERVICE);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mCallAccountManager = (CallAccountManager) context.getSystemService(Context.CALL_ACCOUNT_SERVICE);
//        mCallSettingsManager = (CallSettingsManager) context.getSystemService(Context.CALL_SETTINGS_SERVICE);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mCallButton = (Button) findViewById(R.id.btn_call);
        mCallNumberContainerLayout = (RelativeLayout) findViewById(R.id.layout_call_number_container);

        mCallNumberView = (MultiCallNumberView) findViewById(R.id.multi_callnumber_view);

        int layoutId = R.layout.edittext_call_number;
//        if (GlobalUtil.isLauncherDialer()) {
//            layoutId = R.layout.edittext_call_number_launcher;
//        }
        mCallNumberEditText = mCallNumberView.addEditText(layoutId);
        mCallNumberView.setOnItemRemoved(new OnCallNumberItemRemoved() {
            @Override
            public void onRemoved(View v) {
                if (v instanceof LinearLayout) {
                    LinearLayout layout = (LinearLayout) v;
                    mAvailableItems.add(layout);
                    mUsingItems.remove(layout);
                }
            }
        });

        final EditText et = mCallNumberEditText;

        // This will enable EditText to show soft keyboard.
        OnTouchListener touchListener = new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                /*mMsgHandler.sendEmptyMessage(UPDATE_FOCUS);*/
                if (v instanceof EditText) {
//                    if (mCallSettingsManager.isEnableSoftKeyboard()) {
//                        // save input type and restore input type, this is a safe behavior.
//                        EditText et = (EditText) v;
//                        int inType = et.getInputType();
//                        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
//                        et.onTouchEvent(event);
//                        et.setInputType(inType);
//                        et.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//                        try {
//                            Class<EditText> cls = EditText.class;
//                            Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
//                            setShowSoftInputOnFocus.setAccessible(true);
//                            setShowSoftInputOnFocus.invoke(et, true);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        return true;
//                    } else
                    {
                        try {
                            Class<EditText> cls = EditText.class;
                            Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                            setShowSoftInputOnFocus.setAccessible(true);
                            setShowSoftInputOnFocus.invoke(et, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                }
                return true;
            }
        };
        et.setOnTouchListener(touchListener);

        mDelButton = (ImageButton) findViewById(R.id.btn_del);
        setDelEnabled(false);
    }

    private LinearLayout obtainNewNumberItem() {
        LinearLayout layout = null;
        if (mAvailableItems.size() > 0) {
            layout = mAvailableItems.remove(0);
        } else {
//            if ((mCallManager.hasConference() && mUsingItems.size() < mLineManager.getIdleConfSeat())
//                || mUsingItems.size() < mLineManager.getIdleLineNum()) {
//                int layoutId = R.layout.number_layout;
//                if (GlobalUtil.isLauncherDialer()) {
//                    layoutId = R.layout.number_layout_launcher;
//                }
//                layout = (LinearLayout) mInflater.inflate(layoutId, null);
//            }
        }
        if (layout != null) {
            mUsingItems.add(layout);
        }
        return layout;
    }

    public void addNewSavedNumber(Map<String, Object> map) {
//        String number = (String) map.get(GetContactsData.CONTACTS_NUMBER);
        String number = "";
        if (TextUtils.isEmpty(number)) {
            return;
        }

        LinearLayout layout = obtainNewNumberItem();
        if (layout == null) {
//            Toast.makeText(mContext, getResources().getString(R.string.str_up_line_limit), Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(TAG, "add a new call number: " + map);
        layout.setTag(map);
        final TextView tv = (TextView) layout.findViewById(R.id.tv_number);
        if (number.length() > 12) {
            number = number.substring(0, 12) + "..";
        }
        tv.setText(number);
        mCallNumberView.addSavedNumberView(layout);
        mCallNumberEditText.setText("");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        /*final ContentResolver resolver = getContext().getContentResolver();
        resolver.unregisterContentObserver(mBrightnessObserver);*/
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        //updateChildViewLayout(widthSpec, heightSpec);
    }

    private void updateChildViewLayout(int widthParant, int heightParant) {
        /*
         * widthParant = w1 + w1 + w2
         * w1:w2 = 175:110
         */

        /*
         *            The KeyPad
         *
         *         w1                  w1                   w2
         *  h1 ( ..... call number edit text ..... )   (call button) heightParant
         */

        int w1 = 80;
        int w2 = 43;
        w1 = (widthParant * w1) / (2 * w1 + w2);
        w2 = widthParant - 2 * w1;
//        if (DBG || Log.isLoggable(TAG, Log.VERBOSE) || Log.isLoggable(PhoneApplication.TAG, Log.VERBOSE)) {
//            Log.i(TAG, "widthParant = "+widthParant);
//            Log.i(TAG, "w1 = "+w1+", w2 = "+w2);
//            Log.i(TAG, "heightParant = "+heightParant);
//        }

        LayoutParams params = new LayoutParams();

        /*
         * (call number edit text) 2*w1, h2
         */
        params = (LayoutParams) mCallNumberContainerLayout.getLayoutParams();
        params.height = heightParant - params.topMargin - params.bottomMargin;
        params.width = w1 * 2 - params.leftMargin - params.rightMargin;
        updateViewLayout(mCallNumberContainerLayout, params);

        /*
         * (call button) w2, heightParant
         */
        params = (LayoutParams) mCallButton.getLayoutParams();
        params.height = heightParant - params.topMargin - params.bottomMargin;
        params.width = w2 - params.leftMargin - params.rightMargin;
        updateViewLayout(mCallButton, params);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            updateChildViewLayout(right-left, bottom -top);
        }
    }

    public EditText getEditText() {
        return mCallNumberEditText;
    }

    public Editable getText() {
        return mCallNumberEditText.getText();
    }

    public int getUsingItemSize() {
        return mUsingItems.size();
    }

    public void insert(String delta) {
        int cursor = mCallNumberEditText.getSelectionStart();
        mCallNumberEditText.getText().insert(cursor, delta);
    }

    public void setText(String text) {
        if (mCallNumberView.removeAllCallNumber()) {
            mAvailableItems.addAll(mUsingItems);
            mUsingItems.clear();
        }
        mCallNumberEditText.setText(text);
        mCallNumberEditText.setSelection(text.length());
    }

//    public void setLogic(IDialLogic logic) {
//        mLogic = logic;
//    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mCallButton.setOnClickListener(l);
    }

    public void setOnTouchDelListener(OnTouchListener l) {
        mDelButton.setOnTouchListener(l);
    }

    public void setDelEnabled(boolean enabled) {
        if (enabled) {
            mDelButton.setBackgroundResource(R.drawable.dialer_del_bg);
        } else {
            mDelButton.setBackgroundResource(R.drawable.dialer_del_padding_normal);
        }
    }

    public void setTextChangedListener(TextWatcher l) {
        mCallNumberEditText.addTextChangedListener(l);
    }

    public List<Map<String, Object>> getCallNumbers() {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        if (mUsingItems.size() > 0) {
            for (LinearLayout layout:mUsingItems) {
                if (layout != null && layout.getTag() != null && layout.getTag() instanceof Map) {
                    list.add((Map<String, Object>)layout.getTag());
                }
            }
        }
        return list;
    }

}

