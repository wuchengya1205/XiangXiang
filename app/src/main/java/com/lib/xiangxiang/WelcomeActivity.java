package com.lib.xiangxiang;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lib.xiangxiang.utils.CountdownDrawable;
import com.xiang.lib.ARouterPath;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {


    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initBar();
    }

    private void goActivity() {
        ARouter.getInstance()
                .build(ARouterPath.ROUTER_LOGIN)
                .navigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                goActivity();
                finish();
            }
        }, 1500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    private void initBar(){
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_BAR) // 隐藏导航栏或者状态栏
                .init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer = null;
    }
}
