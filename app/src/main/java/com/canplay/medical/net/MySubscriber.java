package com.canplay.medical.net;

import android.content.Context;
import android.net.ParseException;
import android.widget.Toast;

import com.canplay.medical.R;
import com.canplay.medical.util.CanplayUtils;
import com.canplay.medical.view.ProgressDialog;
import com.google.gson.JsonParseException;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * Created by peter on 2016/9/19.
 */

public abstract class MySubscriber<T> extends Subscriber<T>{

    private Context context;

    private ProgressDialog mProgressDialog;

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int GATEWAY_TIMEOUT = 504;

    protected MySubscriber(Context context){
        super();
        this.context = context;
        mProgressDialog = new ProgressDialog(context);
    }

    protected MySubscriber(Context context, int a){
        super();
        this.context = context;
    }

    protected MySubscriber(){
        super();
    }

    public void showProgressBar(){
        if(mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void stopProgressBar(){
        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void onStart(){
        super.onStart();
        Logger.e("onStart");
        showProgressBar();
    }

    @Override
    public void onCompleted(){
        Logger.e("onCompleted");
        stopProgressBar();
    }

    @Override
    public void onError(Throwable e){
        Logger.e("onError:" + e.getMessage());
        stopProgressBar();
        if(context == null)
            return;
        if(!CanplayUtils.isNetworkAccessiable(context)){
            Toast.makeText(context, R.string.network_error, Toast.LENGTH_SHORT).show();
            return;
        }else if(e instanceof HttpException){
            //HTTP错误
            HttpException httpException = (HttpException) e;
            switch(httpException.code()){
                case REQUEST_TIMEOUT:
                    Toast.makeText(context, R.string.request_timeout, Toast.LENGTH_SHORT).show();
                    break;
                case INTERNAL_SERVER_ERROR:
                    Toast.makeText(context, R.string.internal_server_error, Toast.LENGTH_SHORT).show();
                    break;
            }
            return;
        }else if(e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException){
            //均视为解析错误
            return;
        }else if(e instanceof ConnectException){
            Toast.makeText(context, R.string.internal_server_error, Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
