package com.shizhen5452.justlook.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.activity.ZhihuDetailActivity;
import com.shizhen5452.justlook.adapter.ZhihuDaliyAdapter;
import com.shizhen5452.justlook.bean.ZhihuDaliyBean;
import com.shizhen5452.justlook.bean.ZhihuTopStoriesBean;
import com.shizhen5452.justlook.presenter.ZhihuDaliyPresenter;
import com.shizhen5452.justlook.presenter.presenterimpl.ZhihuDaliyPresenterImpl;
import com.shizhen5452.justlook.utils.CacheUtil;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.NetWorkUtils;
import com.shizhen5452.justlook.utils.ToastUtils;
import com.shizhen5452.justlook.view.ZhihuDaliyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZhihuDaliyFragment extends BaseFragment implements ZhihuDaliyView, AppBarLayout.OnOffsetChangedListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_zhihu)
    RecyclerView       mRvZhihu;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.convenientBanner)
    ConvenientBanner   mConvenientBanner;
    @BindView(R.id.appBarLayout)
    AppBarLayout       mAppBarLayout;
    @BindView(R.id.tv_no_connection_text)
    TextView           mTvNoConnectionText;
    private ZhihuDaliyPresenter mZhihuDaliyPresenter;
    private List<String> mImageList = new ArrayList<>();
    private ZhihuDaliyAdapter             mZhihuDaliyAdapter;
    private RecyclerView.OnScrollListener mLoadingMoreScrollListener;
    private LinearLayoutManager           mLinearLayoutManager;
    private boolean                       isLoadingMore;
    private String                        mCurrentLoadDate;
    private int                           preTotalItemCount;
    private List<ZhihuTopStoriesBean>     mTop_stories;
    private CacheUtil                     mCacheUtil;

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_zhihu_daliy;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setProgressViewOffset(true, 0, 300);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.md_blue_500), getResources().getColor(R.color.md_green_500));
        mConvenientBanner.startTurning(4000);
        mZhihuDaliyAdapter = new ZhihuDaliyAdapter(getContext());
        mZhihuDaliyPresenter = new ZhihuDaliyPresenterImpl(getContext(), this);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mCacheUtil = CacheUtil.get(getContext());
    }

    @Override
    protected void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mLoadingMoreScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mRvZhihu.getChildCount();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (isLoadingMore) {
                    if (totalItemCount > preTotalItemCount) {
                        isLoadingMore = false;
                        preTotalItemCount = totalItemCount;
                    }
                }
                if (!isLoadingMore && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    isLoadingMore = true;
                    loadMoreData();
                }
            }
        };
    }

    @Override
    protected void initData() {
        mRvZhihu.setLayoutManager(mLinearLayoutManager);
        mRvZhihu.setHasFixedSize(true);
        mRvZhihu.setAdapter(mZhihuDaliyAdapter);
        mRvZhihu.addOnScrollListener(mLoadingMoreScrollListener);
        //初始化知乎日报最新消息
        if (!NetWorkUtils.isNetWorkAvailable(getActivity())) {
            if (mCacheUtil.getAsJSONObject(Constant.ZHIHU) != null) {
                mZhihuDaliyPresenter.loadCache();
                onError();
            } else {
                mTvNoConnectionText.setVisibility(View.VISIBLE);
            }
        } else {
            loadData();
        }
    }

    private void loadData() {
        if (mZhihuDaliyAdapter.getItemCount() > 0) {
            mZhihuDaliyAdapter.clearData();
        }
        mCurrentLoadDate = "0";
        mZhihuDaliyPresenter.initZhihu();
    }

    @Override
    public void onPause() {
        super.onPause();
        mConvenientBanner.stopTurning();
    }

    @Override
    public void onInitZhihu(ZhihuDaliyBean zhihuDaliyBean) {
        initPager(zhihuDaliyBean);
        mCurrentLoadDate = zhihuDaliyBean.getDate();
        mZhihuDaliyAdapter.addItems(zhihuDaliyBean.getStories());
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        mZhihuDaliyPresenter.loadMore(mCurrentLoadDate);
    }


    @Override
    public void onLoadMore(ZhihuDaliyBean zhihuDaliyBean) {
        if (isLoadingMore) {
            isLoadingMore = false;
        }
        mCurrentLoadDate = zhihuDaliyBean.getDate();
        mZhihuDaliyAdapter.addItems(zhihuDaliyBean.getStories());
        if (!mRvZhihu.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            loadMoreData();
        }
    }

    @Override
    public void onError() {
        ToastUtils.showShortToast(getActivity(), getResources().getString(R.string.access_net_fail));

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

    /**
     * 初始化轮播图
     */
    private void initPager(ZhihuDaliyBean zhihuDaliyBean) {
        mTop_stories = zhihuDaliyBean.getTop_stories();
        mImageList.clear();

        for (ZhihuTopStoriesBean top_story : mTop_stories) {
            mImageList.add(top_story.getImage());
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mConvenientBanner.setPages(new CBViewHolderCreator<ZhihuHolderView>() {
            @Override
            public ZhihuHolderView createHolder() {
                return new ZhihuHolderView();
            }
        }, mImageList);
    }

    @Override
    public void onRefresh() {
        if (!NetWorkUtils.isNetWorkAvailable(getActivity())) {
            onError();
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        } else {
            mTvNoConnectionText.setVisibility(View.GONE);
            loadData();
        }
    }

    class ZhihuHolderView implements Holder<String> {

        private ImageView mIv;

        @Override
        public View createView(Context context) {
            mIv = new ImageView(context);
            return mIv;
        }

        @Override
        public void UpdateUI(final Context context, int position, final String data) {
            Glide.with(context).load(data).centerCrop().into(mIv);
            //轮播图点击跳转到消息详情界面
            mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (ZhihuTopStoriesBean top_story : mTop_stories) {
                        if (top_story.getImage().equals(data)) {
                            Intent intent = new Intent(getActivity(), ZhihuDetailActivity.class);
                            intent.putExtra(Constant.ZHIHU_DALIY_ID, top_story.getId());
                            startActivity(intent);
                        }
                    }
                }
            });
        }

    }

    /**
     * 设置只有界面最顶端下拉才能使用下拉刷新
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset >= 0) {
            mSwipeRefreshLayout.setEnabled(true);
        } else {
            mSwipeRefreshLayout.setEnabled(false);
        }
    }


}
