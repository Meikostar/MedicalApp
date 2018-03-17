package com.canplay.medical.base.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;

import com.canplay.medical.base.BaseApplication;
import com.orhanobut.logger.Logger;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 应用程序异常类：用于捕获异常和提示错误信息
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppException extends BaseException implements UncaughtExceptionHandler{
    private Context mContext;

    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    private AppException(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 获取APP异常崩溃处理对象
     *
     * @return
     */
    public static AppException getAppExceptionHandler(Context context) {
        return new AppException(context);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Logger.e("uncaughtException:"+ex.getMessage());
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
        
        ((BaseApplication)mContext).exit();
        return;
    }
    
    

    /**
     * 自定义异常处理:收集错误信息&发送错误报告
     *
     * @param ex
     * @return true:处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
    	Logger.e("exception Cause:"+ex.getCause());
    	Logger.e("exception Message:"+ex.getMessage());
        //saveErrorLog(ex);
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                new AlertDialog.Builder(mContext).setTitle("提示")
                        .setMessage("应用异常退出！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ((BaseApplication)mContext).exit();
                            }
                        }).create().show();
                Looper.loop();
            }
        }.start();
        return true;
    }

}
