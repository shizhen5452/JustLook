package com.shizhen5452.justlook.presenter.presenterimpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.shizhen5452.justlook.activity.MainActivity;
import com.shizhen5452.justlook.api.ApiManager;
import com.shizhen5452.justlook.bean.ImageResponseBean;
import com.shizhen5452.justlook.presenter.MainPresenter;
import com.shizhen5452.justlook.utils.ThreadUtils;
import com.shizhen5452.justlook.view.MainView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create by EminemShi on 2017/2/8
 */

public class MainPresenterImpl implements MainPresenter {
    private MainView mMainView;
    private  Context mContext;

    public MainPresenterImpl(MainView mainView) {
        mMainView = mainView;
        mContext = ((MainActivity) mMainView).getApplicationContext();
    }

    @Override
    public void getNavHeaderBackground() {
        ApiManager.getInstance().getZhihuApiService().getImage().enqueue(new Callback<ImageResponseBean>() {
            @Override
            public void onResponse(Call<ImageResponseBean> call, Response<ImageResponseBean> response) {
                if (response.isSuccessful()) {
                    final ImageResponseBean imageResponseBean = response.body();
                    ThreadUtils.runOnSubThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = null;
                            File file = new File(mContext.getFilesDir().getPath() + "/bg.jpg");
                            try {
                                bitmap = BitmapFactory.decodeStream(new URL("http://wpstatic.zuimeia.com/" + imageResponseBean.getData().getImages().get(0).getImage_url() + "?imageMogr/v2/auto-orient/thumbnail/480x320/quality/100").openConnection().getInputStream());
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
                                ThreadUtils.runOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mMainView.onGetNavHeaderBackground();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    mMainView.onError();
                }
            }

            @Override
            public void onFailure(Call<ImageResponseBean> call, Throwable t) {
                mMainView.onError();
            }
        });
    }
}
