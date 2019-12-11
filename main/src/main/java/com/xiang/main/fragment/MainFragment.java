package com.xiang.main.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
@SuppressLint("RestrictedApi")
public class MainFragment extends BaseMvpFragment<MainFContract.IPresenter> implements MainFContract.IView, RefreshListener.bottom, View.OnClickListener {

    private FragmentTransaction transaction;
    private Fragment mFragment;
    private NewsFragment firstPageFragment;
    private VideoPageFragment videoPageFragment;
    private ChatPageFragment chatPageFragment;
    private OnlinePageFragment onlinePageFragment;
    private FloatingActionButton fabAll;
    private FloatingActionButton fabNews;
    private FloatingActionButton fabVideo;
    private FloatingActionButton fabChat;
    private FloatingActionButton fabOnline;
    boolean flag = false;

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
        fabAll = view.findViewById(R.id.fabAll);
        fabNews = view.findViewById(R.id.fabNews);
        fabVideo = view.findViewById(R.id.fabVideo);
        fabChat = view.findViewById(R.id.fabChat);
        fabOnline = view.findViewById(R.id.fabOnline);
        fabAll.setOnClickListener(this);
        fabNews.setOnClickListener(this);
        fabVideo.setOnClickListener(this);
        fabChat.setOnClickListener(this);
        fabOnline.setOnClickListener(this);
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

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.fabAll) {
                if (!flag) {
                    show();
                } else {
                    gone();
                }
            } else if (id == R.id.fabNews) {
                switchFragment(firstPageFragment);
            } else if (id == R.id.fabVideo) {
                switchFragment(videoPageFragment);
            } else if (id == R.id.fabChat) {
                switchFragment(chatPageFragment);
            } else if (id == R.id.fabOnline) {
                switchFragment(onlinePageFragment);
            }
        }

    private void gone() {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator objectAnimator10 = ObjectAnimator.ofFloat(fabNews,"translationX",-100f,0f);
        ObjectAnimator objectAnimator20= ObjectAnimator.ofFloat(fabNews,"rotation",0f,360f);
        ObjectAnimator objectAnimator30= ObjectAnimator.ofFloat(fabNews,"alpha",1f,0f);

        ObjectAnimator translationX20 = ObjectAnimator.ofFloat(fabVideo,"translationX",-50f,0f);
        ObjectAnimator translationY21 = ObjectAnimator.ofFloat(fabVideo,"translationY",-50f,0f);
        ObjectAnimator rotation22 = ObjectAnimator.ofFloat(fabVideo,"rotation",0f,360f);
        ObjectAnimator alpha23 = ObjectAnimator.ofFloat(fabVideo,"alpha",1f,0f);

        ObjectAnimator translationX30 = ObjectAnimator.ofFloat(fabChat,"translationX",-100f,0f);
        ObjectAnimator translationY31 = ObjectAnimator.ofFloat(fabChat,"translationY",-100f,0f);
        ObjectAnimator rotation32 = ObjectAnimator.ofFloat(fabChat,"rotation",0f,360f);
        ObjectAnimator alpha33 = ObjectAnimator.ofFloat(fabChat,"alpha",1f,0f);

        ObjectAnimator objectAnimator40 = ObjectAnimator.ofFloat(fabOnline,"translationY",-100f,0f);
        ObjectAnimator objectAnimator41= ObjectAnimator.ofFloat(fabOnline,"rotation",0f,360f);
        ObjectAnimator objectAnimator42= ObjectAnimator.ofFloat(fabOnline,"alpha",1f,0f);

        animatorSet.play(objectAnimator10).with(objectAnimator20).with(objectAnimator30)
                .with(translationX20).with(translationY21).with(rotation22).with(alpha23)
                .with(translationX30).with(translationY31).with(rotation32).with(alpha33)
                .with(objectAnimator40).with(objectAnimator41).with(objectAnimator42);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                flag = false;
                fabNews.setVisibility(View.GONE);
                fabVideo.setVisibility(View.GONE);
                fabChat.setVisibility(View.GONE);
                fabOnline.setVisibility(View.GONE);
            }
        });
    }

    private void show() {
        AnimatorSet animatorSet = new AnimatorSet();
        fabNews.setVisibility(View.VISIBLE);
        fabVideo.setVisibility(View.VISIBLE);
        fabChat.setVisibility(View.VISIBLE);
        fabOnline.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator10 = ObjectAnimator.ofFloat(fabNews,"translationX",0f,-100f);
        ObjectAnimator objectAnimator20= ObjectAnimator.ofFloat(fabNews,"rotation",0f,360f);
        ObjectAnimator objectAnimator30= ObjectAnimator.ofFloat(fabNews,"alpha",0f,1f);

        ObjectAnimator translationX20 = ObjectAnimator.ofFloat(fabVideo,"translationX",0f,-50f);
        ObjectAnimator translationY21 = ObjectAnimator.ofFloat(fabVideo,"translationY",0f,-50f);
        ObjectAnimator rotation22 = ObjectAnimator.ofFloat(fabVideo,"rotation",0f,360f);
        ObjectAnimator alpha23 = ObjectAnimator.ofFloat(fabVideo,"alpha",0f,1f);

        ObjectAnimator translationX30 = ObjectAnimator.ofFloat(fabChat,"translationX",0f,-100f);
        ObjectAnimator translationY31 = ObjectAnimator.ofFloat(fabChat,"translationY",0f,-100f);
        ObjectAnimator rotation32 = ObjectAnimator.ofFloat(fabChat,"rotation",0f,360f);
        ObjectAnimator alpha33 = ObjectAnimator.ofFloat(fabChat,"alpha",0f,1f);

        ObjectAnimator objectAnimator40 = ObjectAnimator.ofFloat(fabOnline,"translationY",0f,-100f);
        ObjectAnimator objectAnimator41= ObjectAnimator.ofFloat(fabOnline,"rotation",0f,360f);
        ObjectAnimator objectAnimator42= ObjectAnimator.ofFloat(fabOnline,"alpha",0f,1f);

        animatorSet.play(objectAnimator10).with(objectAnimator20).with(objectAnimator30)
                .with(translationX20).with(translationY21).with(rotation22).with(alpha23)
                .with(translationX30).with(translationY31).with(rotation32).with(alpha33)
                .with(objectAnimator40).with(objectAnimator41).with(objectAnimator42);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                flag = true;
            }
        });

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

   /* public void Refresh() {
        View at = fabAll.getChildAt(sePosition);
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
    }*/

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
//        Refresh();
    }


    @Override
    public void finishLoadMore() {

    }


}
