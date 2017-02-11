package com.shizhen5452.justlook.view;

import com.shizhen5452.justlook.bean.ZhihuDetailBean;

import java.util.List;

/**
 * Create by EminemShi on 2017/2/11
 */

public interface BookmarkView {
    void onError();
    void onLoadData(List<ZhihuDetailBean> zhihuDetailBeanList);
}
