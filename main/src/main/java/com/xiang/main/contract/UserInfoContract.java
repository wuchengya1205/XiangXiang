package com.xiang.main.contract;

import com.xiang.lib.allbean.LoginBean;

import mvp.ljb.kt.contract.IPresenterContract;
import mvp.ljb.kt.contract.IViewContract;

/**
 * author : fengzhangwei
 * date : 2019/12/11
 */
public interface UserInfoContract {

    interface IView extends IViewContract {
        String getIconUrl();
        String getName();
        String getLocation();
        String getSign();
        String getEmail();
        void onError(String msg);
        void onSuccess();
    }

    interface IPresenter extends IPresenterContract {
        void changeUserInfo(LoginBean bean);
    }
}
