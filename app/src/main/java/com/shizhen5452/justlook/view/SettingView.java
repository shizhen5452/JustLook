package com.shizhen5452.justlook.view;

/**
 * Create by EminemShi on 2017/2/15
 */

public interface SettingView {
    void onGetCacheSize(String cacheSize);
    void onClearCache(boolean isSuccess, String cacheSize);
}
