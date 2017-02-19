package com.shizhen5452.justlook.fragment;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.activity.MainActivity;
import com.shizhen5452.justlook.presenter.SettingPresenter;
import com.shizhen5452.justlook.presenter.presenterimpl.SettingPresenterImpl;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.SPUtils;
import com.shizhen5452.justlook.view.SettingView;

import butterknife.BindView;

/**
 * Create by EminemShi on 2017/2/15
 */

public class SettingFragment extends BaseFragment implements SettingView, View.OnClickListener {
    @BindView(R.id.tv_cache_size)
    TextView       mTvCacheSize;
    @BindView(R.id.rl_cache)
    RelativeLayout mRlCache;
    @BindView(R.id.switch_night_mode)
    SwitchCompat   mSwitchNightMode;
    private SettingPresenter mSettingPresenter;

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView() {
        mSettingPresenter = new SettingPresenterImpl(getContext(), this);
        int themeId = SPUtils.getInstance(getContext()).getThemeId(Constant.SP_KEY_THEME_ID, R.style.AppTheme);
        if (themeId == R.style.AppTheme_Night) {
            mSwitchNightMode.setChecked(true);
        } else {
            mSwitchNightMode.setChecked(false);
        }
        mSwitchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SPUtils.getInstance(getContext()).putThemeId(Constant.SP_KEY_THEME_ID,R.style.AppTheme_Night);
                } else {
                    SPUtils.getInstance(getContext()).putThemeId(Constant.SP_KEY_THEME_ID,R.style.AppTheme);
                }
                mSwitchNightMode.setChecked(isChecked);
                Snackbar.make(mSwitchNightMode, "重启应用后生效", Snackbar.LENGTH_LONG)
                        .setAction("立即重启", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((MainActivity) getActivity()).reStartApplication();
                            }
                        }).show();
            }
        });
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
