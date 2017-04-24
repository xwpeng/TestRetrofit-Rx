package xwpeng.android.testrx.testinterface;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by xwpeng on 16-8-16.
 */
public class StringConverter implements Converter<ResponseBody, String> {

    @Override
    public String convert(ResponseBody value)  {
        String re = null;
        try {
            re = value.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return re;
    }
}
