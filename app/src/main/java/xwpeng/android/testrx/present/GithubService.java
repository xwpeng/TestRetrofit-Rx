package xwpeng.android.testrx.present;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;
import xwpeng.android.testrx.entity.Repo2;
import xwpeng.android.testrx.entity.Result;

/**
 * Created by xwpeng on 16-8-3.
 */
public interface GithubService {
    // https://api.github.com/users/xwpeng/repos
    @GET("users/{user}/repos")
    Call<List<Repo2>> testBase(@Path("user") String user);

    // https://github.com/xwpeng?tab=repositories
    @GET("{user}")
    Call<ResponseBody> testAnnotationQuery(@Path("user") String user, @Query("tab") String tab);

    //https://www.google.com.hk/search?q=Retrofit&oq=Retrofit
    @GET("/")
    Call<ResponseBody> tsetAnnotationQueryMap(@QueryMap Map<String, String> queryMaps);

    @GET
    Call<Result> testAnnotationURl(@Url String url);

    @GET("list.from")
    Call<Result> testResposeConverter();

    @FormUrlEncoded
    @POST("/")
    Call<ResponseBody> testAnnotationPost(@Field("name") String uid, @Field("occupation") String sid);

    @FormUrlEncoded
    @POST("/")
    Call<ResponseBody> testAnnotationPostMap(@FieldMap Map<String, String> fields);

    @Multipart
    @POST("/upload")
    Call<ResponseBody> testUploadPart(@Part("description") ResponseBody description, @Part MultipartBody.Part file);

    @Multipart
    @POST("/upload")
    Call<ResponseBody> testUploadImagePart(@Part("description") ResponseBody description, @Part(value = "image", encoding = "8-bit") RequestBody image);

    @Multipart
    @POST("/upload")
    Call<ResponseBody> testUploadPartMap(@Part("description") ResponseBody description, @PartMap Map<String, RequestBody> files);


    @GET("users/{user}/repos")
    Observable<List<Repo2>> testBaseReposRx(@Path("user") String user);

}
