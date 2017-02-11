package com.shizhen5452.justlook.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.fragment.BookmarkFragment;
import com.shizhen5452.justlook.fragment.FragmentFactory;
import com.shizhen5452.justlook.fragment.ZhihuDaliyFragment;
import com.shizhen5452.justlook.presenter.MainPresenter;
import com.shizhen5452.justlook.presenter.presenterimpl.MainPresenterImpl;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.ToastUtils;
import com.shizhen5452.justlook.view.MainView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView, NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.fl_main)
    FrameLayout    mFlMain;
    @BindView(R.id.dl_main)
    DrawerLayout   mDlMain;
    @BindView(R.id.nav_main)
    NavigationView mNavMain;
    @BindView(R.id.toolbar)
    Toolbar        mToolbar;
    private FragmentManager mFragmentManager;
    private MainPresenter   mMainPresenter;
    private ZhihuDaliyFragment mZhihuDaliyFragment;
    private MenuItem currentItem;
    private BookmarkFragment mBookmarkFragment;
    private long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        initView();
        initData();
        initListener();


    }

    public void initToolbar() {
//        StatusBarUtils.setTranslucentImageHeader(this,0,mToolbar);
        setSupportActionBar(mToolbar);

        //显示Toolbar左侧菜单并设置点击事件
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDlMain, mToolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        mDlMain.addDrawerListener(drawerToggle);
    }

    private void initView() {
        mZhihuDaliyFragment = (ZhihuDaliyFragment) FragmentFactory.getFragmentByTag(Constant.TAG_ZHIHU_FRAGMENT);
        mBookmarkFragment = (BookmarkFragment) FragmentFactory.getFragmentByTag(Constant.TAG_BOOKMARK_FRAGMENT);

        //默认显示知乎日报界面
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        mFragmentManager.beginTransaction().add(R.id.fl_main, mZhihuDaliyFragment).commit();

        //获取菜单头布局背景图片
        mMainPresenter = new MainPresenterImpl(this);
        mMainPresenter.getNavHeaderBackground();

        currentItem=mNavMain.getMenu().findItem(R.id.zhihu_item);
    }

    private void initData() {

    }

    private void initListener() {
        mNavMain.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDlMain.closeDrawers();
        switch (item.getItemId()) {
            case R.id.zhihu_item:
                addOrShowFragment(mFragmentManager.beginTransaction(), mZhihuDaliyFragment);
                mToolbar.setTitle(R.string.zhihu_daliy);
                break;
            case R.id.bookmark_item:
                addOrShowFragment(mFragmentManager.beginTransaction(), mBookmarkFragment);
                mToolbar.setTitle(R.string.bookmark);
                break;
            case R.id.theme_item:
                ToastUtils.showShortToast(this, "主题");
                break;
            case R.id.setting_item:
                ToastUtils.showShortToast(this, "设置");
                break;
        }
        setMenuItemState(item);
        return true;
    }

    private void setMenuItemState(MenuItem item) {
        if (currentItem != item) {
            currentItem.setChecked(false);
            currentItem=item;
            currentItem.setChecked(true);
        }
    }

    @Override
    public void onGetNavHeaderBackground() {
        File file = new File(getFilesDir().getPath() + "/bg.jpg");
        View headerView = mNavMain.getHeaderView(0);
        ImageView ivNavHeader = (ImageView) headerView.findViewById(R.id.iv_nav_header);
        if (file.exists()) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), getFilesDir().getPath() + "/bg.jpg");
            ivNavHeader.setBackground(bitmapDrawable);
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()- lastTime > 2000) {
            lastTime = System.currentTimeMillis();
            ToastUtils.showShortToast(this, getResources().getString(R.string.exit));
        } else {
            super.onBackPressed();
        }
    }
}
