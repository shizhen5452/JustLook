package com.shizhen5452.justlook.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.fragment.BookmarkFragment;
import com.shizhen5452.justlook.fragment.FragmentFactory;
import com.shizhen5452.justlook.fragment.ZhihuDaliyFragment;
import com.shizhen5452.justlook.presenter.MainPresenter;
import com.shizhen5452.justlook.presenter.presenterimpl.MainPresenterImpl;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.SPUtils;
import com.shizhen5452.justlook.utils.ToastUtils;
import com.shizhen5452.justlook.view.MainView;
import com.shizhen5452.justlook.widget.FlowRadioGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private List<RadioButton> mRadioButtonList=new ArrayList<>();
    private int[] mThemes={R.style.TealTheme,R.style.RedTheme,R.style.PurpleTheme,R.style.BlueTheme,R.style.GreenTheme,R.style.OrangeTheme,R.style.BrownTheme,R.style.GreyTheme,R.style.BlackTheme};
    private int mThemeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mThemeId = SPUtils.getInstance(this).getThemeId(Constant.SP_KEY_THEME_ID, -1);
        if (mThemeId != -1) {
            setTheme(mThemeId);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        initView();
        initData();
        initListener();
    }

    public void initToolbar() {
        setSupportActionBar(mToolbar);

        //显示Toolbar左侧菜单并设置点击事件
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDlMain, mToolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        mDlMain.addDrawerListener(drawerToggle);
    }

    private void initView() {
        mZhihuDaliyFragment = (ZhihuDaliyFragment) FragmentFactory.getInstance().getFragmentByTag(Constant.TAG_ZHIHU_FRAGMENT);
        mBookmarkFragment = (BookmarkFragment) FragmentFactory.getInstance().getFragmentByTag(Constant.TAG_BOOKMARK_FRAGMENT);

        //默认显示知乎日报界面
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        mFragmentManager.beginTransaction().replace(R.id.fl_main, mZhihuDaliyFragment).commit();

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
                setMenuItemState(item);
                break;
            case R.id.bookmark_item:
                addOrShowFragment(mFragmentManager.beginTransaction(), mBookmarkFragment);
                mToolbar.setTitle(R.string.bookmark);
                setMenuItemState(item);
                break;
            case R.id.theme_item:
                showColorSelectDialog();
                break;
            case R.id.setting_item:
                ToastUtils.showShortToast(this, "设置");
                break;
        }
        return true;
    }

    private void showColorSelectDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        mRadioButtonList.clear();

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_color_select, null);
        FlowRadioGroup flowRadioGroup = (FlowRadioGroup) view.findViewById(R.id.flowRadioGroup);

        RadioButton rbTeal= (RadioButton) view.findViewById(R.id.rb_teal);
        RadioButton rbRed= (RadioButton) view.findViewById(R.id.rb_red);
        RadioButton rbPurple= (RadioButton) view.findViewById(R.id.rb_purple);
        RadioButton rbBlue= (RadioButton) view.findViewById(R.id.rb_blue);
        RadioButton rbGreen= (RadioButton) view.findViewById(R.id.rb_green);
        RadioButton rbOrange= (RadioButton) view.findViewById(R.id.rb_orange);
        RadioButton rbBrown= (RadioButton) view.findViewById(R.id.rb_brown);
        RadioButton rbGrey= (RadioButton) view.findViewById(R.id.rb_grey);
        RadioButton rbBlack= (RadioButton) view.findViewById(R.id.rb_black);

        mRadioButtonList.add(rbTeal);
        mRadioButtonList.add(rbRed);
        mRadioButtonList.add(rbPurple);
        mRadioButtonList.add(rbBlue);
        mRadioButtonList.add(rbGreen);
        mRadioButtonList.add(rbOrange);
        mRadioButtonList.add(rbBrown);
        mRadioButtonList.add(rbGrey);
        mRadioButtonList.add(rbBlack);

        if (mThemeId != -1) {
            for (int i = 0; i < mThemes.length; i++) {
                if (mThemes[i] == mThemeId) {
                    mRadioButtonList.get(i).setChecked(true);
                }
            }
        } else {
            mRadioButtonList.get(0).setChecked(true);
        }

        builder.setTitle("请选择主题颜色");
        builder.setView(view);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                for (int i = 0; i < mRadioButtonList.size(); i++) {
                    if (mRadioButtonList.get(i).isChecked()) {
                        SPUtils.getInstance(MainActivity.this).putThemeId(Constant.SP_KEY_THEME_ID,mThemes[i]);
                    }
                }
                ToastUtils.showShortToast(MainActivity.this,"重启应用后生效");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
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
        if (mDlMain.isDrawerOpen(GravityCompat.START)) {
            mDlMain.closeDrawers();
        }
        if (System.currentTimeMillis()- lastTime > 2000) {
            lastTime = System.currentTimeMillis();
            ToastUtils.showShortToast(this, getResources().getString(R.string.exit));
        } else {
            super.onBackPressed();
        }
    }

    private void reLoadActivity() {
        Intent intent =getIntent();
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0,0);
        startActivity(intent);
    }

}
