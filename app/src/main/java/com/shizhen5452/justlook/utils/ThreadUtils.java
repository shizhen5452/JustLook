package com.shizhen5452.justlook.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Create by EminemShi on 2017/2/8
 */

public class ThreadUtils {
    //确保当前Handler关联上主线程的Looper对象
    private static Handler  sHandler  = new Handler(Looper.getMainLooper());
    private static Executor sExecutor = Executors.newSingleThreadExecutor();

    public static void runOnSubThread(Runnable runnable) {
        sExecutor.execute(runnable);
    }

    public static void runOnUIThread(Runnable runnable) {
        sHandler.post(runnable);
    }
}
