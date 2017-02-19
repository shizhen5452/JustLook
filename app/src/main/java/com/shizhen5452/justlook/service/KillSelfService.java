package com.shizhen5452.justlook.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.shizhen5452.justlook.utils.Constant;

/**
 * Create by EminemShi on 2017/2/17
 */

public class KillSelfService extends Service {

    private final Handler mHandler;
    private static final int DELAY_TIME=1000;

    public KillSelfService() {
        mHandler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String packageName = intent.getStringExtra(Constant.INTENT_PACKAGE_NAME);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = getPackageManager().getLaunchIntentForPackage(packageName);
                startActivity(intent1);
                KillSelfService.this.stopSelf();
            }
        },DELAY_TIME);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
