package com.shizhen5452.justlook.api;

import com.shizhen5452.justlook.bean.ZhihuDaliyBean;
import com.shizhen5452.justlook.bean.ZhihuDetailBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Create by EminemShi on 2017/2/6
 */

public interface ZhihuApi {
    @GET("news/latest")
    Call<ZhihuDaliyBean> getLastDaliy();

    @GET("news/{id}")
    Call<ZhihuDetailBean> getZhihuDetail(@Path("id") int id);

    @GET("news/before/{date}")
    Call<ZhihuDaliyBean> getBeforeDetail(@Path("date") String date);
}
