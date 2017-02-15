package com.shizhen5452.justlook.fragment;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.presenter.SettingPresenter;
import com.shizhen5452.justlook.presenter.presenterimpl.SettingPresenterImpl;
import com.shizhen5452.justlook.view.SettingView;

import butterknife.BindView;

/**
 * Create by EminemShi on 2017/2/15
 */

public class SettingFragment extends BaseFragment implements SettingView,View.OnClickListener {
    @BindView(R.id.tv_cache_size)
    TextView       mTvCacheSize;
    @BindView(R.id.rl_cache)
    RelativeLayout mRlCache;
    private SettingPresenter mSettingPresenter;

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView() {
        mSettingPresenter=new SettingPresenterImpl(getContext(),this);
    }

    @Override
    protected void initData() {
        mSettingPresenter.getCacheSize();
    }

    @Override
    protected void initListener() {
        mRlCache.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mSettingPresenter.clearCache();
    }

    @Override
    public void onGetCacheSize(String cacheSize) {
        mTvCacheSize.setText(cacheSize);
    }

    @Override
    public void onClearCache(boolean isSuccess, String cacheSize) {
        if (isSuccess) {
            mTvCacheSize.setText(cacheSize);
        }
    }
}
