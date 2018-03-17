package com.canplay.medical.net;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @param <T>
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T>{

    private Type type;
    private Charset charset;

    public FastJsonResponseBodyConverter(){
    }

    public FastJsonResponseBodyConverter(Type type, Charset charset){
        this.type = type;
        this.charset = charset;
    }

    @Override
    public T convert(ResponseBody value) throws IOException{
        String errorMsg = "";
        int errorCode = 0;
        try{
            Logger.d("convert:" + value.toString());
//            BaseEntity baseEntity = JSON.parseObject(value.string(), BaseEntity.class);
//            Logger.d("convert:" + baseEntity.toString());
            return (T)JSON.parseObject(value.string(), type);
//            if(baseEntity.getCode() == 0){
//                if(baseEntity.getData() == null)
//                    return null;
//                if(type != BaseEntity.class && "".equals(baseEntity.getData())){
//                    return null;
//                }
//
//            }else if(baseEntity.getCode() == 1001){
//                //弹系统文案
//                errorMsg = baseEntity.getMsg();
//            }else if((baseEntity.getCode() >= 1 && baseEntity.getCode() <= 99) || (baseEntity.getCode() >= 1000 && baseEntity.getCode() <= 1999) || (baseEntity.getCode() >= 9000 && baseEntity.getCode() <= 9999)){
//                //return (T) baseEntity;
//                //弹客户端文案
//                errorMsg = "登录失效，请重新登录";
//            }else if((baseEntity.getCode() >= 100 && baseEntity.getCode() <= 999) || (baseEntity.getCode() >= 2000 && baseEntity.getCode() <= 8999)){
//                //弹系统文案
//                errorMsg = baseEntity.getMsg();
//            }else{
//                errorMsg = "系统异常";
//            }
//            throw new IOException(errorMsg);
        }catch(NullPointerException exception){
            throw new IOException("系统异常");
        }
        finally{
            value.close();
        }
    }
}
