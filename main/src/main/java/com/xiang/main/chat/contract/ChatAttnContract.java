package com.xiang.main.chat.contract;

import com.xiang.lib.allbean.AttnBean;

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
public interface ChatAttnContract {
    interface IView extends IViewContract {
        void onSuccess(List<AttnBean> data);
        void onError(String msg);
    }

    interface IPresenter extends IPresenterContract {
        void getAllAttn();
    }
}
