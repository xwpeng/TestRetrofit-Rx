package xwpeng.android.testrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import xwpeng.android.testrx.present.TestRetrofitRx;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageView;
    private ImageView mImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        findViewById(R.id.button1).setOnClickListener(this);
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.main_imageview);
        mImageView2 = (ImageView) findViewById(R.id.main_imageview2);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
//                TestRx.initRx(getApplicationContext(), mImageView);
//                TestRetrofit.initRetrofit();
                TestRetrofitRx.initRetrofitRx();
                break;
        }
    }
}
