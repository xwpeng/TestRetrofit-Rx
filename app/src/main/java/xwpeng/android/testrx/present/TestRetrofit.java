package xwpeng.android.testrx.present;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xwpeng.android.testrx.entity.Repo2;

/**
 * Created by xwpeng on 16-8-4.
 */
public class TestRetrofit {
    private final static String TAG = TestRetrofit.class.getSimpleName();
    public static void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
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
                    Log.d(TAG, response.body().string());
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
}
