package com.xiang.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.xiang.lib.ARouterPath;
import com.xiang.lib.base.ac.BaseActivity;
import com.xiang.main.fragment.MainFragment;
import com.xiang.main.fragment.MenuFragment;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.ROUTER_MAIN)
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private List<Fragment> fList = new ArrayList<>(2);
    public static ViewPager mViewPager;
    private JPTabBar tabbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        mViewPager = findViewById(R.id.vp_access);
        tabbar = findViewById(R.id.tabbar);
    }

    @Titles
    private static final String[] mTitles = {"新闻","视频","聊天","直播"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.mipmap.news,R.mipmap.video,R.mipmap.chat,R.mipmap.online};

    @NorIcons
    private static final int[] mNormalIcons = {R.mipmap.news,R.mipmap.video,R.mipmap.chat,R.mipmap.online};

    @Override
    public void initData() {
        super.initData();
        initBar();
        fList.add(new MenuFragment());
        fList.add(new MainFragment());
        mViewPager.setAdapter(new AccessPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(1);
        tabbar.setContainer(mViewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR) // 隐藏导航栏或者状态栏
                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为 true)
                .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为 true）
                .statusBarAlpha(0.5f)  //状态栏透明度，不写默认 0.0f
                .navigationBarAlpha(0.2f)  //导航栏透明度，不写默认 0.0F
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .init();
    }

    private class AccessPagerAdapter extends FragmentPagerAdapter {

        public AccessPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fList.get(position);
        }

        @Override
        public int getCount() {
            return fList.size();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mViewPager.getCurrentItem() != 1){
                mViewPager.setCurrentItem(1);
            }else {
                moveTaskToBack(false);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void openHome(){
        mViewPager.setCurrentItem(1);
    }
}
