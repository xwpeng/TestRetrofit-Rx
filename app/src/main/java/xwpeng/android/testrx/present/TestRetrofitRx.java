package xwpeng.android.testrx.present;

import android.util.Log;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xwpeng.android.testrx.entity.Repo2;

/**
 * Created by xwpeng on 16-8-4.
 */
public class TestRetrofitRx {
    private final static String TAG = TestRetrofitRx.class.getSimpleName();
    public static void base() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GithubService service = retrofit.create(GithubService.class);
        Observable<List<Repo2>> repoCall = service.testBaseReposRx("xwpeng");
        repoCall.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repo2>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<Repo2> repo2s) {
                        for (Repo2 repo2 : repo2s) {
                            Log.d(TAG, "" + repo2.toString());
                        }
                    }
                });
    }
}
