package com.canplay.medical.base.manager;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.canplay.medical.base.config.BaseConfig;
import com.canplay.medical.base.config.NetConfig;
import com.canplay.medical.net.FastJsonConverterFactory;
import com.canplay.medical.net.HttpCachInterceptor;
import com.canplay.medical.net.MarvelSignInterceptor;
import com.canplay.medical.util.CryptUtil;
import com.orhanobut.logger.Logger;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 接口管理
 * Created by peter on 2016/9/11.
 */
public class ApiManager{

    private Context context;

    public Retrofit mRetrofit;

    private static ApiManager instance = null;

    Cache cache;

    //设缓存有效期为1天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;

    /**
     * 获取异步实例化对象
     */
    public static ApiManager getInstance(Context context, boolean isRxJava){
        if(null == instance){
            synchronized(ApiManager.class){
                if(null == instance){
                    instance = new ApiManager(context, isRxJava);
                }
            }
        }
        return instance;
    }

    /**
     * 获取同步实例化对象
     */
    public static ApiManager getInstance(Context context){
        if(null == instance){
            synchronized(ApiManager.class){
                if(null == instance){
                    instance = new ApiManager(context);
                }
            }
        }
        return instance;
    }

    private ApiManager(Context context, boolean isRxJava){
        this.context = context;
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 100 * 1024 * 1024;// 100 MiB
        cache = new Cache(httpCacheDirectory, cacheSize);
        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(NetConfig.SERVER_URL + File.separator).
                addConverterFactory(FastJsonConverterFactory.create()).
                client(getOkHttpClient());
        if(isRxJava){
            (builder).addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        }
        this.mRetrofit = builder.build();
    }

    /**
     * web Service网络请求
     */
    private Retrofit mWSRetrofit;

    /**
     * web service 的base url
     */
    public ApiManager(Context context){
        this.context = context;
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 100 * 1024 * 1024;// 100 MiB
        cache = new Cache(httpCacheDirectory, cacheSize);
        //webserice 报文解析
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);
        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(NetConfig.SERVER_URL + File.separator).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                addConverterFactory(SimpleXmlConverterFactory.create(serializer)).
                client(getWSOkHttpClient());
        this.mWSRetrofit = builder.build();
    }

    /**
     * 设置OkHttpClient请求
     */
    private OkHttpClient getOkHttpClient(){
        Logger.d("create OkHttpClient");
        // log用拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        MarvelSignInterceptor marvelSign = new MarvelSignInterceptor();
        HttpCachInterceptor cachInterceptor = new HttpCachInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder().
                addInterceptor(logging).
                addNetworkInterceptor(cachInterceptor).
                addInterceptor(marvelSign).
                connectTimeout(NetConfig.HTTP_TIMEOUT, TimeUnit.SECONDS).
                writeTimeout(NetConfig.HTTP_TIMEOUT, TimeUnit.SECONDS).
                readTimeout(NetConfig.HTTP_TIMEOUT, TimeUnit.SECONDS).
                cache(cache);
        Logger.d("builder OkHttpClient");
        return builder.build();
    }

    /**
     * @return
     */
    private OkHttpClient getWSOkHttpClient(){
        // log用拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        MarvelSignInterceptor marvelSign = new MarvelSignInterceptor();
        if(BaseConfig.ISTEST){
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else{
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        HttpCachInterceptor cachInterceptor = new HttpCachInterceptor();
        return new OkHttpClient.Builder().cache(cache)
                //添加拦截器
                .addInterceptor(logging).addNetworkInterceptor(cachInterceptor).addInterceptor(marvelSign)
                // 连接超时时间设置
                .connectTimeout(NetConfig.HTTP_TIMEOUT, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(NetConfig.HTTP_TIMEOUT, TimeUnit.SECONDS)
                //写超时时间设置
                .writeTimeout(NetConfig.HTTP_TIMEOUT, TimeUnit.SECONDS).build();
    }

    public <T> T createWSApi(Class<T> paramClass){
        return this.mWSRetrofit.create(paramClass);
    }

    public <T> T createApi(Class<T> paramClass){
        return this.mRetrofit.create(paramClass);
    }

    public static <T> Subscription setSubscribe(Observable<T> observable, Observer<T> observer){
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

    /**
     * 请求头参数配置
     * isPost: 是否为post请求
     */
    public static Map<String, String> getParameters(Map<String, String> parametersMap, boolean isPost){
        if(isPost){
            //把用户的userID和token带上
            parametersMap.put("device", AppManager.imei);//设备标识
            parametersMap.put("times", System.currentTimeMillis() + "");
        }
        parametersMap.put("platform", "1"); //手表
        parametersMap.put("version", AppManager.info.versionCode + "");//客户端版本
        parametersMap.put("sign", getSign(parametersMap));
        Logger.d("parameters Json:" + JSONObject.toJSON(parametersMap));
        return parametersMap;
    }

    /*
    计算密钥
     */
    private static String getSign(Map<String, String> parametersMap){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> e : parametersMap.entrySet()){
            if(TextUtils.isEmpty(e.getValue())){
                continue;
            }
            String value = e.getValue().toString().trim();
            String[] valueArr = value.split(String.valueOf(NetConfig.CHARKEY));
            if(valueArr.length <= 1){
                sb.append(e.getKey());
                sb.append("=");
                sb.append(value);
                sb.append("&");
            }else{
                StringBuilder str = new StringBuilder();
                for(String c : valueArr){
                    str.append(c);
                    str.append(String.valueOf(NetConfig.CHARKEY));
                }
                str.delete(str.length() - 1, str.length());
                sb.append(e.getKey());
                sb.append("=");
                sb.append(str.toString());
                sb.append("&");
            }
        }
        int len = sb.length();
        sb.delete(len - 1, len);
        try{
            return CryptUtil.md5(sb.toString() + NetConfig.APP_KEY).toLowerCase().substring(NetConfig.startNum, NetConfig.endNum);
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
