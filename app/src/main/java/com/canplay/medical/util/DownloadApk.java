package com.canplay.medical.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.canplay.medical.BuildConfig;
import com.canplay.medical.mvp.activity.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/* Created by mykar on 17/10/25.
    */
public class DownloadApk implements Runnable {
    InputStream is;
    FileOutputStream fos;
    private Context context;
    private MainActivity activity;

    public DownloadApk(String url, MainActivity activity) {
        this.url = url;
        this.activity = activity;
    }

    private String url;

    /**
     * 下载完成,提示用户安装
     */
    public void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivityForResult(intent, 0);
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().get().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {

                //获取内容总长度
                long contentLength = response.body().contentLength();
                //设置最大值
                //保存到sd卡
                File apkFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".apk");
                fos = new FileOutputStream(apkFile);
                //获得输入流
                is = response.body().byteStream();
                //定义缓冲区大小
                byte[] bys = new byte[1024];
                int progress = 0;
                int len = -1;
                while ((len = is.read(bys)) != -1) {
                    try {
                        Thread.sleep(1);
                        fos.write(bys, 0, len);
                        fos.flush();
                        progress += len;
                        //设置进度

                    } catch (InterruptedException e) {
                        return;
                    }
                }
                //下载完成,提示用户安装
                installApk(apkFile);
            }
        } catch (IOException e) {
            return;
        } finally {
            //关闭io流
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                is = null;
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos = null;
            }
        }
    }
}