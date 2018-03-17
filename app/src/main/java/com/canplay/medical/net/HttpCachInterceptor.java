package com.canplay.medical.net;

import com.canplay.medical.base.ApplicationConfig;
import com.canplay.medical.util.CanplayUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HttpCach 缓存拦截器
 * Created by leo on 2016/9/2.
 */
public class HttpCachInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!CanplayUtils.isNetworkAccessiable(ApplicationConfig.context)) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            Logger.w("no network");
        }
        Response originalResponse = chain.proceed(request);
        if (CanplayUtils.isNetworkAccessiable(ApplicationConfig.context)) { //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder().removeHeader("Pragma").header("Cache-Control", cacheControl).build();
        } else {
            return originalResponse.newBuilder().removeHeader("Pragma").header("Cache-Control", "public, only-if-cached, max-stale=2419200").build();
        }
    }
}
