package com.shizhen5452.justlook.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.bean.ZhihuDetailBean;
import com.shizhen5452.justlook.db.DBUtils;
import com.shizhen5452.justlook.presenter.ZhihuDetailPresenter;
import com.shizhen5452.justlook.presenter.presenterimpl.ZhihuDetailPresenterImpl;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.StatusBarUtils;
import com.shizhen5452.justlook.utils.ToastUtils;
import com.shizhen5452.justlook.utils.WebUtil;
import com.shizhen5452.justlook.view.ZhihuDetailView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shizhen5452.justlook.R.id.appBarLayout;

public class ZhihuDetailActivity extends BaseActivity implements ZhihuDetailView, AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    @BindView(R.id.iv_top)
    ImageView               mIvTop;
    @BindView(R.id.toolbar)
    Toolbar                 mToolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(appBarLayout)
    AppBarLayout            mAppBarLayout;
    @BindView(R.id.wv)
    WebView                 mWv;
    @BindView(R.id.nestedScrollView)
    NestedScrollView        mNestedScrollView;
    @BindView(R.id.fab)
    FloatingActionButton    mFab;
    private int DEFAULT_ID = 3892357;
    private ZhihuDetailPresenter mZhihuDetailPresenter;
    private boolean isBookmarked;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_detail);
        ButterKnife.bind(this);

        initToolbar();
        initView();
        initListener();
        initData();
    }

    private void initToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        StatusBarUtils.setTranslucentImageHeader(this, 0, mToolbar);
        showProgressDialog(getResources().getString(R.string.loading));
        mZhihuDetailPresenter = new ZhihuDetailPresenterImpl(this);

        mWv.setScrollbarFadingEnabled(true);
        WebSettings settings = mWv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(false);
        mWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mZhihuDetailPresenter.openUrl(view, url);
                return true;
            }
        });
    }

    private void initListener() {
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNestedScrollView.smoothScrollTo(0, 0);
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(this);
        mFab.setOnClickListener(this);
    }

    private void initData() {
        mId = getIntent().getIntExtra(Constant.ZHIHU_DALIY_ID, DEFAULT_ID);
        mZhihuDetailPresenter.initDetail(mId);
        if (DBUtils.getDB(this).isBookmarked(Constant.ZHIHU, mId+"", 1)) {
            isBookmarked = true;
            mFab.setImageResource(R.mipmap.ic_favorite_red_a700_48dp);
        } else {
            isBookmarked=false;
            mFab.setImageResource(R.mipmap.ic_favorite_white_48dp);
        }
    }

    @Override
    public void onInitDetail(ZhihuDetailBean zhihuDetailBean) {
        hideProgressDialog();
        String image = zhihuDetailBean.getImage();
        Glide.with(getApplicationContext()).load(image).centerCrop().into(mIvTop);
        String body = zhihuDetailBean.getBody();
        if (TextUtils.isEmpty(body)) {
            mWv.loadUrl(zhihuDetailBean.getShare_url());
        } else {
            String data = WebUtil.buildHtmlWithCss(body, zhihuDetailBean.getCss());
            mWv.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING, null);
        }
    }

    @Override
    public void onError() {
        ToastUtils.showShortToast(this, getResources().getString(R.string.access_net_fail));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (Math.abs(verticalOffset) >= mAppBarLayout.getTotalScrollRange()) {
            mCollapsingToolbarLayout.setTitle(getResources().getString(R.string.zhihu_daliy));
        } else {
            mCollapsingToolbarLayout.setTitle("");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止WebView内存泄露
        if (mWv != null) {
            ((ViewGroup) mWv.getParent()).removeView(mWv);
            mWv.destroy();
            mWv = null;
        }
        EventBus.getDefault().post(Constant.BOOKMARK_DB_CHANGED);
    }

    @Override
    public void onClick(View v) {
        if (!isBookmarked) {
            mFab.setImageResource(R.mipmap.ic_favorite_red_a700_48dp);
            DBUtils.getDB(this).putIsBookmark(Constant.ZHIHU,mId+"",1);
            isBookmarked = true;
            ToastUtils.showShortToast(this, "已收藏");
        } else {
            mFab.setImageResource(R.mipmap.ic_favorite_white_48dp);
            DBUtils.getDB(this).deleteIsBookmark(Constant.ZHIHU,mId+"");
            isBookmarked = false;
            ToastUtils.showShortToast(this, "取消收藏");
        }
    }
}
