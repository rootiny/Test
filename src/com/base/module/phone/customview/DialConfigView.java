/****************************************************************************
 *
 * FILENAME:        com.base.module.phone.customview.DialConfigView.java
 *
 * LAST REVISION:   $Revision: 1.0
 * LAST MODIFIED:   $Date: 2013/01/22 02:14:08 2015-7-16
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

import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.grandstream.confctrol.R;
//import com.base.module.call.CallManager;
//import com.base.module.call.account.CallAccount;
//import com.base.module.call.account.CallAccountManager;
//import com.base.module.call.settings.CallSettingsManager;
//import com.base.module.phone.BluetoothIndicator;
//import com.base.module.phone.R;
//import com.base.module.phone.utils.GlobalUtil;

import java.util.ArrayList;
import java.util.List;

public class DialConfigView extends ScrollView {
    private static final String TAG = "DialConfigView";
    private LayoutInflater mInflater;
    private Context mContext;
    private LinearLayout mAccountLayout, mScheduleLayout;
//    private List<DialAccountItemView> mAccountItemViewList = new ArrayList<DialAccountItemView>();
//    private List<DialAccountItemView> mAvailableAccountItemList = new ArrayList<DialAccountItemView>();
    private int mCheckedAccountId = -1;
//    private DialAccountItemView mCheckedAccountItem;
    private Button mCallModeSpinner;
//    private AccountOnClickCallBack mAccountCallBack;
//    private BluetoothIndicator mBtIndicator;

//    private CallAccountManager mCallAccountManager;
//    private CallSettingsManager mCallSettingsManager;

    public DialConfigView(Context context) {
        this(context, null);
    }

    public DialConfigView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = LayoutInflater.from(context);
//        mCallAccountManager = (CallAccountManager) context.getSystemService(Context.CALL_ACCOUNT_SERVICE);
//        mCallSettingsManager = (CallSettingsManager) context.getSystemService(Context.CALL_SETTINGS_SERVICE);
//        if (GlobalUtil.isLauncherDialer()) {
//            mInflater.inflate(R.layout.dial_config_view_launcher, this, true);
//        } else {
            mInflater.inflate(R.layout.dial_config_view, this, true);
//        }
        mCallModeSpinner = (Button) findViewById(R.id.spinner_call_mode);
        mAccountLayout = (LinearLayout) findViewById(R.id.layout_usable_accounts);
        mScheduleLayout = (LinearLayout) findViewById(R.id.layout_scheduled_conf);
//        if (!GlobalUtil.isLauncherDialer()) {
            setBackgroundColor(0xfff0f4f7);
//        }
//        initView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        if (mCallModeDlg != null) {
//            if (mCallModeDlg.isShowing()) {
//                mCallModeDlg.dismiss();
//            }
//            mCallModeDlg = null;
//        }
//    }
//
//    private void initView() {
//        loadCallMode();
//        loadAccounts();
//    }

//    private void loadAccounts() {
//        List<CallAccount> callAccountList = null;
//        CallAccount btAccount = null;
//        CallAccount lyncAccount = null;
//        CallAccount skypeAccount = null;
//        CallAccount hangoutsAccount = null;
//        if (mCallAccountManager != null) {
//            callAccountList = mCallAccountManager.getAllUsableCallAccounts();
//            btAccount = mCallAccountManager.getBluetoothAccount();
//            lyncAccount = mCallAccountManager.getLyncAccount();
//            skypeAccount = mCallAccountManager.getSkypeAccount();
//            hangoutsAccount = mCallAccountManager.getHangoutsAccount();
//        }
//        mAccountLayout.removeAllViews();
//        mAvailableAccountItemList.addAll(mAccountItemViewList);
//
//        mAccountItemViewList.clear();
//        mCheckedAccountItem = null;
//
//        if (btAccount.isActived() && isGVCBtConnect()) {
//            setAppCountItem(btAccount);
//            mCheckedAccountId = btAccount.getLocalId();
//            mCheckedAccountItem = (DialAccountItemView) mAccountLayout.getChildAt(0);
//            updateAccountCheck();
//            return;
//        }
//
//
//        if (callAccountList != null) {
//            int len = callAccountList.size();
//            for (int i = 0; i<len; i++) {
//                CallAccount callAccount = callAccountList.get(i);
//                setAppCountItem(callAccount);
//            }
//
//            if (mCheckedAccountItem == null && len > 0 && mCheckedAccountId == -1) {
//                CallAccount account = callAccountList.get(0);
//                mCheckedAccountId = account.getLocalId();
//                mCheckedAccountItem = (DialAccountItemView) mAccountLayout.getChildAt(0);
//            }
//        }
//
//        if (btAccount.isActived() == true) {
//              setAppCountItem(btAccount);
//        }
//
//        if(AccountManager.hasLyncAccount(getContext()) && lyncAccount != null){
//            setAppCountItem(lyncAccount);
//        }
//
//        if(AccountManager.hasSkypeAccount(getContext()) && skypeAccount != null){
//            setAppCountItem(skypeAccount);
//        }
//
//        if(AccountManager.hasHangoutAccount(getContext()) && hangoutsAccount != null){
//            setAppCountItem(hangoutsAccount);
//        }
//
//
//        updateAccountCheck();
//    }
//    private void setAppCountItem(CallAccount account){
//        DialAccountItemView accountItem = createAccountItem(account);
//        if (mCheckedAccountId == account.getLocalId()) {
//            mCheckedAccountItem = accountItem;
//        }
//        mAccountItemViewList.add(accountItem);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 75);
//        mAccountLayout.addView(accountItem, params);
//    }
//
//
//    private void updateAccountCheck() {
//        int len = mAccountItemViewList.size();
//        for (int i = 0; i < len; i++) {
//            DialAccountItemView item = mAccountItemViewList.get(i);
//            CallAccount account = item.getAccount();
//            if (account.getLocalId() == mCheckedAccountId) {
//                item.setChecked(true);
//            }else{
//                item.setChecked(false);
//            }
//        }
//    }
//
//    private DialAccountItemView createAccountItem(CallAccount account) {
//        DialAccountItemView accountItem;
//        if (mAvailableAccountItemList.size() > 0) {
//            accountItem = mAvailableAccountItemList.remove(0);
//        } else {
//            int layoutId = R.layout.dial_account_item;
//            if (GlobalUtil.isLauncherDialer()) {
//                layoutId = R.layout.dial_account_item_launcher;
//            }
//            accountItem = (DialAccountItemView) mInflater.inflate(layoutId, mAccountLayout, false);
//        }
//
//        Log.d("account","account="+account);
//        TextView accountNumberText = (TextView) accountItem.findViewById(R.id.tv_account_number);
//        accountNumberText.setText(getAccountName(account));
//        ImageView accountIcon = (ImageView) accountItem.findViewById(R.id.imageview_account);
//
//        if (account.isRegistered()) {
//            if (account.getLocalId() == CallAccountManager.BT_ACCOUNT_ID) {
//                accountIcon.setImageResource(R.drawable.call_account_bt_icon);
//            } else if (account.getLocalId() < CallAccountManager.MAX_SIP_ACCOUNT) {
//                accountIcon.setImageResource(R.drawable.call_account_icon);
//            }else if(account.getLocalId() >= CallAccountManager.LYNC_ACCOUNT_ID &&
//                    account.getLocalId() <= CallAccountManager.HANGOUTS_ACCOUNT_ID){
//                accountIcon.setImageResource(R.drawable.third_app_account_activation);
//            }
//        } else {
//            if (account.getLocalId() == CallAccountManager.BT_ACCOUNT_ID) {
//                accountIcon.setImageResource(R.drawable.call_account_unregistered_bt_icon);
//            } else if (account.getLocalId() < CallAccountManager.MAX_SIP_ACCOUNT) {
//                accountIcon.setImageResource(R.drawable.call_account_not_registered_icon);
//            }else if(account.getLocalId() >= CallAccountManager.LYNC_ACCOUNT_ID &&
//                    account.getLocalId() <= CallAccountManager.HANGOUTS_ACCOUNT_ID){
//                accountIcon.setImageResource(R.drawable.third_app_account_not_activated);
//            }
//        }
//
//        accountItem.setAccount(account);
//        accountItem.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialAccountItemView accountView = (DialAccountItemView) v;
//                if (accountView.getAccount() != null) {
//                    mCheckedAccountId = accountView.getAccount().getLocalId();
//                    mCheckedAccountItem = accountView;
//                    updateAccountCheck();
//                    if(mAccountCallBack != null){
//                        mAccountCallBack.onClick(mCheckedAccountId);
//                    }
//                }
//            }
//        });
//
//        return accountItem;
//    }
//    public interface AccountOnClickCallBack{
//        public void onClick(int accountId);
//    }
//    public void setAccoutCallback(AccountOnClickCallBack callBack){
//        this.mAccountCallBack = callBack;
//    }
//    private String getAccountName(CallAccount account) {
//        if (account == null || (TextUtils.isEmpty(account.getLocalName()) && TextUtils.isEmpty(account.getSipAuthId()))) {
//            int accountId = account.getLocalId()+1;
//            return getResources().getString(R.string.str_account)+" " + accountId;
//        } else {
//            if (!TextUtils.isEmpty(account.getLocalName())) {
//                return account.getLocalName();
//            } else {//TextUtils.isEmpty(callAccount.getSipAuthId())
//                return account.getSipAuthId();
//            }
//        }
//    }
//
//    public LinearLayout getScheduleLayout() {
//        return mScheduleLayout;
//    }
//
//    public void setBtIndicator(BluetoothIndicator indicator) {
//        mBtIndicator = indicator;
//        initView();
//    }
//
//    private boolean isGVCBtConnect() {
//        if (mBtIndicator == null) {
//            Log.e(TAG, "mBtIndicator is null!");
//            return false;
//        }
//
//        return mBtIndicator.isGVCBtConnect();
//    }
//
//    private void loadCallMode() {
//        if (isGVCBtConnect()) {
//            mSelectionItemsId = R.array.call_mode_gvc_bt_call_array;
//            if (mCallSettingsManager.isIpCallDisabled()) {
//                mSelectionItemsId = R.array.call_mode_gvc_bt_call_noip_array;
//            }
//        }else {
//            mSelectionItemsId = R.array.call_mode_enable_IP_call_array;
//            if (mCallSettingsManager.isIpCallDisabled()) {
//                mSelectionItemsId = R.array.call_mode_disable_IP_call_array;
//            }
//        }
//        mSelectionItems = mContext.getResources().getStringArray(mSelectionItemsId);
//
//        Object tag = mCallModeSpinner.getTag();
//        boolean resetTag = true;
//        if (tag instanceof Integer) {
//            int tagValue = (Integer)tag;
//            if (tagValue >= 0 && tagValue < mSelectionItems.length) {
//                resetTag = false;
//            }
//        }
//
//        if (resetTag) {
//            mCallModeSpinner.setTag(0);
//            mCallModeSpinner.setText(mSelectionItems[0]);
//        }
//
//        mCallModeSpinner.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mCallModeDlg == null) {
//                    mCallModeDlg = buildCallModeDlg();
//                }
//                mCallModeDlg.show();
//            }
//        });
//    }
//
//    private AlertDialog.Builder mCallModeBuilder = null;
//    private DialogInterface.OnClickListener mOnSelectCallModeListener;
//    private DialogInterface.OnDismissListener mOnDismissListener;
//    private DialogInterface.OnCancelListener mOnCancelListener;
//    private AlertDialog mCallModeDlg = null;
//    private String[] mSelectionItems = null;
////    private int mSelectionItemsId = R.array.call_mode_enable_IP_call_array;
//
//    private AlertDialog buildCallModeDlg() {
//        if (mCallModeBuilder == null) {
//            mCallModeBuilder = new AlertDialog.Builder(mContext);
//        }
//        AlertDialog.Builder dlg = mCallModeBuilder;
//
//        if (mOnSelectCallModeListener == null) {
//            mOnSelectCallModeListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    mCallModeSpinner.setText(mSelectionItems[which]);
//                    if (isGVCBtConnect() && which == 1) {
//                        mCallModeSpinner.setTag(CallManager.CALLMODE_IP);
//                    } else {
//                        mCallModeSpinner.setTag(which);
//                    }
//                    dialog.dismiss();
//                }
//            };
//        }
//
//        if (mOnDismissListener == null) {
//            mOnDismissListener = new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    Log.i(TAG, "CallMode dialog onDismiss");
//                    mCallModeDlg = null;
//                }
//            };
//        }
//
//        if (mOnCancelListener == null) {
//            mOnCancelListener = new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    Log.i(TAG, "CallMode dialog onCancel");
//                }
//            };
//        }
//
//        dlg.setTitle(R.string.str_mode).setItems(mSelectionItemsId, mOnSelectCallModeListener)
//            .setOnDismissListener(mOnDismissListener).setOnCancelListener(mOnCancelListener);
//        return dlg.create();
//    }
//
//    public int getCallMode() {
//        int callMode = CallManager.CALLMODE_SIP;
//        int which = (Integer) mCallModeSpinner.getTag();
//        switch (which) {
//        case 0:
//            callMode = CallManager.CALLMODE_SIP;
//            break;
//        case 1:
//            callMode = CallManager.CALLMODE_PAGING;
//            break;
//        case 2:
//            callMode = CallManager.CALLMODE_IP;
//            break;
//        default:
//            break;
//        }
//        return callMode;
//    }
//
//    public CallAccount getSelectedCallAccount() {
//        if (mCheckedAccountItem == null) {
//            return null;
//        }
//
//        return mCheckedAccountItem.getAccount();
//    }
//
//    public void setAccountSpinerPosition(int accountId) {
//        if (accountId != -1) {
//            mCheckedAccountId = accountId;
//            loadAccounts();
//        }
//    }
//
//    public void setCallModeSpinerPosition(int callMode) {
//        if(callMode > CallManager.CALLMODE_IP) return;
//
//        if (callMode == CallManager.CALLMODE_IP && mCallSettingsManager.isIpCallDisabled()) {
//            mCallModeSpinner.setText(mSelectionItems[CallManager.CALLMODE_SIP]);
//            mCallModeSpinner.setTag(CallManager.CALLMODE_SIP);
//        } else {
//            mCallModeSpinner.setText(mSelectionItems[callMode]);
//            mCallModeSpinner.setTag(callMode);
//        }
//    }
//
//    public void setCallModeSpinnerEnabled(boolean enabled) {
//        mCallModeSpinner.setEnabled(enabled);
//    }
//
//    public void reloadAccounts() {
//        loadAccounts();
//    }
//
//    public void reloadCallMode() {
//        loadCallMode();
//    }
}

