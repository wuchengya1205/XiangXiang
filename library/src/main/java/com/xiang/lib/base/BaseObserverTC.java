package com.xiang.lib.base;

import android.util.Log;

import androidx.annotation.NonNull;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import mvp.ljb.kt.contract.IViewContract;

public abstract class BaseObserverTC<T> implements Observer<BaseResponseTC<T>> {

    private IViewContract mView;

    @Override
    public final void onSubscribe( Disposable d) {

        Log.i("Net","--onSubscribe---" + d);
        onSubscribeEx(d);
    }

    public BaseObserverTC(IViewContract viewContract) {
        mView = viewContract;
    }

    @Override
    public final void onNext(@NonNull BaseResponseTC<T> data) {
        mView.dismissLoading();
        Log.i("Net","--onNext---");
        int code = data.getCode();
        if (code == 0){
            onNextSN(data.getMsg());
            return;
        }
        onNextEx(data.getData());
    }

    @Override
    public final void onError(@NonNull Throwable e) {
        mView.dismissLoading();
        Log.i("Net","--onError---" + e.toString());
        onErrorEx(e);
    }

    @Override
    public final void onComplete() {
        mView.dismissLoading();
        Log.i("Net","--onComplete---");
        onCompleteEx();
    }

    //Java实现下划线开头的方法
    protected void onCompleteEx() {

    }

    protected void onSubscribeEx(@NonNull Disposable d) {

    }

    protected void onNextEx(@NonNull T data) {

    }

    protected void onErrorEx(@NonNull Throwable e) {

    }

    protected void onNextSN(String msg){

    }

}
