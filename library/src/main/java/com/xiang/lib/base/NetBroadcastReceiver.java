package com.xiang.lib.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class NetBroadcastReceiver extends BroadcastReceiver {

    public static NetChangeListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("NetBroadcastReceiver", "NetBroadcastReceiver changed");
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Boolean netWorkState = isNetworkConnected(context);
            Log.i("Net","---当前是否有网络---NetBroadcastReceiver:::::::" + netWorkState);
            // 当网络发生变化，判断当前网络状态，并通过NetEvent回调当前网络状态
            if (listener != null) {
                listener.onChangeListener(netWorkState);
            }
        }
    }


    /**
     * 是否有网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void setRegisterNetBRChange(NetChangeListener e){
        listener = e;
    }
}
