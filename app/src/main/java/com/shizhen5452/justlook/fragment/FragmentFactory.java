package com.shizhen5452.justlook.fragment;

import com.shizhen5452.justlook.utils.Constant;

/**
 * Create by EminemShi on 2017/2/6
 */

public class FragmentFactory {
    private FragmentFactory() {
    }

    private static FragmentFactory    sFragmentFactory;
    private        ZhihuDaliyFragment zhihuDaliyFragment;
    private        BookmarkFragment   bookmarkFragment;

    public static FragmentFactory getInstance() {
        if (sFragmentFactory == null) {
            sFragmentFactory = new FragmentFactory();
        }
        return sFragmentFactory;
    }

    public BaseFragment getFragmentByTag(String tag) {
        switch (tag) {
            case Constant.TAG_ZHIHU_FRAGMENT:
                if (zhihuDaliyFragment == null) {
                    zhihuDaliyFragment = new ZhihuDaliyFragment();
                }
                return zhihuDaliyFragment;
            case Constant.TAG_BOOKMARK_FRAGMENT:
                if (bookmarkFragment == null) {
                    bookmarkFragment = new BookmarkFragment();
                }
                return bookmarkFragment;
        }
        return null;
    }

}
