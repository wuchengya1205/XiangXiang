package com.xiang.main.chat.contract;

import com.xiang.lib.allbean.AttnBean;
import com.xiang.lib.allbean.LoginBean;

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
public interface AttnInfoContract {

    interface IView extends IViewContract {
        void onSuccess(LoginBean bean);
        void onError(String msg);
    }

    interface IPresenter extends IPresenterContract {
        void getInfo(String uid);
    }
}
