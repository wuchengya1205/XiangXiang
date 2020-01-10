package com.xiang.main.contract;

import java.math.BigDecimal;

import mvp.ljb.kt.contract.IPresenterContract;
import mvp.ljb.kt.contract.IViewContract;

/**
 * author : fengzhangwei
 * date : 2020/01/08
 */
public interface RedEnvelopeContract {

    interface IView extends IViewContract {
        void onSuccess(BigDecimal money);
        void onError(String msg);
        String getInputMoney();
    }

    interface IPresenter extends IPresenterContract {
        void sendRedEnvelope();
    }
}
