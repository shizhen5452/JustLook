package com.shizhen5452.justlook.activity;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.fragment.BaseFragment;
import com.shizhen5452.justlook.fragment.FragmentFactory;
import com.shizhen5452.justlook.utils.Constant;

/**
 * Create by EminemShi on 2017/2/3
 */

public class BaseActivity extends AppCompatActivity {
    protected BaseFragment currentFragment= FragmentFactory.getInstance().getFragmentByTag(Constant.TAG_ZHIHU_FRAGMENT);
    private ProgressDialog mProgressDialog;

    /**
     * 添加或显示Fragment
     */
    protected void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment) {
            return;
        }
        if (!fragment.isAdded()) {
            transaction.hide(currentFragment).add(R.id.fl_main, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment= (BaseFragment) fragment;
    }

    public void showProgressDialog(String content) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(content);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }
}
