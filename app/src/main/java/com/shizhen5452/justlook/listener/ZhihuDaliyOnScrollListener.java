package com.shizhen5452.justlook.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Create by EminemShi on 2017/2/7
 */

public abstract class ZhihuDaliyOnScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager mLinearLayoutManager;
    private int currentPage=0;
    private int mVisibleItemCount;
    private int mTotalItemCount;
    private int mPreTotalItemCount;
    private int mFirstVisibleItem;
    private boolean isLoading=true;

    public ZhihuDaliyOnScrollListener(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mVisibleItemCount = recyclerView.getChildCount();
        mTotalItemCount = mLinearLayoutManager.getItemCount();
        mFirstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (isLoading) {
            if (mTotalItemCount > mPreTotalItemCount) {
                isLoading=false;
                mPreTotalItemCount=mTotalItemCount;
            }
        }

        if (!isLoading && mTotalItemCount - mVisibleItemCount <= mFirstVisibleItem) {
            currentPage++;
            onLoadMore(currentPage);
            isLoading=true;
        }

    }

    public abstract void onLoadMore(int currentPage);
}
