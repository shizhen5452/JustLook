package com.shizhen5452.justlook.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Create by EminemShi on 2017/2/12
 */

public class SPUtils {

    private SharedPreferences mThemeSP;

    private SPUtils(Context context){}
    private static SPUtils mSPUtils;
    private static Context mContext;

    public static SPUtils getInstance(Context context) {
        if (mSPUtils == null) {
            mSPUtils=new SPUtils(context);
        }
        mContext=context;
        return mSPUtils;
    }

    public void putThemeId(String key, int id) {
        if (mThemeSP == null) {
            mThemeSP = mContext.getSharedPreferences(Constant.SP_NAME_THEME, mContext.MODE_PRIVATE);
        }
        mThemeSP.edit().putInt(key,id).apply();
    }

    public int getThemeId(String key,int defaultId) {
        if (mThemeSP == null) {
            mThemeSP = mContext.getSharedPreferences(Constant.SP_NAME_THEME, mContext.MODE_PRIVATE);
        }
        int id = mThemeSP.getInt(key, defaultId);
        return id;
    }
}
