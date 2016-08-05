package xwpeng.android.testrx.present;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import xwpeng.android.testrx.R;

/**
 * Created by xwpeng on 16-8-4.
 */
public class TestRx {
    private final static String TAG = TestRx.class.getSimpleName();

    private static Observable create() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onNext("xwpeng");
                subscriber.onCompleted();
            }
        });
    }

    private static Observable justCreate() {
        return Observable.just("Hello", "Wrold", "xwpeng");
    }

    private static Observable fromCreate() {
        String[] a = new String[]{"Hello", "World", "xwpeng"};
        return Observable.from(a);
    }

    public static void base(final Context context, final ImageView imageView) {
        if (imageView == null) return;
//        create()
//        justCreate()
        fromCreate().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, s);
            }
        });

        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
                Log.d(TAG, "current thread id: " + Thread.currentThread().getId());
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
                imageView.setImageDrawable(drawable);
                Log.d(TAG, "current thread id: " + Thread.currentThread().getId());
            }
        });

        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "" + integer);
                    }
                });

        Observable.just(R.mipmap.ic_launcher)
                .map(new Func1<Integer, Drawable>() {

                    @Override
                    public Drawable call(Integer integer) {
                        return context.getResources().getDrawable(integer);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Drawable>() {
                    @Override
                    public void call(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
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
