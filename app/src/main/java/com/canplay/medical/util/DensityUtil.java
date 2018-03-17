package com.canplay.medical.util;

import android.content.Context;

import com.canplay.medical.base.BaseApplication;

/**
 * Created by qi_fu on 2017/12/31.
 */

public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float getWidth(Context context){
        if (context==null){
            return 0;
        }
        float widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        return widthPixels;
    }

    public static float getHeight(Context context){
        if (context==null){
            return 0;
        }
        float heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        return heightPixels;
    }


    public static int px2dip(int pxValue) {

        final float scale = BaseApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale =  BaseApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
