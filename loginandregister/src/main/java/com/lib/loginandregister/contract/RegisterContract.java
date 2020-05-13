package com.lib.loginandregister.contract;

import mvp.ljb.kt.contract.IPresenterContract;
import mvp.ljb.kt.contract.IViewContract;


public interface RegisterContract {

    interface IView extends IViewContract {
        String getName();
        String getEmail();
        String getLocation();
        String getMobile();
        String getPassword();
        void onSuccess();
        void onError(String msg);
    }

    interface IPresenter extends IPresenterContract {
        void register();
    }
}
