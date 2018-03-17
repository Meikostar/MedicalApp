package com.canplay.medical.util;


import android.os.Handler;
import android.util.Log;

import com.canplay.medical.base.BaseApplication;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by yg on 2017/3/29.
 */

public class JPushUtils {
    private static final String TAG = "JPushUtils";

    private JPushUtils(){}
    private static JPushUtils instance;
    public static JPushUtils shareInstance(){
        if(instance == null){
            instance = new JPushUtils();
        }
        return instance;
    }
    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    public void  setAlias(String alias, int flag) {

        // 调用 Handler 来异步设置别名
        switch (flag){
            case 1:
                tag.add("general_tag");
                break;
            case 2:
                tag.add("company_tag");
                break;
            case 3:
                tag.add("company_super_tag");
                break;
            case 4:
                tag.add("admin_super_tag");
                break;
            case 5:
                tag.add("admin_tag");
                break;
        }

        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));

    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private Set<String> tag=new HashSet<>();
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。

                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(BaseApplication.getInstance(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);

                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

}
