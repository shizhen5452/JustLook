package com.shizhen5452.justlook.presenter.presenterimpl;

import com.shizhen5452.justlook.api.ApiManager;
import com.shizhen5452.justlook.application.MyApplication;
import com.shizhen5452.justlook.bean.ZhihuDaliyBean;
import com.shizhen5452.justlook.presenter.ZhihuDaliyPresenter;
import com.shizhen5452.justlook.utils.ToastUtils;
import com.shizhen5452.justlook.view.ZhihuDaliyView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ZhihuDaliyPresenterImpl implements ZhihuDaliyPresenter {
    private ZhihuDaliyView mZhihuDaliyView;

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
                    mZhihuDaliyView.onInitZhihu(zhihuDaliyBean);
                } else {
                    ToastUtils.showShortToast(MyApplication.getContext(),"网络数据异常");
                }
            }

            @Override
            public void onFailure(Call<ZhihuDaliyBean> call, Throwable t) {
                ToastUtils.showShortToast(MyApplication.getContext(),"访问网络异常");
            }
        });
    }
}
