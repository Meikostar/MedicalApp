package com.canplay.medical.mvp.http;



import com.canplay.medical.bean.USER;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;


public interface BaseApi {


    /**
     * Login
     * @param options
     * @return
     */
    @POST("VerifyMobileNumber/")
    Observable<USER> Login(@QueryMap Map<String, String> options);



}
