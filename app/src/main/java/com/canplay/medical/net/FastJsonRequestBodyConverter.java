package com.canplay.medical.net;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 *
 * @param <T>
 */
public class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody>{

  private Type type;
  private Charset charset;
  private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

  public FastJsonRequestBodyConverter(Type type, Charset charset) {
    this.type = type;
    this.charset = charset;
  }

  @Override
  public RequestBody convert(T value) throws IOException {

    Logger.d("convert:"+value.toString());
    return RequestBody.create(MEDIA_TYPE, JSON.toJSONString(value).getBytes(charset));
  }
}
