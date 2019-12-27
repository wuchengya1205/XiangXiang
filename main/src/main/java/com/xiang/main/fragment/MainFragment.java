package com.xiang.main.fragment;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.main.R;
import com.xiang.main.chat.fragment.ChatPageFragment;
import com.xiang.main.contract.MainFContract;
import com.xiang.main.news.NewsFragment;
import com.xiang.main.presenter.MainFPresenter;
import com.xiang.main.video.VideoPageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/12/10
 * time   :10:24
 * desc   :ohuo
 * version: 1.0
 */
@SuppressLint("RestrictedApi")
public class MainFragment extends BaseMvpFragment<MainFContract.IPresenter> implements MainFContract.IView,BottomBarLayout.OnItemSelectedListener {

    private FragmentTransaction transaction;
    private Fragment mFragment;
    private BottomBarLayout bottom_bar;
    private List<Fragment> list = new ArrayList<>(3);

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
        bottom_bar = view.findViewById(R.id.bottom_bar);
    }

    @Override
    public void initData() {
        super.initData();
        initFragment();
        bottom_bar.setOnItemSelectedListener(this);
    }

    private void initFragment() {
        list.add(new NewsFragment());
        list.add(new VideoPageFragment());
        list.add(new ChatPageFragment());
//        list.add(new OnlinePageFragment());
        transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.main_frame, list.get(0)).commit();
        mFragment = list.get(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        initBar();
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


    @Override
    public void onItemSelected(BottomBarItem bottomBarItem, int i, int i1) {
        switchFragment(list.get(i1));
    }
}
