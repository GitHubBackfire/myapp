package com.example.backfire.myapp.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by backfire on 2017/9/30.
 */

public class ApiManager {

    private static final String BOOK_STORE_URL = "http://www.shuwu.mobi";
    private static final String FICTION_TEXT_URL = "http://www.wcsfa.com";

    private static final Interceptor REWRITE_CHAHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return null;
        }
    };

    private static ApiManager apiManager;

    public static ApiManager getInstance() {
        if (apiManager == null) {
            synchronized (ApiManager.class) {
                if (apiManager == null) {
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    private BookApi bookApi;
    private FictionApi fictionApi;
    private Object bookMonitor = new Object();
    private Object fictionMonitor = new Object();

    private static OkHttpClient client = new OkHttpClient.Builder()
            //.addNetworkInterceptor(REWRITE_CHAHE_CONTROL_INTERCEPTOR)
            //.addInterceptor(REWRITE_CHAHE_CONTROL_INTERCEPTOR)
            .build();

    public BookApi getBookApiService() {
        if (bookApi == null) {
            synchronized (bookMonitor) {
                if (bookApi == null) {
                    bookApi = new Retrofit.Builder()
                            .baseUrl(BOOK_STORE_URL)
                            .client(client)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build().create(BookApi.class);
                }
            }
        }
        return bookApi;
    }

    public FictionApi getFictionService() {
        if (fictionApi == null) {
            synchronized (fictionMonitor) {
                if (fictionApi == null) {
                    fictionApi = new Retrofit.Builder()
                            .baseUrl(FICTION_TEXT_URL)
                            .client(client)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build().create(FictionApi.class);

                }
            }
        }
        return  fictionApi;
    }


}
