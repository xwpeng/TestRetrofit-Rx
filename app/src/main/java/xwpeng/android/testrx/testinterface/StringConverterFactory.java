package xwpeng.android.testrx.testinterface;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by xwpeng on 16-8-16.
 */
public class StringConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, String> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new StringConverter();
    }
}
