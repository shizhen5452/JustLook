package com.shizhen5452.justlook.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.adapter.BookmarkAdapter;
import com.shizhen5452.justlook.bean.ZhihuDetailBean;
import com.shizhen5452.justlook.presenter.BookmarkPresenter;
import com.shizhen5452.justlook.presenter.presenterimpl.BookmarkPresenterImpl;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.ToastUtils;
import com.shizhen5452.justlook.view.BookmarkView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Create by EminemShi on 2017/2/10
 */

public class BookmarkFragment extends BaseFragment implements BookmarkView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_bookmark)
    RecyclerView       mRvBookmark;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private BookmarkPresenter mBookmarkPresenter;
    private BookmarkAdapter   mBookmarkAdapter;

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_bookmark;
    }

    @Override
    protected void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mSwipeRefreshLayout.setProgressViewOffset(true, 0, 300);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.md_blue_500), getResources().getColor(R.color.md_green_500));

        mBookmarkPresenter = new BookmarkPresenterImpl(this, getActivity());
        mBookmarkAdapter = new BookmarkAdapter(getActivity());
        mRvBookmark.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvBookmark.setHasFixedSize(true);
        mRvBookmark.setAdapter(mBookmarkAdapter);
    }

    @Override
    protected void initData() {
        mBookmarkPresenter.loadData();
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onLoadData(List<ZhihuDetailBean> zhihuDetailBeanList) {
        mBookmarkAdapter.addItems(zhihuDetailBeanList);
    }

    @Override
    public void onNoBookMark() {
        ToastUtils.showShortToast(getActivity(), "亲，你没有收藏的文章哦");
    }

    @Override
    public void showProgressBar() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDBChanged(String msg) {
        if (Constant.BOOKMARK_DB_CHANGED.equals(msg)) {
            mBookmarkPresenter.loadData();
        }
    }

    @Override
    public void onError() {
        ToastUtils.showShortToast(getActivity(), getResources().getString(R.string.access_net_fail));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        mBookmarkPresenter.loadData();
    }
}
