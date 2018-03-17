package com.canplay.medical.mvp.present;


import android.support.annotation.NonNull;


import com.canplay.medical.base.manager.ApiManager;
import com.canplay.medical.bean.USER;
import com.canplay.medical.mvp.http.BaseApi;

import com.canplay.medical.net.MySubscriber;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import rx.Subscription;


public class LoginPresenter implements LoginContract.Presenter {
    private Subscription subscription;

    private LoginContract.View mView;

    private BaseApi contactApi;

    @Inject
    LoginPresenter(ApiManager apiManager){
        contactApi = apiManager.createApi(BaseApi.class);
    }
    @Override
    public void goLogin(String account, String pwd) {
        Map<String, String> params = new TreeMap<>();
        params.put("account", account);
        params.put("pwd", pwd);
        subscription = ApiManager.setSubscribe(contactApi.Login(ApiManager.getParameters(params, true)), new MySubscriber<USER>(){
            @Override
            public void onError(Throwable e){
                super.onError(e);
                if(e.toString().contains("java.io.IOException:")){
                    mView.showTomast("账号或密码错误");
                }

            }

            @Override
            public void onNext(USER entity){
                mView.toEntity(entity);

            }
        });
    }
    @Override
    public void getToken() {

    }
    @Override
    public void attachView(@NonNull LoginContract.View view){
        mView = view;
    }


    @Override
    public void detachView(){
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        mView = null;
    }
}
