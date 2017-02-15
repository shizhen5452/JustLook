package com.shizhen5452.justlook.fragment;

import com.shizhen5452.justlook.utils.Constant;

/**
 * Create by EminemShi on 2017/2/6
 */

public class FragmentFactory {
    private FragmentFactory() {
    }

    private static FragmentFactory    sFragmentFactory;
    private        ZhihuDaliyFragment mZhihuDaliyFragment;
    private        BookmarkFragment   mBookmarkFragment;
    private        AboutFragment      mAboutFragment;
    private        SettingFragment    mSettingFragment;

    public static FragmentFactory getInstance() {
        if (sFragmentFactory == null) {
            sFragmentFactory = new FragmentFactory();
        }
        return sFragmentFactory;
    }

    public BaseFragment getFragmentByTag(String tag) {
        switch (tag) {
            case Constant.TAG_ZHIHU_FRAGMENT:
                if (mZhihuDaliyFragment == null) {
                    mZhihuDaliyFragment = new ZhihuDaliyFragment();
                }
                return mZhihuDaliyFragment;
            case Constant.TAG_BOOKMARK_FRAGMENT:
                if (mBookmarkFragment == null) {
                    mBookmarkFragment = new BookmarkFragment();
                }
                return mBookmarkFragment;
            case Constant.TAG_ABOUT_FRAGMENT:
                if (mAboutFragment == null) {
                    mAboutFragment = new AboutFragment();
                }
                return mAboutFragment;
            case Constant.TAG_SETTING_FRAGMENT:
                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                }
                return mSettingFragment;
        }
        return null;
    }

}
