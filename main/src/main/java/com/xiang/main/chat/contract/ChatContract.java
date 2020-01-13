package com.xiang.main.chat.contract;

import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.chatBean.ChatMessage;

import java.util.List;

import mvp.ljb.kt.contract.IPresenterContract;
import mvp.ljb.kt.contract.IViewContract;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/10/24
 * time   :15:07
 * desc   :ohuo
 * version: 1.0
 */
public interface ChatContract {
    interface IView extends IViewContract {
        void onError(String msg);
        void onSuccessInfo(LoginBean bean);
        void onSuccessHistory(List<ChatMessage> list);
        void onSuccessRedEnvelope(ChatMessage data);
        void onSuccessNull();
    }

    interface IPresenter extends IPresenterContract {
        void getUserInfo(String uid);
        void getHistory(String conversation,int pageNo,int pageSize);
        void updateEnvelope(String fromId,String toId,String pid);
        void updateMoney(String money);
    }
}
