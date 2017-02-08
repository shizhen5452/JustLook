package com.shizhen5452.justlook.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.bean.ZhihuDetailBean;
import com.shizhen5452.justlook.presenter.ZhihuDetailPresenter;
import com.shizhen5452.justlook.presenter.presenterimpl.ZhihuDetailPresenterImpl;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.StatusBarUtils;
import com.shizhen5452.justlook.utils.ToastUtils;
import com.shizhen5452.justlook.utils.WebUtil;
import com.shizhen5452.justlook.view.ZhihuDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shizhen5452.justlook.R.id.appBarLayout;

public class ZhihuDetailActivity extends BaseActivity implements ZhihuDetailView, AppBarLayout.OnOffsetChangedListener {

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
    private int DEFAULT_ID = 3892357;
    private ZhihuDetailPresenter mZhihuDetailPresenter;

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

        WebSettings settings = mWv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWv.setWebChromeClient(new WebChromeClient());
        mWv.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
    }

    private void initListener() {
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNestedScrollView.smoothScrollTo(0,0);
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    private void initData() {
        int id = getIntent().getIntExtra(Constant.ZHIHU_DALIY_ID, DEFAULT_ID);
        mZhihuDetailPresenter.initDetail(id);
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
            mWv.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
        }
    }

    @Override
    public void onError() {
        ToastUtils.showShortToast(this,getResources().getString(R.string.access_net_fail));
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
            mWv=null;
        }
    }
}
