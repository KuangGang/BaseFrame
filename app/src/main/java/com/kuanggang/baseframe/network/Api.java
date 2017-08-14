package com.kuanggang.baseframe.network;

import com.kuanggang.baseframe.model.GankCategory;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author KG on 2017/6/8.
 */

public interface Api {

    @GET("data/{category}/{size}/{page}")
    Observable<GankCategory> getGankListByCategory(@Path("category") String category, @Path("page") int page, @Path("size") int size);

}
