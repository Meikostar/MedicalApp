package com.canplay.medical.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Leo on 2016/12/23.
 */
public abstract class BaseService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }



    @Override
    public void onCreate(){
        super.onCreate();
        initInjector();
    }

    /*
   绑定依赖
    */
    public abstract void initInjector();
}
