package xwpeng.android.testrx.present.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import xwpeng.android.testrx.entity.Result;

/**
 * Created by xwpeng on 16-8-5.
 */
public class ResultConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, Result> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new ResultConverter();
    }
}
