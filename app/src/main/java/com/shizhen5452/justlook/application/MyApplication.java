package com.shizhen5452.justlook.application;

import android.app.Application;
import android.content.Context;

/**
 * Create by EminemShi on 2017/2/3
 */

public class MyApplication extends Application {
    private static MyApplication mMyApplication;

    public static Context getContext() {
        return mMyApplication;
    }

}
