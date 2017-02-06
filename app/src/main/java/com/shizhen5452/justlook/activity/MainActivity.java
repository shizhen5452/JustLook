package com.shizhen5452.justlook.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.fragment.FragmentFactory;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.fl_main)
    FrameLayout    mFlMain;
    @BindView(R.id.dl_main)
    DrawerLayout   mDlMain;
    @BindView(R.id.nav_main)
    NavigationView mNavMain;
    private FragmentManager mFragmentManager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        mFragmentManager.beginTransaction().add(R.id.fl_main, FragmentFactory.getFragmentByTag(Constant.TAG_ZHIHU_FRAGMENT)).commit();
    }

    public void initToolbar(Toolbar toolbar, String title) {
        mToolbar=toolbar;
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);


        //显示Toolbar左侧菜单并设置点击事件
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDlMain, mToolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        mDlMain.addDrawerListener(drawerToggle);
    }

    private void initData() {

    }

    private void initListener() {
        mNavMain.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.zhihu:
                mDlMain.closeDrawers();
                addOrShowFragment(mFragmentManager.beginTransaction(), FragmentFactory.getFragmentByTag(Constant.TAG_ZHIHU_FRAGMENT));
                mToolbar.setTitle(R.string.zhihu_daliy);
                break;
            case R.id.netease:
                mDlMain.closeDrawers();
                addOrShowFragment(mFragmentManager.beginTransaction(), FragmentFactory.getFragmentByTag(Constant.TAG_NETEASE_FRAGMENT));
                mToolbar.setTitle(R.string.netease_news);
                break;
            case R.id.gank:
                mDlMain.closeDrawers();
                addOrShowFragment(mFragmentManager.beginTransaction(), FragmentFactory.getFragmentByTag(Constant.TAG_GANK_FRAGMENT));
                mToolbar.setTitle(R.string.gank_io);
                break;
            case R.id.setting:
                mDlMain.closeDrawers();
                ToastUtils.showShortToast(this, "设置");
                break;
            case R.id.about:
                mDlMain.closeDrawers();
                ToastUtils.showShortToast(this, "关于");
                break;
        }
        return true;
    }
}
