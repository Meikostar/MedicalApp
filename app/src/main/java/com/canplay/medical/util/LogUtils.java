package com.canplay.medical.util;

import android.util.Log;


/**
 * 管理Log 的类.
 */
public class LogUtils {
    private static final String TAG = "ChaoJiPi";
    private static boolean DEBUG=true;
    public static void d(String TAG, String content){
        if(DEBUG){
            if (StringUtil.isNotEmpty(content)){
                Log.d(TAG, content);
            }

//            LogToFile.init(YunShlApplication.mYunShlApplication);
//            LogToFile.d(TAG,content);
        }
    }
    public static void e(String TAG, String content){
        if(DEBUG){
            if (StringUtil.isNotEmpty(content)){
                Log.e(TAG, content);
            }

//            LogToFile.init(YunShlApplication.mYunShlApplication);
//            LogToFile.e(TAG,content);
        }
    }

    public static void i(String TAG, String content){
        if(DEBUG){
            if (StringUtil.isNotEmpty(content)){
                Log.i(TAG, content);
            }

//            LogToFile.init(YunShlApplication.mYunShlApplication);
//            LogToFile.e(TAG,content);
        }
    }

    public static void i(String content){
        if(DEBUG){
            if (StringUtil.isNotEmpty(content)){
                Log.i(TAG, content);
            }

//            LogToFile.init(YunShlApplication.mYunShlApplication);
//            LogToFile.e(TAG,content);
        }
    }

    public static void w(String TAG, String content){
        if(DEBUG){
            if (StringUtil.isNotEmpty(content)){
                Log.w(TAG, content);
            }
        }
    }
    public static void w(String content){
        if(DEBUG){
            if (StringUtil.isNotEmpty(content)){
                Log.w(TAG, content);
            }

//            LogToFile.init(YunShlApplication.mYunShlApplication);
//            LogToFile.e(TAG,content);
        }
    }


//    public static void writeLogToFile(final String TAG, final String content){
//        LogToFile.init(YunShlApplication.mYunShlApplication);
//        LogToFile.d(TAG,content);
//    }

}
