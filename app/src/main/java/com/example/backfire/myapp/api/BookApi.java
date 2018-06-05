package com.example.backfire.myapp.api;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by backfire on 2017/9/30.
 */

public interface BookApi {
    /**
     * http://www.shuwu.mobi/tag/azw3/page/3
     *
     * http://www.shuwu.mobi/category/jpwlxs/lsjk
     * http://www.shuwu.mobi/category/jpwlxs/lsjk/page/2
     *
     * http://www.shuwu.mobi/category/duokan
     * http://www.shuwu.mobi/category/duokan/page/2
     *
     * http://www.shuwu.mobi/?s=%E8%AE%BA%E8%AF%AD
     * @param date
     * @param page
     * @return
     */
    @GET("/date/{date}/page/{page}")
    rx.Observable<ResponseBody> getBookResponseBody(@Path("date") String date,@Path("page")int page);
    @GET("/date/{date}/page/{page}")
    rx.Observable<ResponseBody> getButtonResponseBody(@Path("date") String date,@Path("page")int page);

    @GET("/tag/{tag}/page/{page}")
    rx.Observable<ResponseBody> getTagResponseBody(@Path("tag") String tag,@Path("page")int page);

    @GET("?")
    rx.Observable<ResponseBody> getSearchResponseBody(@Query("s") String bookName);



}
