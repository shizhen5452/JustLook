package com.shizhen5452.justlook.presenter.presenterimpl;

import android.content.Context;

import com.google.gson.Gson;
import com.shizhen5452.justlook.api.ApiManager;
import com.shizhen5452.justlook.bean.ZhihuDaliyBean;
import com.shizhen5452.justlook.bean.ZhihuDaliyItemBean;
import com.shizhen5452.justlook.presenter.ZhihuDaliyPresenter;
import com.shizhen5452.justlook.utils.CacheUtil;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.ThreadUtils;
import com.shizhen5452.justlook.view.ZhihuDaliyView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ZhihuDaliyPresenterImpl implements ZhihuDaliyPresenter {
    private Context mContext;
    private ZhihuDaliyView mZhihuDaliyView;
    private final CacheUtil mCacheUtil;
    private Gson mGson=new Gson();

    public ZhihuDaliyPresenterImpl(Context context, ZhihuDaliyView zhihuDaliyView) {
        mContext=context;
        mZhihuDaliyView = zhihuDaliyView;
        mCacheUtil = CacheUtil.get(context);
    }

    @Override
    public void initZhihu() {
        mZhihuDaliyView.showProgressBar();
        ApiManager.getInstance().getZhihuApiService().getLastDaliy().enqueue(new Callback<ZhihuDaliyBean>() {
            @Override
            public void onResponse(Call<ZhihuDaliyBean> call, Response<ZhihuDaliyBean> response) {
                if (response.isSuccessful()) {
                    ZhihuDaliyBean zhihuDaliyBean = response.body();
                    String date = zhihuDaliyBean.getDate();
                    for (ZhihuDaliyItemBean zhihuDaliyItemBean : zhihuDaliyBean.getStories()) {
                        zhihuDaliyItemBean.setDate(date);
                    }
                    mCacheUtil.put(Constant.ZHIHU,mGson.toJson(zhihuDaliyBean));
                    mZhihuDaliyView.onInitZhihu(zhihuDaliyBean);
                } else {
                    mZhihuDaliyView.onError();
                }
                mZhihuDaliyView.hideProgressBar();
            }

            @Override
            public void onFailure(Call<ZhihuDaliyBean> call, Throwable t) {
                mZhihuDaliyView.onError();
                mZhihuDaliyView.hideProgressBar();
            }
        });
    }

    @Override
    public void loadMore(final String date) {
        ThreadUtils.postDelayThread(new Runnable() {
            @Override
            public void run() {
                ApiManager.getInstance().getZhihuApiService().getBeforeDetail(date).enqueue(new Callback<ZhihuDaliyBean>() {
                    @Override
                    public void onResponse(Call<ZhihuDaliyBean> call, Response<ZhihuDaliyBean> response) {
                        if (response.isSuccessful()) {
                            ZhihuDaliyBean zhihuDaliyBean = response.body();
                            String date = zhihuDaliyBean.getDate();
                            for (ZhihuDaliyItemBean zhihuDaliyItemBean : zhihuDaliyBean.getStories()) {
                                zhihuDaliyItemBean.setDate(date);
                            }
                            mZhihuDaliyView.onLoadMore(zhihuDaliyBean);
                        } else {
                            mZhihuDaliyView.onLoadMoreError();
                        }
                    }

                    @Override
                    public void onFailure(Call<ZhihuDaliyBean> call, Throwable t) {
                        mZhihuDaliyView.onLoadMoreError();
                    }
                });
            }
        });

    }

    @Override
    public void loadCache() {
        ZhihuDaliyBean zhihuDaliyBean = mGson.fromJson(mCacheUtil.getAsJSONObject(Constant.ZHIHU).toString(), ZhihuDaliyBean.class);
        mZhihuDaliyView.onInitZhihu(zhihuDaliyBean);
    }
}
