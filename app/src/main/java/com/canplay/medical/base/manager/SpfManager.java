package com.canplay.medical.base.manager;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreference管理
 * Created by peter on 2016/9/11.
 */
public class SpfManager {

    private Application mApplication;

    public SpfManager(Application application){
        mApplication = application;
    }

    public SharedPreferences getSharedPreferences(){
      return  PreferenceManager.getDefaultSharedPreferences(mApplication);
    }
}
