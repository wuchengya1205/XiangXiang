package com.xiang.main.online;


import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.main.R;
import com.xiang.main.online.contract.OnlinePageContract;
import com.xiang.main.online.presenter.OnlinPagePresenter;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/10/24
 * time   :15:10
 * desc   :ohuo
 * version: 1.0
 */
public class OnlinePageFragment extends BaseMvpFragment<OnlinePageContract.IPresenter> implements OnlinePageContract.IView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_online;
    }


    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public Class<? extends OnlinePageContract.IPresenter> registerPresenter() {
        return OnlinPagePresenter.class;
    }
}
