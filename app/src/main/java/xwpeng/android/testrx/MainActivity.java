package xwpeng.android.testrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import xwpeng.android.testrx.present.TestRetrofit;
import xwpeng.android.testrx.present.TestRetrofitRx;
import xwpeng.android.testrx.present.TestRx;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageView;
    private ImageView mImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        findViewById(R.id.testRx_button).setOnClickListener(this);
        findViewById(R.id.testRetrofit_button).setOnClickListener(this);
        findViewById(R.id.testRetrofitRx_button).setOnClickListener(this);
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.main_imageview);
        mImageView2 = (ImageView) findViewById(R.id.main_imageview2);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testRx_button:
                TestRx.base(getApplicationContext(), mImageView);
                break;
            case R.id.testRetrofit_button:
//                TestRetrofit.base();
//                  TestRetrofit.query();
//                TestRetrofit.queryMap();
//                TestRetrofit.responseConverter();
                TestRetrofit.annotationURl();
                break;
            case R.id.testRetrofitRx_button:
                TestRetrofitRx.base();
                break;
        }
    }
}
