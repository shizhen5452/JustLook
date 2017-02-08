package com.shizhen5452.justlook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by EminemShi on 2017/2/3
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutResId(), container, false);
        bind = ButterKnife.bind(this, view);
        initView();
        initListener();
        initData();
        return view;
    }

    protected abstract int setLayoutResId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
