package com.xiang.lib.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.xiang.lib.utils.NetState;


public class NetBroadcastReceiver extends BroadcastReceiver {

    public static NetChangeListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("NetBroadcastReceiver", "NetBroadcastReceiver changed");
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Boolean netWorkState = NetState.hasNetWorkConnection(context);
            Log.i("Net","---当前是否有网络---NetBroadcastReceiver:::::::" + netWorkState);
            // 当网络发生变化，判断当前网络状态，并通过NetEvent回调当前网络状态
            final int workStatus = NetState.getNetWorkStatus(context);
            if (listener != null) {
                listener.onChangeListener(netWorkState);
            }
            if (netWorkState){
                if (listener != null) {
                    listener.onNetWorkState(workStatus);
                }
            }

        }
    }



    public static void setRegisterNetBRChange(NetChangeListener e){
        listener = e;
    }
}
