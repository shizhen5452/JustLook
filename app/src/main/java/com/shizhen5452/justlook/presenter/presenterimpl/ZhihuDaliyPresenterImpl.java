package com.shizhen5452.justlook.presenter.presenterimpl;

import com.shizhen5452.justlook.api.ApiManager;
import com.shizhen5452.justlook.bean.ZhihuDaliyBean;
import com.shizhen5452.justlook.presenter.ZhihuDaliyPresenter;
import com.shizhen5452.justlook.utils.DateUtils;
import com.shizhen5452.justlook.view.ZhihuDaliyView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ZhihuDaliyPresenterImpl implements ZhihuDaliyPresenter {
    private ZhihuDaliyView mZhihuDaliyView;
    private List<ZhihuDaliyBean.StoriesBean> mAllStoriesBeanList=new ArrayList<>();

    public ZhihuDaliyPresenterImpl(ZhihuDaliyView zhihuDaliyView) {
        mZhihuDaliyView = zhihuDaliyView;
    }

    @Override
    public void initZhihu() {
        ApiManager.getInstance().getZhihuApiService().getLastDaliy().enqueue(new Callback<ZhihuDaliyBean>() {
            @Override
            public void onResponse(Call<ZhihuDaliyBean> call, Response<ZhihuDaliyBean> response) {
                if (response.isSuccessful()) {
                    ZhihuDaliyBean zhihuDaliyBean = response.body();
                    List<ZhihuDaliyBean.StoriesBean> stories = zhihuDaliyBean.getStories();
                    mAllStoriesBeanList.clear();
                    mAllStoriesBeanList.addAll(stories);
                    mZhihuDaliyView.onInitZhihu(zhihuDaliyBean,mAllStoriesBeanList);
                } else {
                    mZhihuDaliyView.onError();
                }
            }

            @Override
            public void onFailure(Call<ZhihuDaliyBean> call, Throwable t) {
                mZhihuDaliyView.onError();
            }
        });
    }

    @Override
    public void loadMore(String date) {
        String beforeDate = DateUtils.getBeforeDate(date);
        ApiManager.getInstance().getZhihuApiService().getBeforeDetail(date).enqueue(new Callback<ZhihuDaliyBean>() {
            @Override
            public void onResponse(Call<ZhihuDaliyBean> call, Response<ZhihuDaliyBean> response) {
                if (response.isSuccessful()) {
                    ZhihuDaliyBean zhihuDaliyBean = response.body();
                    List<ZhihuDaliyBean.StoriesBean> stories = zhihuDaliyBean.getStories();
                    mAllStoriesBeanList.addAll(stories);
                    mZhihuDaliyView.onLoadMore(zhihuDaliyBean);
                } else {
                    mZhihuDaliyView.onError();
                }
            }

            @Override
            public void onFailure(Call<ZhihuDaliyBean> call, Throwable t) {
                mZhihuDaliyView.onError();
            }
        });
    }
}
