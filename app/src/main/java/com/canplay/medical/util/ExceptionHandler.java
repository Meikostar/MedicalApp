package com.canplay.medical.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import com.canplay.medical.R;
import com.canplay.medical.base.ApplicationConfig;
import com.canplay.medical.base.config.BaseConfig;
import com.canplay.medical.base.manager.AppManager;
import com.canplay.medical.bean.Contract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leo.
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler{
    private Context context;

    public void init(Context context){
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable){
        new Thread(){
            @Override
            public void run(){
                Looper.prepare();
                try{
                    Toast.makeText(context, R.string.cash_tishi, Toast.LENGTH_LONG).show();
                    android.util.Log.e("crash", "uncaughtException", throwable);
                    String log = getLogInfo(throwable);
                    saveLog(log);
                }catch(Exception e){
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }.start();
        try{
            Thread.sleep(3000);
        }catch(Exception e){
            e.printStackTrace();
        }
        AppManager.getInstance(context).exitAPP(context);
    }

    public String getLogInfo(Throwable throwable) throws PackageManager.NameNotFoundException{
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while(cause != null){
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        PackageManager packageManager = ApplicationConfig.context.getPackageManager();
        PackageInfo info = packageManager.getPackageInfo(ApplicationConfig.APP_PACKAGE_NAME, PackageManager.GET_ACTIVITIES);
        StringBuilder builder = new StringBuilder();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH-mm-ss");
        builder.append(String.format("Devices Model: %s\n", Build.MODEL));
        builder.append(String.format("Devices SDK Version: %s\n", Build.VERSION.SDK_INT));
        builder.append(String.format("Software Version Name: %s\n", info.versionName));
        builder.append(String.format("Software Version Code: %s\n", info.versionCode));
        //start(使用标准版的应用名作为标识 )
        builder.append(String.format("Software Type: %s\n", Contract.SOFT_WARE_TYPE));
        //end
        builder.append(String.format("Crash Time: %s\n", format.format(date)));
        builder.append(writer.toString());
        return builder.toString();
    }

    public void saveLog(String input) throws Exception{
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH-mm-ss");
        File folder = new File(BaseConfig.APP_LOG);
        if(folder.mkdirs() || folder.isDirectory()){
            File log = new File(folder, format.format(date) + ".txt");
            FileOutputStream out = null;
            try{
                out = new FileOutputStream(log);
                out.write(input.getBytes());
            }finally{
                if(out != null){
                    out.close();
                }
            }
            // upload(log);
        }
    }
}