package com.xiang.main.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.main.R;
import com.xiang.main.listener.RefreshListener;
import com.xiang.main.chat.ChatPageFragment;
import com.xiang.main.contract.MainFContract;
import com.xiang.main.news.NewsFragment;
import com.xiang.main.online.OnlinePageFragment;
import com.xiang.main.presenter.MainFPresenter;
import com.xiang.main.utils.BezierTypeEvaluator;
import com.xiang.main.video.VideoPageFragment;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/12/10
 * time   :10:24
 * desc   :ohuo
 * version: 1.0
 */
public class MainFragment extends BaseMvpFragment<MainFContract.IPresenter> implements MainFContract.IView, BottomNavigationBar.OnTabSelectedListener, RefreshListener.bottom {

    private FrameLayout main_frame;
    private BottomNavigationBar bottom_nav_bar;
    private TextBadgeItem badgeItem;
    private FragmentTransaction transaction;
    private Fragment mFragment;
    private NewsFragment firstPageFragment;
    private VideoPageFragment videoPageFragment;
    private ChatPageFragment chatPageFragment;
    private OnlinePageFragment onlinePageFragment;
    private int tabPosition = 0;
    private int sePosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public Class<? extends MainFContract.IPresenter> registerPresenter() {
        return MainFPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        main_frame = view.findViewById(R.id.main_frame);
        bottom_nav_bar = view.findViewById(R.id.bottom_nav_bar);
    }

    @Override
    public void initBar() {
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR) // 隐藏导航栏或者状态栏
                .init();
    }

    @Override
    public void initData() {
        super.initData();
        initBottomNavigationBar();
        initFragment();
    }

    private void initFragment() {
        firstPageFragment = new NewsFragment();
        videoPageFragment = new VideoPageFragment();
        chatPageFragment = new ChatPageFragment();
        onlinePageFragment = new OnlinePageFragment();
        transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.main_frame, firstPageFragment).commit();
        mFragment = firstPageFragment;
        firstPageFragment.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initBar();
    }

    private void initBottomNavigationBar() {
        bottom_nav_bar
                .setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setActiveColor("#ffffff")//选中颜色
                .setInActiveColor("#2B2B2B")//未选中颜色
                .setBarBackgroundColor("#EDC18E");//导航栏背景色
        badgeItem = new TextBadgeItem()
                .setBorderWidth(3)
                .setTextColor(Color.WHITE)
                .setBackgroundColor(Color.RED)
                .setText("66");

        /**
         *添加导航按钮
         */
        bottom_nav_bar.addItem(
                new BottomNavigationItem(R.mipmap.frist, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.frist, "视频").setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.frist, "聊天").setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.frist, "直播").setBadgeItem(badgeItem))//添加小红点数据
                .initialise();//initialise 一定要放在 所有设置的最后一项
    }

    @Override
    public void onTabSelected(int position) {
        tabPosition = (position + 1);
        sePosition = position;
        switch (position) {
            case 0:
                switchFragment(firstPageFragment);
                break;
            case 1:
                switchFragment(videoPageFragment);
                break;
            case 2:
                switchFragment(chatPageFragment);
                break;
            case 3:
                switchFragment(onlinePageFragment);
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void switchFragment(Fragment fragment) {
        //判断当前显示的Fragment是不是切换的Fragment
        if (mFragment != fragment) {
            //判断切换的Fragment是否已经添加过
            if (!fragment.isAdded()) {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getChildFragmentManager()
                        .beginTransaction()
                        .hide(mFragment)
                        .add(R.id.main_frame, fragment)
                        .commit();
            } else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getChildFragmentManager()
                        .beginTransaction()
                        .hide(mFragment)
                        .show(fragment)
                        .commit();
            }
            mFragment = fragment;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    public void Refresh() {
        View at = bottom_nav_bar.getChildAt(sePosition);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //贝塞尔结束数据点
        int[] endPosition = new int[2];
        at.getLocationInWindow(endPosition);

        PointF startF = new PointF();
        PointF endF = new PointF();
        PointF controllF = new PointF();

        startF.x = 0;
        startF.y = dip2px(getContext(), 60);
        endF.x = at.getX() / 4 * tabPosition;
        endF.y = height-at.getY()/2;
        controllF.x = width;
        controllF.y = endF.y / 4;

        final ImageView imageView = new ImageView(getContext());
        main_frame.addView(imageView);
        imageView.setImageResource(R.mipmap.xx);
        imageView.getLayoutParams().width = dip2px(getContext(), 40);
        imageView.getLayoutParams().height = dip2px(getContext(), 40);
        imageView.setVisibility(View.VISIBLE);
        imageView.setX(0);
        imageView.setY(height / 7);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierTypeEvaluator(controllF), startF, endF);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });


        ObjectAnimator objectAnimatorX = new ObjectAnimator().ofFloat(at, "scaleX", 0.8f, 1.0f);
        ObjectAnimator objectAnimatorY = new ObjectAnimator().ofFloat(at, "scaleY", 0.8f, 1.0f);
        objectAnimatorX.setInterpolator(new AccelerateInterpolator());
        objectAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
//        set.play(objectAnimatorX).with(objectAnimatorY).after(valueAnimator);
        set.play(valueAnimator);
        set.setDuration(800);
        set.start();
    }

    public int dip2px(Context context, float dipValue) {
        return (int) (dipValue * (getScreenDensity(context) / 160f) + 0.5f);
    }

    @SuppressWarnings("deprecation")
    public int getScreenDensity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;

    }


    @Override
    public void finishRefresh() {
        Refresh();
    }


    @Override
    public void finishLoadMore() {

    }
}
