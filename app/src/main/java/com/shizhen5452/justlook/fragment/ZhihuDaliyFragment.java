package com.shizhen5452.justlook.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.activity.ZhihuDetailActivity;
import com.shizhen5452.justlook.adapter.ZhihuDaliyAdapter;
import com.shizhen5452.justlook.bean.ZhihuDaliyBean;
import com.shizhen5452.justlook.listener.ZhihuDaliyOnScrollListener;
import com.shizhen5452.justlook.presenter.ZhihuDaliyPresenter;
import com.shizhen5452.justlook.presenter.presenterimpl.ZhihuDaliyPresenterImpl;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.ToastUtils;
import com.shizhen5452.justlook.view.ZhihuDaliyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZhihuDaliyFragment extends BaseFragment implements ZhihuDaliyView, AppBarLayout.OnOffsetChangedListener, ZhihuDaliyAdapter.OnZhihuItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_zhihu)
    RecyclerView       mRvZhihu;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.convenientBanner)
    ConvenientBanner   mConvenientBanner;
    @BindView(R.id.appBarLayout)
    AppBarLayout       mAppBarLayout;
    private ZhihuDaliyPresenter mZhihuDaliyPresenter;
    private List<String> mImageList = new ArrayList<>();
    private ZhihuDaliyAdapter mZhihuDaliyAdapter;
    private String mDate;
    private List<String> mDateList=new ArrayList<>();

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_zhihu_daliy;
    }

    @Override
    protected void initView() {

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorAccent));

        mConvenientBanner.startTurning(4000);
    }

    @Override
    protected void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        mDateList.clear();
        //初始化知乎日报最新消息
        mZhihuDaliyPresenter = new ZhihuDaliyPresenterImpl(this);
        mZhihuDaliyPresenter.initZhihu();
    }

    @Override
    public void onPause() {
        super.onPause();
        mConvenientBanner.stopTurning();
    }

    @Override
    public void onInitZhihu(ZhihuDaliyBean zhihuDaliyBean,List<ZhihuDaliyBean.StoriesBean> allStoriesBeanList) {
        initPager(zhihuDaliyBean);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRvZhihu.setLayoutManager(linearLayoutManager);
        mDate = zhihuDaliyBean.getDate();
        mDateList.add(mDate);
        mZhihuDaliyAdapter = new ZhihuDaliyAdapter(mDateList,allStoriesBeanList);
        mZhihuDaliyAdapter.setOnZhihuItemClickListener(this);
        mRvZhihu.setAdapter(mZhihuDaliyAdapter);
        mRvZhihu.addOnScrollListener(new ZhihuDaliyOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData();
            }
        });
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        mZhihuDaliyPresenter.loadMore(mDate);
    }

    @Override
    public void onError() {
        ToastUtils.showShortToast(getActivity(),getResources().getString(R.string.access_net_fail));

    }

    @Override
    public void onLoadMore(ZhihuDaliyBean zhihuDaliyBean) {
        mDate=zhihuDaliyBean.getDate();
        mDateList.add(mDate);
        mZhihuDaliyAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化轮播图
     */
    private void initPager(ZhihuDaliyBean zhihuDaliyBean) {
        List<ZhihuDaliyBean.TopStoriesBean> top_stories = zhihuDaliyBean.getTop_stories();
        mImageList.clear();
        for (ZhihuDaliyBean.TopStoriesBean top_story : top_stories) {
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
        initData();
    }

    class ZhihuHolderView implements Holder<String> {

        private ImageView mIv;

        @Override
        public View createView(Context context) {
            mIv = new ImageView(context);
            return mIv;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(context).load(data).centerCrop().into(mIv);
        }

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset >= 0) {
            mSwipeRefreshLayout.setEnabled(true);
        } else {
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public void onZhihuItemClickListener(int id) {
        Intent intent = new Intent(getActivity(), ZhihuDetailActivity.class);
        intent.putExtra(Constant.ZHIHU_DALIY_ID,id);
        startActivity(intent);
    }

}
