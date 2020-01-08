package com.xiang.main.chat.fragment;

import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.main.R;
import com.xiang.main.chat.contract.ChatMsgContract;
import com.xiang.main.chat.presenter.ChatMsgPresenter;

/**
 * author : fengzhangwei
 * date : 2019/12/23
 */
public class ChatMsgFragment extends BaseMvpFragment<ChatMsgContract.IPresenter> implements ChatMsgContract.IView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_msg;
    }

    @Override
    public Class<? extends ChatMsgContract.IPresenter> registerPresenter() {
        return ChatMsgPresenter.class;
    }
}
