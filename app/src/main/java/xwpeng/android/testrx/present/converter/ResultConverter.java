package xwpeng.android.testrx.present.converter;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import xwpeng.android.testrx.entity.Result;

/**
 * Created by xwpeng on 16-8-5.
 */
public class ResultConverter implements Converter<ResponseBody, Result>{

    @Override
    public Result convert(ResponseBody value) throws IOException {
        String valueString = value.string();
        NetResult netResult = new Gson().fromJson(valueString, NetResult.class);
        Result result = new Result();
        result.resultCode = netResult.resultcode;
        result.reason = netResult.reason;
        result.errorCode = netResult.error_code;
        result.contents = netResult.result;
        return result;
    }

    static class NetResult {
        /**
         * resultcode : 101
         * reason : KEY ERROR!
         * result : []
         * error_code : 10001
         */
        public String resultcode;
        public String reason;
        public int error_code;
        public List<String> result;
    }
}
