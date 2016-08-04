package xwpeng.android.testrx;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import xwpeng.android.testrx.entity.Repo2;

/**
 * Created by xwpeng on 16-8-3.
 */
public interface GithubService {
        @GET("users/{user}/repos")
        Call<List<Repo2>> listRepos(@Path("user") String user);
        @GET("users/{user}/repos")
        Observable<List<Repo2>> listReposRx(@Path("user") String user);
}
