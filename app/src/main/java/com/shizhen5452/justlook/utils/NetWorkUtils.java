package com.shizhen5452.justlook.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Create by EminemShi on 2017/2/7
 */

public class NetWorkUtils {

    private static ConnectivityManager sConnectivityManager;

    //判断网络是否可用
    public static boolean isNetWorkAvailable(Context context) {
        if (sConnectivityManager == null) {
            sConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo networkInfo = sConnectivityManager.getActiveNetworkInfo();
        return networkInfo!=null&&networkInfo.isConnected();
    }

}
