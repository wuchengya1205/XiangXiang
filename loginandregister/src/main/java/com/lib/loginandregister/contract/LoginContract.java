package com.lib.loginandregister.contract;

import mvp.ljb.kt.contract.IPresenterContract;
import mvp.ljb.kt.contract.IViewContract;


public interface LoginContract {

    interface IView extends IViewContract {

        void loginSuccess();

        void loginError(String msg);

        String getPhone();

        String getPassword();

    }

    interface IPresenter extends IPresenterContract {

        void login();
    }
}
