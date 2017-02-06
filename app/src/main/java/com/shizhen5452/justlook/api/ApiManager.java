package com.shizhen5452.justlook.api;

import com.shizhen5452.justlook.utils.BaseUrls;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ApiManager {
    private Object mObject=new Object();
    public static ApiManager sApiManager;
    public ZhihuApi mZhihuApi;

    public static ApiManager getInstance() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager=new ApiManager();
                }
            }
        }
        return sApiManager;
    }

    public ZhihuApi getZhihuApiService() {
        if (mZhihuApi == null) {
            synchronized (mObject) {
                if (mZhihuApi == null) {
                    mZhihuApi=new Retrofit
                            .Builder()
                            .baseUrl(BaseUrls.ZHIHU_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ZhihuApi.class);
                }
            }
        }
        return mZhihuApi;
    }

}
