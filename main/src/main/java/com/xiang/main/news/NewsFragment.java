package com.xiang.main.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.main.R;
import com.xiang.main.listener.RefreshListener;
import com.xiang.main.news.contract.NewsContract;
import com.xiang.main.news.presenter.NewsPresenter;
import com.xiang.main.news.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/12/10
 * time   :10:41
 * desc   :ohuo
 * version: 1.0
 */
public class NewsFragment extends BaseMvpFragment<NewsContract.IPresenter> implements NewsContract.IView, BannerViewPager.OnClickBannerListener, OnLoadMoreListener, ViewPager.OnPageChangeListener, TabLayout.BaseOnTabSelectedListener, OnRefreshListener {

    private List<String> tabData = new ArrayList<>();
    private List<String> bannerData = new ArrayList<>();
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BannerViewPager mBanner;
    private static SmartRefreshLayout smart_refresh;
    private static RefreshListener.bottom mLoadMoreListener;
    private static RefreshListener.top mRefreshListener;
    private static Boolean isRefresh = true; // 是刷新还是加载更多
    private static Boolean isCreate = false;  // 刷新完是否开启贝塞尔曲线

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {
        super.initView();
        mTabLayout = view.findViewById(R.id.tb_test);
        mViewPager = view.findViewById(R.id.vp_test);
        mBanner = view.findViewById(R.id.banner);
        smart_refresh = view.findViewById(R.id.smart_refresh);
    }

    @Override

    public void initData() {
        super.initData();
        initTabData();
        initBannerData();
        mBanner.initBanner(bannerData, false)//关闭3D画廊效果
                .addPageMargin(20, 50)//参数1page之间的间距,参数2中间item距离边界的间距
                .addPoint(bannerData.size() + 2)//添加指示器
                .addPointBottom(7)
                .addStartTimer(2)
                .addRoundCorners(5)//圆角
                .finishConfig()//这句必须加
                .addBannerListener(this);
        mViewPager.setAdapter(new NewsTabAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
        mTabLayout.addOnTabSelectedListener(this);
        smart_refresh.setOnRefreshListener(this);
        smart_refresh.setOnLoadMoreListener(this);
    }

    private void initBannerData() {
        bannerData.add("http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg");
        bannerData.add("https://ww1.sinaimg.cn/large/0065oQSqly1g2hekfwnd7j30sg0x4djy.jpg");
        bannerData.add("https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg");
        bannerData.add("https://ws1.sinaimg.cn/large/0065oQSqly1fytdr77urlj30sg10najf.jpg");
        bannerData.add("https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg");
        bannerData.add("https://ws1.sinaimg.cn/large/0065oQSqgy1fy58bi1wlgj30sg10hguu.jpg");
        bannerData.add("https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg");
        bannerData.add("https://ws1.sinaimg.cn/large/0065oQSqgy1fxd7vcz86nj30qo0ybqc1.jpg");
        bannerData.add("https://ws1.sinaimg.cn/large/0065oQSqgy1fwyf0wr8hhj30ie0nhq6p.jpg");
        bannerData.add("https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg");
    }

    private void initTabData() {
        tabData.add("头条");
        tabData.add("社会");
        tabData.add("国内");
        tabData.add("国际");
        tabData.add("娱乐");
        tabData.add("体育");
        tabData.add("军事");
        tabData.add("科技");
        tabData.add("财经");
        tabData.add("时尚");
    }

    @Override
    public Class<? extends NewsContract.IPresenter> registerPresenter() {
        return NewsPresenter.class;
    }

    /**
     * 轮播图点击
     *
     * @param position
     */
    @Override
    public void onBannerClick(int position) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mRefreshListener != null) {
            mRefreshListener.refresh();
            refreshLayout.finishRefresh(3000);

        }
        isRefresh = true;
        isCreate = true;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mRefreshListener != null) {
            mRefreshListener.loadMore();
            refreshLayout.finishLoadMore(3000);
        }
        isRefresh = false;
        isCreate = true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        isCreate = false;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        isCreate = false;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    class NewsTabAdapter extends FragmentStatePagerAdapter {

        public NewsTabAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            SingleNewsFragment newsFragment = new SingleNewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(SingleNewsFragment.TAB_KEY, tabData.get(position));
            newsFragment.setArguments(bundle);
            return newsFragment;
        }

        @Override
        public int getCount() {
            return tabData.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabData.get(position);
        }

    }

    public void setOnRefreshLoadMoreListener(RefreshListener.bottom listener) {
        mLoadMoreListener = listener;
    }

    public static void setOnRefreshLoadMoreListener(RefreshListener.top listener) {
        mRefreshListener = listener;
    }

    public static void finish() {

        if (isCreate) {
            if (isRefresh) {
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.finishRefresh();
                    smart_refresh.finishRefresh();
                }
            }
            if (!isRefresh) {
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.finishLoadMore();
                    smart_refresh.finishLoadMore();
                }
            }
        }

    }

    public static void finishNo(){
        if (isRefresh) {
            smart_refresh.finishRefresh();
        }
        if (!isRefresh) {
            smart_refresh.finishLoadMore();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLoadMoreListener = null;
        mRefreshListener = null;
        smart_refresh = null;
    }
}
