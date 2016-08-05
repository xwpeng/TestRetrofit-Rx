package xwpeng.android.testrx.entity;

import java.util.List;

/**
 * Created by xwpeng on 16-8-5.
 */
public class Result {

    /**
     * resultcode : 101
     * reason : KEY ERROR!
     * result : []
     * error_code : 10001
     */
    public String resultCode;
    public String reason;
    public int errorCode;
    public List<String> contents;

    @Override
    public String toString() {
        return "Result{" +
                "resultCode='" + resultCode + '\'' +
                ", reason='" + reason + '\'' +
                ", errorCode=" + errorCode +
                ", contents=" + contents +
                '}';
    }
}
