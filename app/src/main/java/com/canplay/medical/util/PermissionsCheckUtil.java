package com.canplay.medical.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by yangnaibo on 2016/8/27.
 * 动态获取权限工具类
 */
public class PermissionsCheckUtil{

    //全部必要权限
    public static final String[] necessaryPermissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};

    //请求LOCATION权限的请求码
    public static final int CODE_REQUEST_PERMISSION_LOCATION = 0x01;
    //请求PHONE权限的请求码
    public static final int CODE_REQUEST_PERMISSION_PHONE = 0x02;
    //请求STORAGE权限的请求码
    public static final int CODE_REQUEST_PERMISSION_STORAGE = 0x03;
    //请求联系人权限的请求码
    public static final int CODE_REQUEST_PERMISSION_CONTACTS = 0x04;
    //请求CAMEAR权限的请求码
    public static final int CODE_REQUEST_PERMISSION_CAMEAR = 0x05;
    //请求MICROPHONE权限的请求码
    public static final int CODE_REQUEST_PERMISSION_MICROPHONE = 0x06;
    //请求SMS权限的请求码
    public static final int CODE_REQUEST_PERMISSION_SMS = 0x07;
    //请求相机和STORAGE权限的请求码
    public static final int CODE_REQUEST_PERMISSION_C_S = 0x08;
    //请求4种权限的请求码
    public static final int CODE_REQUEST_PERMISSION_ALL = 0x09;

    /**
     * 判断全部必要权限是否缺失
     *
     * @param activity
     * @return
     */
    public static boolean lacksNecessaryPermissions(Activity activity){
        return lacksPermissions(activity, necessaryPermissions);
    }

    /**
     * 申请必要权限(one by one)
     * 顺序:LOCATION、PHONE、STORAGE
     *
     * @param activity
     */
    public static void requestNecessaryPermissionOneByOne(Activity activity){
        requestAllPermission(activity);
    }

    /**
     * 检查LOCATION权限是否获取
     *
     * @param activity
     * @param needApply 检查到没有权限时是否需要去申请权限
     *                  申请权限后，需要复写Activity中的onRequestPermissionsResult方法监听用户操作
     * @return
     */
    public static boolean checkLocationPermission(Activity activity, boolean needApply){
        if(needApply){
            return checkAndRequestPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION, CODE_REQUEST_PERMISSION_LOCATION);
        }else{
            return !lacksPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    /**
     * 检查PHONE权限是否获取
     *
     * @param activity
     * @param needApply 检查到没有权限时是否需要去申请权限
     *                  申请权限后，需要复写Activity中的onRequestPermissionsResult方法监听用户操作
     * @return
     */
    public static boolean checkPhonePermission(Activity activity, boolean needApply){
        if(needApply){
            return checkAndRequestPermission(activity, Manifest.permission.READ_PHONE_STATE, CODE_REQUEST_PERMISSION_PHONE);
        }else{
            return !lacksPermission(activity, Manifest.permission.READ_PHONE_STATE);
        }
    }

    /**
     * 检查STORGE权限是否获取
     *
     * @param activity
     * @param needApply 检查到没有权限时是否需要去申请权限
     *                  申请权限后，需要复写Activity中的onRequestPermissionsResult方法监听用户操作
     * @return
     */
    public static boolean checkStoragePermission(Activity activity, boolean needApply){
        if(needApply){
            return checkAndRequestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, CODE_REQUEST_PERMISSION_STORAGE);
        }else{
            return !lacksPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * 检查CONTACTS权限是否获取
     *
     * @param activity
     * @param needApply 检查到没有权限时是否需要去申请权限
     *                  申请权限后，需要复写Activity中的onRequestPermissionsResult方法监听用户操作
     * @return
     */
    public static boolean checkContactsPermission(Activity activity, boolean needApply){
        if(needApply){
            return checkAndRequestPermission(activity, Manifest.permission.READ_CONTACTS, CODE_REQUEST_PERMISSION_CONTACTS);
        }else{
            return !lacksPermission(activity, Manifest.permission.READ_CONTACTS);
        }
    }

    /**
     * 检查CAMEAR权限是否获取
     *
     * @param activity
     * @param needApply 检查到没有权限时是否需要去申请权限
     *                  申请权限后，需要复写Activity中的onRequestPermissionsResult方法监听用户操作
     * @return
     */
    public static boolean checkCamearPermission(Activity activity, boolean needApply){
        if(needApply){
            return checkAndRequestPermission(activity, Manifest.permission.CAMERA, CODE_REQUEST_PERMISSION_CAMEAR);
        }else{
            return !lacksPermission(activity, Manifest.permission.CAMERA);
        }
    }

    /**
     * 检查MICROPHONE权限是否获取
     *
     * @param activity
     * @param needApply 检查到没有权限时是否需要去申请权限
     *                  申请权限后，需要复写Activity中的onRequestPermissionsResult方法监听用户操作
     * @return
     */
    public static boolean checkMicrophonePermission(Activity activity, boolean needApply){
        if(needApply){
            return checkAndRequestPermission(activity, Manifest.permission.RECORD_AUDIO, CODE_REQUEST_PERMISSION_MICROPHONE);
        }else{
            return !lacksPermission(activity, Manifest.permission.RECORD_AUDIO);
        }
    }

    /**
     * 检查SMS权限是否获取
     *
     * @param activity
     * @param needApply 检查到没有权限时是否需要去申请权限
     *                  申请权限后，需要复写Activity中的onRequestPermissionsResult方法监听用户操作
     * @return
     */
    public static boolean checkSMSPermission(Activity activity, boolean needApply){
        if(needApply){
            return checkAndRequestPermission(activity, Manifest.permission.SEND_SMS, CODE_REQUEST_PERMISSION_SMS);
        }else{
            return !lacksPermission(activity, Manifest.permission.SEND_SMS);
        }
    }

    /**
     * 检查指定权限，若没有授权，则去申请
     *
     * @param activity
     * @param permission
     * @param request_code
     * @return
     */
    private static boolean checkAndRequestPermission(Activity activity, String permission, int request_code){
        if(lacksPermission(activity, permission)){
            ActivityCompat.requestPermissions(activity, new String[]{permission}, request_code);
            return false;
        }
        return true;
    }

    /**
     * 申请LOCATION权限
     *
     * @param activity
     */
    private static void requestLocationPermission(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CODE_REQUEST_PERMISSION_LOCATION);
    }

    /**
     * 申请PHONE权限
     *
     * @param activity
     */
    public static void requestPhonePermission(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, CODE_REQUEST_PERMISSION_PHONE);
    }

    /**
     * 申请4种权限权限
     *
     * @param activity
     */
    public static void requestAllPermission(Activity activity){
        ActivityCompat.requestPermissions(activity, necessaryPermissions, CODE_REQUEST_PERMISSION_ALL);
    }

    /**
     * 申请相机权限和STORAGE权限
     *
     * @param activity
     */
    public static void requestCameraStoragePermission(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_REQUEST_PERMISSION_C_S);
    }

    /**
     * 申请STORAGE权限
     *
     * @param activity
     */
    public static void requestStoragePermission(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_REQUEST_PERMISSION_STORAGE);
    }

    /**
     * 申请相机权限
     *
     * @param activity
     */
    public static void requestCameraPermission(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CODE_REQUEST_PERMISSION_CAMEAR);
    }

    /**
     * 判断多个权限是否已缺失
     *
     * @param activity
     * @param permissions
     * @return
     */
    public static boolean lacksPermissions(Activity activity, String[] permissions){
        for(String permission : permissions){
            if(lacksPermission(activity, permission)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个权限是否缺失
     *
     * @param activity
     * @param permission
     * @return
     */
    private static boolean lacksPermission(Activity activity, String permission){
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED;
    }
}
