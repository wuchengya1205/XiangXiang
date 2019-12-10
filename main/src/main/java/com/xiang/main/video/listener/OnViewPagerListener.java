package com.xiang.main.video.listener;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/11/5
 * time   :15:02
 * desc   :ohuo
 * version: 1.0
 */
public interface OnViewPagerListener {
    /**
     * 初始化第一个View
     */
    void onInitComplete();


    /**
     * 选中的监听以及判断是否滑动到底部
     *
     * @param position
     * @param isBottom
     */
    void onPageSelected(int position, boolean isBottom);


    /**
     * 释放的监听
     *
     * @param isNext
     * @param position
     */
    void onPageRelease(boolean isNext, int position);
}
