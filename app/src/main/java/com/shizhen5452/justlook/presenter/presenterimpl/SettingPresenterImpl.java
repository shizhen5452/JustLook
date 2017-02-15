package com.shizhen5452.justlook.presenter.presenterimpl;

import android.content.Context;

import com.blankj.utilcode.utils.CleanUtils;
import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.Utils;
import com.shizhen5452.justlook.presenter.SettingPresenter;
import com.shizhen5452.justlook.utils.CacheUtil;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.ThreadUtils;
import com.shizhen5452.justlook.view.SettingView;

import java.io.File;

/**
 * Create by EminemShi on 2017/2/15
 */

public class SettingPresenterImpl implements SettingPresenter {
    private SettingView mSettingView;
    private Context mContext;
    private final CacheUtil mCacheUtil;

    public SettingPresenterImpl(Context context,SettingView settingView) {
        mSettingView = settingView;
        mContext=context;
        mCacheUtil = CacheUtil.get(context);
    }

    @Override
    public void getCacheSize() {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                final String cacheSize = FileUtils.getDirSize(new File(Constant.CACHE_DIR));
                ThreadUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mSettingView.onGetCacheSize(cacheSize);
                    }
                });
            }
        });
    }

    @Override
    public void clearCache() {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Utils.init(mContext);
                final boolean isSuccess = CleanUtils.cleanInternalCache();
                final String cacheSize = FileUtils.getDirSize(new File(Constant.CACHE_DIR));
                ThreadUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mSettingView.onClearCache(isSuccess,cacheSize);
                    }
                });
            }
        });
    }
}
