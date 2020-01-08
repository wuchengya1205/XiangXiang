package com.xiang.main.chat.presenter;


import android.util.Log;

import androidx.annotation.NonNull;

import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.BaseMvpPresenter;
import com.xiang.lib.base.BaseObserverTC;
import com.xiang.lib.net.IHttpProtocol;
import com.xiang.main.chat.contract.AttnInfoContract;

import net.ljb.kt.client.HttpFactory;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */
public class AttnInfoPresenter extends BaseMvpPresenter<AttnInfoContract.IView> implements AttnInfoContract.IPresenter {


    @Override
    public void getInfo(String uid) {
        HashMap<String,String> map = new HashMap<>();
        map.put("uid",uid);
        HttpFactory.INSTANCE.getProtocol(IHttpProtocol.class)
                .getUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverTC<LoginBean>(getMvpView()){

                    @Override
                    protected void onNextEx(@NonNull LoginBean data) {
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
