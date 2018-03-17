package com.canplay.medical.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;

/**
 * Created by Administrator on 2016/9/20.
 */
public class ApplicationConfig{

    /**
     * 当前应用的上下文对象
     */
    public static Context context;

    /**
     * 资源对象
     */
    public static Resources resouce;
    /**
     * 应用的版本
     */
    public static String APP_VERSION = "";

    public static String APP_PACKAGE_NAME = "";

    /**
     * 登陆用户id
     */
    private static long userId = 0;

    /**
     * 登陆用户token
     */
    private static String token = "";

    public static void setAppInfo(Context context) throws NullPointerException{
        if(context == null)
            throw new NullPointerException("context is not null");
        ApplicationConfig.context = context;
        ApplicationConfig.resouce = context.getResources();
        try{
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            ApplicationConfig.APP_VERSION = info.versionName;
            APP_PACKAGE_NAME = info.packageName;
        }catch(PackageManager.NameNotFoundException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @Title: inifLoginUserInfo
     * @Description: TODO(配置用户登陆的基本信息)
     * @param: userId 用户ID
     * @param: token 用户token
     * @return: void
     */
    public static void initLoginUserBaseInfo(long userId, String token){
        ApplicationConfig.userId = userId;
        ApplicationConfig.token = token;
    }
}