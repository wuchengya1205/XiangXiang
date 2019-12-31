package com.xiang.main.chat.presenter;


import android.util.Log;

import androidx.annotation.NonNull;

import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.BaseMvpPresenter;
import com.xiang.lib.base.BaseObserverTC;
import com.xiang.lib.chatBean.ChatMessage;
import com.xiang.lib.net.IHttpProtocol;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.SPUtils;
import com.xiang.main.chat.contract.ChatContract;

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
public class ChatPresenter extends BaseMvpPresenter<ChatContract.IView> implements ChatContract.IPresenter {


    @Override
    public void getUserInfo(String uid) {
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
                        getMvpView().onSuccessInfo(data);
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

    @Override
    public void getHistory(String conversation,int pageNo,int pageSize) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("uid", SPUtils.getInstance().getString(Constant.SPKey_UID));
        map.put("conversation",conversation);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        HttpFactory.INSTANCE.getProtocol(IHttpProtocol.class)
                .getHistory(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverTC<List<ChatMessage>>(getMvpView()) {
                    @Override
                    protected void onNextEx(@NonNull List<ChatMessage> data) {
                        super.onNextEx(data);
                        getMvpView().onSuccessHistory(data);
                    }

                    @Override
                    protected void onNextSN(String msg) {
                        super.onNextSN(msg);
                    }

                    @Override
                    protected void onErrorEx(@NonNull Throwable e) {
                        super.onErrorEx(e);
                    }
                });
    }
}
