package com.xiang.main.presenter;


import android.util.Log;

import androidx.annotation.NonNull;

import com.lib.xiangxiang.im.GsonUtil;
import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.BaseMvpPresenter;
import com.xiang.lib.base.BaseObserverTC;
import com.xiang.lib.net.IHttpProtocol;
import com.xiang.lib.utils.ArithmeticUtils;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.SPUtils;
import com.xiang.main.contract.RedEnvelopeContract;

import net.ljb.kt.client.HttpFactory;

import java.math.BigDecimal;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : fengzhangwei
 * date : 2020/01/08
 */
public class RedEnvelopePresenter extends BaseMvpPresenter<RedEnvelopeContract.IView> implements RedEnvelopeContract.IPresenter{

    @Override
    public void sendRedEnvelope() {
        String inputMoney = getMvpView().getInputMoney();
        String json = SPUtils.getInstance().getString(Constant.SPKey_USERINFO);
        LoginBean bean = GsonUtil.GsonToBean(json, LoginBean.class);
        BigDecimal money = bean.getMoney();
        BigDecimal sub = ArithmeticUtils.sub(money, new BigDecimal(inputMoney));
        if (!sub.equals("0.00")){
            boolean b = ArithmeticUtils.compare(sub, new BigDecimal("0.00"));
            if (b){
                bean.setMoney(sub);
                updateMoney(new BigDecimal(inputMoney),bean);
            }else {
                getMvpView().onError("余额不足.");
            }
        }else {
            bean.setMoney(sub);
            updateMoney(new BigDecimal(inputMoney),bean);
        }
    }

    private void updateMoney(final BigDecimal sub, final LoginBean bean) {
        String uid = SPUtils.getInstance().getString(Constant.SPKey_UID);
        HashMap<String, String> map = new HashMap<>();
        map.put("money",String.valueOf(sub));
        map.put("uid",uid);
        HttpFactory.INSTANCE.getProtocol(IHttpProtocol.class)
                .updateUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverTC<String>(getMvpView()){

                    @Override
                    protected void onNextEx(@NonNull String data) {
                        Log.i("net","--------" + data);
                        SPUtils.getInstance().put(Constant.SPKey_USERINFO,GsonUtil.BeanToJson(bean));
                        getMvpView().onSuccess(sub);
                    }

                    @Override
                    protected void onErrorEx(@NonNull Throwable e) {
                        if (e.toString().contains("ConnectException")){
                            getMvpView().onError("请检查网络连接!");
                        }else {
                            getMvpView().onError(e.toString());
                        }
                    }

                    @Override
                    protected void onNextSN(String msg) {
                        super.onNextSN(msg);
                        getMvpView().onError(msg);
                    }
                });
    }
}
