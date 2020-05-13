package com.lib.loginandregister.presenter;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.lib.loginandregister.contract.LoginContract;
import com.lib.loginandregister.contract.RegisterContract;
import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.BaseMvpPresenter;
import com.xiang.lib.base.BaseObserverTC;
import com.xiang.lib.base.BaseResponseTC;
import com.xiang.lib.net.IHttpProtocol;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.SPUtils;

import net.ljb.kt.client.HttpFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RegisterPresenter extends BaseMvpPresenter<RegisterContract.IView> implements RegisterContract.IPresenter {

    private List<String> list;

    @Override
    public void register() {
        String name = getMvpView().getName();
        String email = getMvpView().getEmail();
        String location = getMvpView().getLocation();
        String mobile = getMvpView().getMobile();
        String password = getMvpView().getPassword();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("location", location);
        map.put("imageUrl",getImageUrl());
        map.put("sex","男");
        map.put("mobile", mobile);
        map.put("password", password);
        HttpFactory.INSTANCE.getProtocol(IHttpProtocol.class)
                .register(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverTC<Object>(getMvpView()){

                    @Override
                    protected void onNextEx(@NonNull Object data) {
                        Log.i("net","--------" + data);
                        getMvpView().onSuccess();
                    }

                    @Override
                    protected void onErrorEx(@NonNull Throwable e) {
                        getMvpView().onError(e.toString());
                    }

                    @Override
                    protected void onNextSN(String msg) {
                        super.onNextSN(msg);
                        getMvpView().onError(msg);
                    }
                });
    }

    private String getImageUrl(){
        list = new ArrayList<>();
        list.add("https://ss0.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2611652800,2596506430&fm=26&gp=0.jpg");
        list.add("https://b-ssl.duitang.com/uploads/item/201707/06/20170706164810_kiCre.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201807/06/20180706112250_3iBxt.thumb.700_0.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201807/06/20180706112251_niE3Y.thumb.700_0.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201804/29/20180429111927_4i2Ks.thumb.700_0.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201709/10/20170910110429_5J8jt.jpeg");
        list.add("http://img3.imgtn.bdimg.com/it/u=466636099,2440212896&fm=11&gp=0.jpg");
        list.add("https://b-ssl.duitang.com/uploads/item/201808/03/20180803090324_qrygh.thumb.700_0.jpeg");
        return list.get((int)(0+Math.random()*(list.size()-0+1)));
    }
}
