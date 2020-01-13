package com.xiang.main.chat.presenter;


import android.util.Log;

import androidx.annotation.NonNull;

import com.lib.xiangxiang.im.GsonUtil;
import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.BaseMvpPresenter;
import com.xiang.lib.base.BaseObserverTC;
import com.xiang.lib.chatBean.ChatMessage;
import com.xiang.lib.net.IHttpProtocol;
import com.xiang.lib.utils.ArithmeticUtils;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.SPUtils;
import com.xiang.main.chat.contract.ChatContract;

import net.ljb.kt.client.HttpFactory;

import java.math.BigDecimal;
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
    public void getHistory(String conversation, final int pageNo, int pageSize) {
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
                        getMvpView().onSuccessNull();

                    }

                    @Override
                    protected void onErrorEx(@NonNull Throwable e) {
                        super.onErrorEx(e);
                        getMvpView().onError("错误.");
                    }
                });
    }

    @Override
    public void updateEnvelope(String fromId,String toId,String pid) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("fromId",fromId);
        map.put("toId",toId);
        map.put("pid",pid);
        HttpFactory.INSTANCE.getProtocol(IHttpProtocol.class)
                .upRedEnvelope(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverTC<ChatMessage>(getMvpView()) {
                    @Override
                    protected void onNextEx(@NonNull ChatMessage data) {
                        super.onNextEx(data);
                        getMvpView().onSuccessRedEnvelope(data);
                    }

                    @Override
                    protected void onNextSN(String msg) {
                        super.onNextSN(msg);
                        getMvpView().onSuccessNull();

                    }

                    @Override
                    protected void onErrorEx(@NonNull Throwable e) {
                        super.onErrorEx(e);
                        getMvpView().onError("错误.");
                    }
                });
    }

    @Override
    public void updateMoney(String money) {
        String uid = SPUtils.getInstance().getString(Constant.SPKey_UID);
        String json = SPUtils.getInstance().getString(Constant.SPKey_USERINFO);
        final LoginBean bean = GsonUtil.GsonToBean(json, LoginBean.class);
        BigDecimal money2 = bean.getMoney();
        final BigDecimal sub = ArithmeticUtils.add(String.valueOf(money2),money);
        HashMap<String, String> map = new HashMap<>();
        map.put("uid",uid);
        map.put("money",String.valueOf(sub));
        HttpFactory.INSTANCE.getProtocol(IHttpProtocol.class)
                .updateUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverTC<String>(getMvpView()){

                    @Override
                    protected void onNextEx(@NonNull String data) {
                        Log.i("net","--------" + data);
                        bean.setMoney(sub);
                        SPUtils.getInstance().put(Constant.SPKey_USERINFO,GsonUtil.BeanToJson(bean));

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
