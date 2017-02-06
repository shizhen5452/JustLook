package com.shizhen5452.justlook.fragment;

import com.shizhen5452.justlook.utils.Constant;

/**
 * Create by EminemShi on 2017/2/6
 */

public class FragmentFactory {

    private static ZhihuDaliyFragment sZhihuDaliyFragment;
    private static NeteaseNewsFragment sNeteaseNewsFragment;
    private static GankFragment sGankFragment;

    public static BaseFragment getFragmentByTag(String tag) {
        switch (tag) {
            case Constant.TAG_ZHIHU_FRAGMENT:
                if (sZhihuDaliyFragment == null) {
                    sZhihuDaliyFragment = new ZhihuDaliyFragment();
                }
                return sZhihuDaliyFragment;
            case Constant.TAG_NETEASE_FRAGMENT:
                if (sNeteaseNewsFragment == null) {
                    sNeteaseNewsFragment = new NeteaseNewsFragment();
                }
                return sNeteaseNewsFragment;
            case Constant.TAG_GANK_FRAGMENT:
                if (sGankFragment == null) {
                    sGankFragment = new GankFragment();
                }
                return sGankFragment;
        }
        return null;
    }

}
