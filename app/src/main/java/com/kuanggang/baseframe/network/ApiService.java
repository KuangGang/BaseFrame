package com.kuanggang.baseframe.network;


import com.kuanggang.baseframe.BuildConfig;
import com.kuanggang.baseframe.Constants;
import com.kuanggang.baseframe.AppApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author KG on 2017/6/8.
 */

public class ApiService {

    private static Api mApi = init();

    private static Api init(){
        File cacheFile = new File(AppApplication.application.getCacheDir(), "HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 20); // 20M

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(Constants.READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new CacheInterceptor())
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(cache);

        if (BuildConfig.isDebug){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(Api.class);
    }

    public static Api getApi(){
        return mApi;
    }
}
