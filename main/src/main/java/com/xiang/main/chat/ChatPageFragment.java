package com.xiang.main.chat;


import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.main.R;
import com.xiang.main.chat.contract.ChatPageContract;
import com.xiang.main.chat.presenter.ChatPagePresenter;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/10/24
 * time   :15:06
 * desc   :ohuo
 * version: 1.0
 */
public class ChatPageFragment extends BaseMvpFragment<ChatPageContract.IPresenter> implements ChatPageContract.IView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
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
    public Class<? extends ChatPageContract.IPresenter> registerPresenter() {
        return ChatPagePresenter.class;
    }
}
