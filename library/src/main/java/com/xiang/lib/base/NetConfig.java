package com.xiang.lib.base;

import android.content.Context;
import android.content.IntentFilter;

/**
 * author : fengzhangwei
 * date : 2019/9/12
 */
public class NetConfig {


    public static void init(Context context) {
        //实例化IntentFilter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        NetBroadcastReceiver netBroadcastReceiver = new NetBroadcastReceiver();
        //注册广播接收
        context.registerReceiver(netBroadcastReceiver, filter);
    }


}
