package xwpeng.android.testrx;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import xwpeng.android.testrx.entity.Repo2;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;
    private ImageView mImageView2;
    private final static String TAG = "xwpeng12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        initRx();
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRetrofit();
            }
        });
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.main_imageview);
        mImageView2 = (ImageView) findViewById(R.id.main_imageview2);
    }

    public void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory()
                .build();
        GithubService service = retrofit.create(GithubService.class);
        final Call<List<Repo2>> repoCall = service.listRepos("xwpeng");
        //同步请求
     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                Response<ResponseBody> response = null;
                try {
                    response = repoCall.execute();
                    Log.e(TAG, response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
        repoCall.enqueue(new Callback<List<Repo2>>() {
            @Override
            public void onResponse(Call<List<Repo2>> call, Response<List<Repo2>> response) {
                try {
                    for (Repo2 repo2 : response.body()) {
                        Log.e(TAG, "enqueue: " + repo2.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Repo2>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

    private void initRx() {
          /*      Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onNext("xwpeng");
                subscriber.onCompleted();
            }
        });
        Observable observable1 = Observable.just("Hello", "Wrold", "xwpeng");*/
        String[] a = new String[]{"Hello", "World", "xwpeng"};
        Observable.from(a).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, s);
            }
        });

        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
                Log.e(TAG, "current thread id: " + Thread.currentThread().getId());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Drawable drawable) {
                mImageView.setImageDrawable(drawable);
                Log.e(TAG, "current thread id: " + Thread.currentThread().getId());
            }
        });

        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "" + integer);
                    }
                });

        Observable.just(R.mipmap.ic_launcher)
                .map(new Func1<Integer, Drawable>() {

                    @Override
                    public Drawable call(Integer integer) {
                        return getResources().getDrawable(integer);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Drawable>() {
                    @Override
                    public void call(Drawable drawable) {
                        mImageView2.setImageDrawable(drawable);
                    }
                });

/*        Student[] students = ...;
        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onNext(Course course) {
                Log.d(tag, course.getName());
            }
            ...
        };
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(subscriber);*/

        //todo compose()

    /*    return observableFactory.<T>toObservable(this)
                .compose(this.<T>transform(observableRequest))
                .observeOn(Schedulers.io())
                .map(new ResponseMetadataOperator<>(this))
                .flatMap(this::mapResponse)
                .observeOn(AndroidSchedulers.mainThread())
                .<AirResponse<T>>compose(group.transform(tag))
                .doOnError(new ErrorLoggingAction(request))
                .doOnError(NetworkUtil::checkForExpiredToken)
                .subscribe(request.observer());*/

    }




}
