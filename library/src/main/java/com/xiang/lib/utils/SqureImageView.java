package com.xiang.lib.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * author : fengzhangwei
 * date : 2020/1/8
 */
public class SqureImageView extends AppCompatImageView {
    public SqureImageView(Context context) {
        super(context);
    }

    public SqureImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //让宽度的测量尺寸代替高度尺寸
        super.onMeasure(widthMeasureSpec,widthMeasureSpec);
    }
}
