package com.canplay.medical.net;

import com.orhanobut.logger.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by weizilong on 2016/8/26.
 */
public class FastJsonConverterFactory extends Converter.Factory {

  private Charset charset;
  private static final Charset UTF_8 = Charset.forName("UTF-8");

  public static FastJsonConverterFactory create() {
    Logger.d("FastJsonConverterFactory create");
    return create(UTF_8);
  }

  public static FastJsonConverterFactory create(Charset charset) {
    return new FastJsonConverterFactory(charset);
  }

  public FastJsonConverterFactory(Charset charset) {
    this.charset = charset;
  }

  @Override
  public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
    Logger.d("requestBodyConverter:");
    return new FastJsonRequestBodyConverter<>(type, charset);
  }

  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                          Retrofit retrofit) {
    Logger.d("responseBodyConverter:");
    return new FastJsonResponseBodyConverter<>(type, charset);
  }
}
