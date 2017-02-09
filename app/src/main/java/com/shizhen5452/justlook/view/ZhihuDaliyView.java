package com.shizhen5452.justlook.view;

import com.shizhen5452.justlook.bean.ZhihuDaliyBean;

import java.util.List;

/**
 * Create by EminemShi on 2017/2/6
 */

public interface ZhihuDaliyView {
    void onInitZhihu(ZhihuDaliyBean zhihuDaliyBean);
    void onError();
    void onLoadMore(ZhihuDaliyBean zhihuDaliyBean);
    void showProgressBar();
    void hideProgressBar();
}
