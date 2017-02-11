package com.shizhen5452.justlook.presenter.presenterimpl;

import android.content.Context;

import com.shizhen5452.justlook.api.ApiManager;
import com.shizhen5452.justlook.bean.ZhihuDetailBean;
import com.shizhen5452.justlook.db.DBUtils;
import com.shizhen5452.justlook.presenter.BookmarkPresenter;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.view.BookmarkView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create by EminemShi on 2017/2/11
 */

public class BookmarkPresenterImpl implements BookmarkPresenter {
    private BookmarkView mBookmarkView;
    private Context mContext;
    private List<ZhihuDetailBean> mZhihuDetailBeanList =new ArrayList<>();

    public BookmarkPresenterImpl(BookmarkView bookmarkView, Context context) {
        mBookmarkView = bookmarkView;
        mContext=context;
    }

    @Override
    public void loadData() {
        mZhihuDetailBeanList.clear();
        List<String> allIsBookmark = DBUtils.getDB(mContext).getAllIsBookmark(Constant.ZHIHU);
        if (allIsBookmark != null && allIsBookmark.size() > 0) {
            for (int i = 0; i < allIsBookmark.size(); i++) {
                ApiManager.getInstance().getZhihuApiService().getZhihuDetail(Integer.parseInt(allIsBookmark.get(i))).enqueue(new Callback<ZhihuDetailBean>() {
                    @Override
                    public void onResponse(Call<ZhihuDetailBean> call, Response<ZhihuDetailBean> response) {
                        if (response.isSuccessful()) {
                            ZhihuDetailBean zhihuDetailBean = response.body();
                            mZhihuDetailBeanList.add(zhihuDetailBean);
                            mBookmarkView.onLoadData(mZhihuDetailBeanList);
                        } else {
                            mBookmarkView.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<ZhihuDetailBean> call, Throwable t) {
                        mBookmarkView.onError();
                    }
                });
            }
        }
    }
}
