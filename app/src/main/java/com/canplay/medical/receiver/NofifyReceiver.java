package com.canplay.medical.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.canplay.medical.base.RxBus;
import com.canplay.medical.base.SubscriptionBean;
import com.canplay.medical.base.manager.AppManager;
import com.canplay.medical.bean.Message;
import com.canplay.medical.mvp.activity.MainActivity;


import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by yg on 2017/6/16.
 */

public class NofifyReceiver extends BroadcastReceiver {
    private static final String TAG = "NofifyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的自定义消息");
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.e("bundle", bundle.toString());
            Log.e("extras", extras);
            JSONObject extrasJson;
            try {
                extrasJson = new JSONObject(extras);
                openNotification(context,extrasJson);
            } catch (Exception e) {
                extras = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                try {
                    extrasJson = new JSONObject(extras);
                    openNotification(context, extrasJson);
                } catch (JSONException e1) {
                    Log.w(TAG, "Unexpected: extras is not a valid json", e);
                }
                return;
            }


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的通知");
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.e("bundle", bundle.toString());
            Log.e("extras", extras);
            JSONObject extrasJson;
            try {
                extrasJson = new JSONObject(extras);
                openNotification(context,extrasJson);
            } catch (Exception e) {
                extras = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                try {
                    extrasJson = new JSONObject(extras);
                    openNotification(context, extrasJson);
                } catch (JSONException e1) {
                    Log.w(TAG, "Unexpected: extras is not a valid json", e);
                }
                return;
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            JSONObject extrasJson;
            try {
                extrasJson = new JSONObject(extras);
                openNotification(context,extrasJson);
            } catch (Exception e) {
                extras = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                try {
                    extrasJson = new JSONObject(extras);
                    openNotification(context, extrasJson);
                } catch (JSONException e1) {
                    Log.w(TAG, "Unexpected: extras is not a valid json", e);
                }
                return;
            }
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, JSONObject extrasJson) {
        AppManager appManager = AppManager.getInstance(context);
        long pushId = 0;
        long businessId = 0;
        String menuName;
        String tableNo;
        pushId = extrasJson.optLong("pushId");
        businessId = extrasJson.optLong("businessId");
        menuName = extrasJson.optString("menuName");
        tableNo = extrasJson.optString("tableNo");
        Message message = new Message();
        message.setMenuName(menuName);
        Log.e("menuName", menuName);
        message.setPushId(pushId);
        message.setBusinessId(businessId);
        message.setTableNo(tableNo);
        Log.e("messageRecevier", message.toString());
        RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.NOFIFY, message));
        if (appManager.isActivityExist(MainActivity.class)) {
            appManager.finishActivityTop(MainActivity.class);
            Log.e("isExist", true + "");
        } else {
            Intent i = new Intent();  //自定义打开的界面
            i.setClass(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            Log.e("isExist", false + "");
        }
    }
}
