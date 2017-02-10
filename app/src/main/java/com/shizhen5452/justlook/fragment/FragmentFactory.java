package com.shizhen5452.justlook.fragment;

import com.shizhen5452.justlook.utils.Constant;

/**
 * Create by EminemShi on 2017/2/6
 */

public class FragmentFactory {

    private static ZhihuDaliyFragment sZhihuDaliyFragment;
    private static BookmarkFragment sBookmarkFragment;

    public static BaseFragment getFragmentByTag(String tag) {
        switch (tag) {
            case Constant.TAG_ZHIHU_FRAGMENT:
                if (sZhihuDaliyFragment == null) {
                    sZhihuDaliyFragment = new ZhihuDaliyFragment();
                }
                return sZhihuDaliyFragment;
            case Constant.TAG_BOOKMARK_FRAGMENT:
                if (sBookmarkFragment == null) {
                    sBookmarkFragment = new BookmarkFragment();
                }
                return sBookmarkFragment;
        }
        return null;
    }

}
