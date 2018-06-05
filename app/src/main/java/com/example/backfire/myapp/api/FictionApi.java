package com.example.backfire.myapp.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by backfire on 2017/11/18.
 */

public interface FictionApi {
  //  @GET("{urlname}")
   // rx.Observable<ResponseBody> getFictionResponseBody(@Path("urlname") String urlName);
    @GET
    rx.Observable<ResponseBody> getFictionResponseBody(@Url String urlName);
}
