package xwpeng.android.testrx.present;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xwpeng.android.testrx.entity.Repo2;
import xwpeng.android.testrx.entity.Result;
import xwpeng.android.testrx.present.converter.ResultConverterFactory;

/**
 * Created by xwpeng on 16-8-4.
 */
public class TestRetrofit {
    private final static String TAG = TestRetrofit.class.getSimpleName();

    public static void base() {
        final Call<List<Repo2>> repoCall = getService("https://api.github.com/", GsonConverterFactory.create()).testBase("xwpeng");
        //同步请求
      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                Response<List<Repo2>> response = null;
                try {
                    response = repoCall.execute();
                    Log.d(TAG, response.body().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

        //异步
        repoCall.enqueue(new Callback<List<Repo2>>() {
            @Override
            public void onResponse(Call<List<Repo2>> call, Response<List<Repo2>> response) {
                try {
                    for (Repo2 repo2 : response.body()) {
                        Log.d(TAG, "enqueue: " + repo2.toString());
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

    public static void query() {
        getService("https://github.com/", null).testAnnotationQuery("xwpeng", "repositories")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.d(TAG, response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
    }

    public static void queryMap() {
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("q", "Retrofit");
        querys.put("oq", "Retrofit");
        getService("https://www.google.com.hk/search/", null).tsetAnnotationQueryMap(querys)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.d(TAG, response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
    }

    public static void annotationURl() {
        getService("http://dd/", new ResultConverterFactory()).testAnnotationURl("http://japi.juhe.cn/joke/content/list.from")
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.d(TAG, response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
    }

    public static void responseConverter() {
        getService("http://japi.juhe.cn/joke/content/", new ResultConverterFactory()).testResposeConverter()
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.d(TAG, response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
    }

    private static GithubService getService(String baseUrl, Converter.Factory factory) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).client(httpClient.build());
        if (factory != null) {
            builder.addConverterFactory(factory);
        }
        Retrofit retrofit = builder.build();
        return retrofit.create(GithubService.class);
    }

}
