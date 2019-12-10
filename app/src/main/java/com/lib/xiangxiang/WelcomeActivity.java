package com.lib.xiangxiang;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lib.xiangxiang.utils.CountdownDrawable;
import com.xiang.lib.ARouterPath;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView countdown;
    private CountdownDrawable mCdDrawable;
    private Animator animator;
    private ObjectAnimator progressAnimator;
    boolean isEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initBar();
        if (animator != null){
            animator.cancel();
        }
        mCdDrawable = new CountdownDrawable(
                getResources().getDimensionPixelSize(R.dimen.dp_4), getResources().getColor(R.color.dark_grey), getResources().getColor(R.color.color_238AFF), getResources().getColor(R.color.holo_green_light), 3, getResources().getColor(R.color.colorWhite));
        countdown.setImageDrawable(mCdDrawable);
        animator = prepareAnimator();
        animator.start();
    }

    private void goActivity() {
        ARouter.getInstance()
                .build(ARouterPath.ROUTER_LOGIN)
                .withTransition(R.anim.fade_in, R.anim.fade_out)
                .navigation();
        finish();
    }

    private void initView() {
        countdown = (ImageView) findViewById(R.id.countdown);
        countdown.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.countdown) {
            isEnd = true;
            goActivity();
        }
    }

    private Animator prepareAnimator() {
        AnimatorSet animation = new AnimatorSet();

        // 进度条动画
        progressAnimator = ObjectAnimator.ofFloat(mCdDrawable, "progress", 1f, 0f);
        progressAnimator.setDuration(3000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                countdown.setVisibility(View.GONE);
                if (!isEnd){
                    goActivity();
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                countdown.setVisibility(View.GONE);
            }
        });

        // 居中的倒计时数字
        ObjectAnimator showNumberAnimator = ObjectAnimator.ofInt(mCdDrawable, "showNumber", 3, 0);
        showNumberAnimator.setDuration(3000);
        showNumberAnimator.setInterpolator(new LinearInterpolator());
        animation.playTogether(progressAnimator, showNumberAnimator);
        return animation;
    }

    private void initBar(){
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_BAR) // 隐藏导航栏或者状态栏
                .init();
    }
}
