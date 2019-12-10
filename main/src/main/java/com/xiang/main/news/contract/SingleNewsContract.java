package com.xiang.main.news.contract;



import com.xiang.lib.allbean.CommonBean;

import mvp.ljb.kt.contract.IPresenterContract;
import mvp.ljb.kt.contract.IViewContract;

/**
 * author : fengzhangwei
 * date : 2019/11/12
 */
public interface SingleNewsContract {

    interface IView extends IViewContract {
        String getType();
        void onError(String msg);
        void onSuccess(CommonBean commonBean);
    }

    interface IPresenter extends IPresenterContract {
        void getNewsData();
    }
}
