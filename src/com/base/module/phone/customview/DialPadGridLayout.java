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
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
//import com.base.module.call.CallManager;
//import com.base.module.call.line.LineObjManager;
//import com.base.module.phone.logic.IDialLogic;
//import com.base.module.phone.utils.GlobalUtil;
//import com.base.module.phone.utils.SpanUtil;
import com.grandstream.confctrol.R;

public class DialPadGridLayout extends GridLayout {
    private static final String TAG = "DialPadGridLayout";
    private static final int[] BTN_NUM_ID = { R.id.btn_number0,
            R.id.btn_number1, R.id.btn_number2, R.id.btn_number3,
            R.id.btn_number4, R.id.btn_number5, R.id.btn_number6,
            R.id.btn_number7, R.id.btn_number8, R.id.btn_number9,
            };
    private Button[] mNumberButton = new Button[10];
    private Button mPoundButton, mStartButton;
    private Button mConfButton, mContactsButton;

//    private CallManager mCallManager;
//    private LineObjManager mLineManager;
    private int mConfLineNum;

    public DialPadGridLayout(Context context) {
        this(context, null);
    }

    public DialPadGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mCallManager = (CallManager) context.getSystemService(Context.CALL_MANAGER_SERVICE);
//        mLineManager = (LineObjManager) context.getSystemService(Context.CALL_LINE_SERVICE);
        mConfLineNum = -1;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < mNumberButton.length; i++) {
            mNumberButton[i] = (Button) findViewById(BTN_NUM_ID[i]);
            // the background including text
            mNumberButton[i].setText("");
        }

        mStartButton = (Button) findViewById(R.id.btn_star);
        mPoundButton = (Button) findViewById(R.id.btn_pound);
        mStartButton.setText("");
        mPoundButton.setText("");

        mContactsButton = (Button) findViewById(R.id.btn_contacts);
        mContactsButton.setText(R.string.str_contacts);
        initConfBtn();
    }

    private void initConfBtn() {
        if (mConfButton == null) {
            mConfButton = (Button) findViewById(R.id.btn_conf);
        }
        int confLineNum = 0;
//        if (mCallManager.hasConference()) {
//            confLineNum = LineObjManager.MAXCONFSEAT - mLineManager.getIdleConfSeat();
//        }
        if (confLineNum != mConfLineNum) {
            Resources res = getResources();
//            CharSequence confText = SpanUtil.formatConfText(res.getString(R.string.str_conf), String.valueOf(confLineNum),
//                    res.getString(confLineNum > 1 ? R.string.str_lines: R.string.str_line), mConfButton.isEnabled());
//            mConfButton.setText(confText);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        /*final ContentResolver resolver = getContext().getContentResolver();
        resolver.unregisterContentObserver(mBrightnessObserver);*/
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {

        // TODO Auto-generated method stub
        super.onMeasure(widthSpec, heightSpec);
//        updateChildViewLayout(widthSpec, heightSpec);

    }

    private void updateChildViewLayout(int widthParant, int heightParant) {
        /*
         * widthParant = w1 + w1+1  + w1+1 + w2+1
         * heightParant = 3 * h1 + h2
         * w1:w2 = 134:120 or 140:108
         */

        /*
         *            The KeyPad
         *
         *       w1    w1     w1     w2
         *  h1 ( 1 )  ( 2 )  ( 3 ) (conf....)   2*h1
         *  h1 ( 4 )  ( 5 )  ( 6 ) (conf....)
         *  h1 ( 7 )  ( 8 )  ( 9 ) (contacts)   h1+h2
         *  h2 ( * )  ( 0 )  ( # ) (contacts)
         */

        int h1 = heightParant / 4;
        int h2 = heightParant - 3 * h1;
        int w1 = 140;
        int w2 = 108;
//        if (GlobalUtil.isLauncherDialer()) {
//            w1 = 134;
//            w2 = 120;
//        }

        w1 = (widthParant) * w1 / (3 * w1 + w2);
        w2 = widthParant - 3 * w1;
        Log.i(TAG, "widthParant = "+widthParant);
        Log.i(TAG, "w1 = "+w1+", w2 = "+w2);
        Log.i(TAG, "heightParant = "+heightParant);
        Log.i(TAG, "h1 = "+h1+", h2 = "+h2);

        LayoutParams params = new LayoutParams();
        /*
         * ( 1 - 9 )
         */
        for (int i = 1; i <= 9; i++) {
            params = (LayoutParams) mNumberButton[i].getLayoutParams();
            params.width = w1;
            params.height = h1;
            updateViewLayout(mNumberButton[i], params);
        }

        /*
         * ( 0 ) w1, h2
         */
        params = (LayoutParams) mNumberButton[0].getLayoutParams();
        params.width = w1;
        params.height = h2;
        updateViewLayout(mNumberButton[0], params);

        /*
         * ( * ) w1, h2
         */
        params = (LayoutParams) mStartButton.getLayoutParams();
        params.height = h2;
        params.width = w1;
        updateViewLayout(mStartButton, params);

        /*
         * ( # ) w1, h2
         */
        params = (LayoutParams) mPoundButton.getLayoutParams();
        params.height = h2;
        params.width = w1;
        updateViewLayout(mPoundButton, params);

        /*
         * (conf) w2, 2*h2
         */
        params = (LayoutParams) mConfButton.getLayoutParams();
        params.height = h1*2;
        params.width = w2;
        updateViewLayout(mConfButton, params);

        /*
         * (contacts) w2, h1+h2
         */
        params = (LayoutParams) mContactsButton.getLayoutParams();
        params.height = h1+h2;
        params.width = w2;
        updateViewLayout(mContactsButton, params);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        Log.i(TAG, "onLayout " + changed);
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            updateChildViewLayout(right-left, bottom -top);
        }
    }

//    public void setLogic(IDialLogic logic, boolean setTransfer) {
//        // mLogic = logic;
//        setTransfer(setTransfer);
//    }

    private void setTransfer(boolean useTransfer) {
        mConfButton.setEnabled(!useTransfer);
        initConfBtn();
    }

    public void updateConfState() {
        initConfBtn();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
//        if (GlobalUtil.isLauncherDialer()) {
//            for (int i = 0; i < 10; i++) {
//                mNumberButton[i].setOnClickListener(l);
//            }
//            mPoundButton.setOnClickListener(l);
//            mStartButton.setOnClickListener(l);
//        }
        mConfButton.setOnClickListener(l);
        mContactsButton.setOnClickListener(l);
    }

    public void setOnTouchKeyListener(OnTouchListener l) {
//        if (!GlobalUtil.isLauncherDialer()) {
//            for (int i = 0; i < 10; i++) {
//                mNumberButton[i].setOnTouchListener(l);
//            }
//            mPoundButton.setOnTouchListener(l);
//            mStartButton.setOnTouchListener(l);
//        }
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        for (int i = 0; i < 10; i++) {
            mNumberButton[i].setOnLongClickListener(l);
        }
        mPoundButton.setOnLongClickListener(l);
        mStartButton.setOnLongClickListener(l);;
        mConfButton.setOnLongClickListener(l);
        mContactsButton.setOnLongClickListener(l);
    }

}

