package com.shizhen5452.justlook.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.activity.MainActivity;
import com.shizhen5452.justlook.activity.ZhihuDetailActivity;
import com.shizhen5452.justlook.adapter.ZhihuDaliyAdapter;
import com.shizhen5452.justlook.bean.ZhihuDaliyBean;
import com.shizhen5452.justlook.bean.ZhihuDaliyItemBean;
import com.shizhen5452.justlook.bean.ZhihuTopStoriesBean;
import com.shizhen5452.justlook.presenter.ZhihuDaliyPresenter;
import com.shizhen5452.justlook.presenter.presenterimpl.ZhihuDaliyPresenterImpl;
import com.shizhen5452.justlook.utils.CacheUtil;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.DBUtils;
import com.shizhen5452.justlook.utils.NetWorkUtils;
import com.shizhen5452.justlook.utils.ToastUtils;
import com.shizhen5452.justlook.view.ZhihuDaliyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZhihuDaliyFragment extends BaseFragment implements ZhihuDaliyView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tv_no_connection_text)
    TextView           mTvNoConnectionText;
    @BindView(R.id.rv_zhihu)
    RecyclerView       mRvZhihu;
    private ZhihuDaliyPresenter mZhihuDaliyPresenter;
    private List<String> mImageList = new ArrayList<>();
    private LinearLayoutManager       mLinearLayoutManager;
    private String                    mCurrentLoadDate;
    private List<ZhihuTopStoriesBean> mTop_stories;
    private CacheUtil                 mCacheUtil;
    private ConvenientBanner          mConvenientBanner;
    private View                      mHeaderView;
    private ZhihuDaliyAdapter         mZhihuDaliyAdapter;
    private int                       mCurrentCounter;
    private int mTotalCounter = 5;
    private static final int DELAY_TIME=500;

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_zhihu_daliy;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setProgressViewOffset(true, 0, 300);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.md_blue_500), getResources().getColor(R.color.md_green_500));

        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.header_zhihu_daliy_fragment, (ViewGroup) ((MainActivity) getContext()).findViewById(android.R.id.content), false);
        mConvenientBanner = (ConvenientBanner) mHeaderView.findViewById(R.id.convenientBanner);
        mConvenientBanner.startTurning(4000);

        mZhihuDaliyPresenter = new ZhihuDaliyPresenterImpl(getContext(), this);
        mCacheUtil = CacheUtil.get(getContext());

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mZhihuDaliyAdapter = new ZhihuDaliyAdapter();
        mZhihuDaliyAdapter.addHeaderView(mHeaderView);
        mZhihuDaliyAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRvZhihu.setLayoutManager(mLinearLayoutManager);
        mRvZhihu.setHasFixedSize(true);
        mRvZhihu.setAdapter(mZhihuDaliyAdapter);
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRvZhihu.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ZhihuDaliyItemBean item = (ZhihuDaliyItemBean) adapter.getItem(position);
                DBUtils.getDB(getContext()).putIsRead(Constant.ZHIHU, item.getId() + "", 1);
                TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
                tvTitle.setTextColor(Color.GRAY);
                Intent intent = new Intent(getContext(), ZhihuDetailActivity.class);
                intent.putExtra(Constant.ZHIHU_DALIY_ID, item.getId());
                getContext().startActivity(intent);
            }
        });

        mZhihuDaliyAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRvZhihu.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= mTotalCounter) {
                            //数据全部加载完毕
                            mZhihuDaliyAdapter.loadMoreEnd();
                        } else {
                            if (mZhihuDaliyAdapter.getItem(0) == null) {
                                return;
                            }
                            loadMoreData();
                            mCurrentCounter = mTotalCounter;
                            mTotalCounter += 5;
                        }
                    }

                }, DELAY_TIME);
            }
        });
    }

    @Override
    protected void initData() {
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
        mCurrentLoadDate = "0";
        mZhihuDaliyPresenter.initZhihu();
    }

    @Override
    public void onInitZhihu(ZhihuDaliyBean zhihuDaliyBean) {
        initPager(zhihuDaliyBean);
        mCurrentLoadDate = zhihuDaliyBean.getDate();
        mZhihuDaliyAdapter.setNewData(zhihuDaliyBean.getStories());
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        mZhihuDaliyPresenter.loadMore(mCurrentLoadDate);
    }


    @Override
    public void onLoadMore(ZhihuDaliyBean zhihuDaliyBean) {
        mCurrentLoadDate=zhihuDaliyBean.getDate();
        mZhihuDaliyAdapter.loadMoreComplete();
        mZhihuDaliyAdapter.addData(zhihuDaliyBean.getStories());
    }


    @Override
    public void onError() {
        mSwipeRefreshLayout.setRefreshing(false);
        ToastUtils.showShortToast(getActivity(), getResources().getString(R.string.access_net_fail));
    }

    @Override
    public void onLoadMoreError() {
        mZhihuDaliyAdapter.loadMoreFail();
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

    @Override
    public void onRefresh() {
        if (!NetWorkUtils.isNetWorkAvailable(getActivity())) {
            onError();
            if (mCacheUtil.getAsJSONObject(Constant.ZHIHU) == null) {
                mTvNoConnectionText.setVisibility(View.VISIBLE);
            }
        } else {
            mTvNoConnectionText.setVisibility(View.GONE);
            loadData();
        }
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

    @Override
    public void onPause() {
        super.onPause();
        mConvenientBanner.stopTurning();
    }


}
