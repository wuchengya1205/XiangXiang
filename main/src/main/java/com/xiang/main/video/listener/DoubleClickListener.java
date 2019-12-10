package com.xiang.main.video.listener;

import android.view.View;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/11/8
 * time   :17:23
 * desc   :ohuo
 * version: 1.0
 */
public abstract class DoubleClickListener implements View.OnClickListener {
    private static final long DOUBLE_TIME = 500;
    private static long lastClickTime = 0;

    @Override
    public void onClick(View view) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTime < DOUBLE_TIME) {
            onDoubleClick(view);
        }else {
            onSingleClick(view);
        }
        lastClickTime = currentTimeMillis;
    }

    public abstract void onDoubleClick(View v);

    public abstract void onSingleClick(View v);

    }
