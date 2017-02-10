package com.shizhen5452.justlook.presenter.presenterimpl;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;

import com.shizhen5452.justlook.activity.ZhihuDetailActivity;
import com.shizhen5452.justlook.api.ApiManager;
import com.shizhen5452.justlook.bean.ZhihuDetailBean;
import com.shizhen5452.justlook.presenter.ZhihuDetailPresenter;
import com.shizhen5452.justlook.view.ZhihuDetailView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create by EminemShi on 2017/2/7
 */

public class ZhihuDetailPresenterImpl implements ZhihuDetailPresenter {
    private ZhihuDetailView mZhihuDetailView;

    public ZhihuDetailPresenterImpl(ZhihuDetailView zhihuDetailView) {
        mZhihuDetailView = zhihuDetailView;
    }

    @Override
    public void initDetail(int id) {
        ApiManager.getInstance().getZhihuApiService().getZhihuDetail(id).enqueue(new Callback<ZhihuDetailBean>() {
            @Override
            public void onResponse(Call<ZhihuDetailBean> call, Response<ZhihuDetailBean> response) {
                if (response.isSuccessful()) {
                    ZhihuDetailBean zhihuDetailBean = response.body();
                    mZhihuDetailView.onInitDetail(zhihuDetailBean);
                } else {
                    mZhihuDetailView.onError();
                }
            }

            @Override
            public void onFailure(Call<ZhihuDetailBean> call, Throwable t) {
                mZhihuDetailView.onError();
            }
        });
    }

    @Override
    public void openUrl(WebView view, String url) {
        ((ZhihuDetailActivity) mZhihuDetailView).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
    }
}
