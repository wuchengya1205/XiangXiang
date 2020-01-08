package com.xiang.main.chat.presenter;


import android.util.Log;

import androidx.annotation.NonNull;

import com.xiang.lib.allbean.AttnBean;
import com.xiang.lib.base.BaseMvpPresenter;
import com.xiang.lib.base.BaseObserverTC;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.net.IHttpProtocol;
import com.xiang.lib.utils.SPUtils;
import com.xiang.main.chat.contract.ChatAttnContract;

import net.ljb.kt.client.HttpFactory;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/10/24
 * time   :15:07
 * desc   :ohuo
 * version: 1.0
 */
public class ChatAttnPresenter extends BaseMvpPresenter<ChatAttnContract.IView> implements ChatAttnContract.IPresenter {

    @Override
    public void getAllAttn() {
        HashMap<String,String> map = new HashMap<>();
        map.put("uid",SPUtils.getInstance().getString(Constant.SPKey_UID));
        HttpFactory.INSTANCE.getProtocol(IHttpProtocol.class)
                .getAllAttn(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverTC<List<AttnBean>>(getMvpView()){

                    @Override
                    protected void onNextEx(@NonNull List<AttnBean> data) {
                        Log.i("net","--------" + data.toString());
                        getMvpView().onSuccess(data);
                    }

                    @Override
                    protected void onErrorEx(@NonNull Throwable e) {

                    }

                    @Override
                    protected void onNextSN(String msg) {
                        super.onNextSN(msg);

                    }
                });
    }
}
