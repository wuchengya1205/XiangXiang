package com.xiang.main.presenter;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.BaseMvpPresenter;
import com.xiang.lib.base.BaseObserverTC;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.IHttpProtocol;
import com.xiang.lib.utils.SPUtils;
import com.xiang.main.contract.MainFContract;
import com.xiang.main.contract.UserInfoContract;

import net.ljb.kt.client.HttpFactory;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : fengzhangwei
 * date : 2019/11/12
 */
public class UserInfoPresenter extends BaseMvpPresenter<UserInfoContract.IView> implements UserInfoContract.IPresenter{


    @Override
    public void changeUserInfo(final LoginBean bean) {
        String uid = SPUtils.getInstance().getString(Constant.SPKey_UID);
        HashMap<String, String> map = new HashMap<>();
        String iconUrl = getMvpView().getIconUrl();
        String name = getMvpView().getName();
        String location = getMvpView().getLocation();
        String sign = getMvpView().getSign();
        String email = getMvpView().getEmail();
        if (!iconUrl.isEmpty()){
            if (!iconUrl.equals(bean.getImageUrl())){
                map.put("imageUrl",iconUrl);
                bean.setImageUrl(iconUrl);
            }
        }
        if (!name.equals(bean.getUsername())){
            map.put("username",name);
            bean.setUsername(name);
        }
        if (!location.equals(bean.getLocation())){
            map.put("location",location);
            bean.setLocation(location);
        }
        if (!sign.equals(bean.getSign())){
            map.put("sign",sign);
            bean.setSign(sign);
        }
        if (!email.equals(bean.getEmail())){
            map.put("email",email);
            bean.setEmail(email);
        }
        map.put("uid",uid);
        if (map.size() <= 1){
            getMvpView().dismissLoading();
            getMvpView().onError("未修改信息");
            return;
        }
        HttpFactory.INSTANCE.getProtocol(IHttpProtocol.class)
                .updateUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverTC<String>(getMvpView()){

                    @Override
                    protected void onNextEx(@NonNull String data) {
                        Log.i("net","--------" + data);
                        SPUtils.getInstance().put(Constant.SPKey_USERINFO,new Gson().toJson(bean));
                        getMvpView().onSuccess();
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
