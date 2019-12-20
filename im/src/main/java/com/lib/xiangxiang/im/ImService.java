package com.lib.xiangxiang.im;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * author : fengzhangwei
 * date : 2019/12/19
 */
public class ImService extends Service {

    // 命令key
    public static final String SOCKET_CMD = "cmd";

    //  命令操作
    public static final int SOCKET_INIT = 0;   // 初始化,连接
    public static final int SOCKET_RESET = 1; //  断开,关闭所有监听
    public static final int SOCKET_SEND_MSG = 2; // 发送消息
    public static final int SOCKET_SEND_MSG_CALLBACK = 3; // 发送消息回调
    public static final int SOCKET_SEND_ASK = 4; // 发送已接受消息
    public static final int SOCKET_RECEIVER_MSG = 5; // 收到消息

    // 传递参数 key
    public static final String SOCKET_PID = "pid";
    public static final String SOCKET_DATA = "data";
    public static final String SOCKET_MSG = "msg";
    public static final String SOCKET_EVENT = "event";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            initChannel(this);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.chat);
            Notification.Builder builder = new Notification.Builder(this);
            builder.setChannelId(BuildConfig.APPLICATION_ID);
            builder.setContentTitle("xx_im_service");
            builder.setContentText("im_chat");
            builder.setSmallIcon(R.mipmap.chat);
            builder.setLargeIcon(bitmap);
            builder.build();
        }
    }

    private void initChannel(Context constant) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           long[] patten =  new long[3];
            patten[0] = 100;
            patten[1] = 200;
            patten[2] = 300;
            NotificationChannel channel = new NotificationChannel(BuildConfig.APPLICATION_ID, "xx_im_service", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.enableVibration(true);
            channel.setVibrationPattern(patten);
            NotificationManager systemService = (NotificationManager) constant.getSystemService(Context.NOTIFICATION_SERVICE);
            systemService.createNotificationChannel(channel);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int cmd = intent.getIntExtra(SOCKET_CMD, -1);
        switch (cmd) {
            case SOCKET_INIT:
                initSocket(intent);
                break;
            case SOCKET_RESET:

                break;
            case SOCKET_SEND_MSG:
                sendMsgSocket(intent);
                break;
            case SOCKET_SEND_MSG_CALLBACK:
                callMsg2UI(intent);
                break;
            case SOCKET_SEND_ASK:

                break;
            case SOCKET_RECEIVER_MSG:
                callChatMsg2UI(intent);
                break;
            default:

                break;
        }
        return START_STICKY;
    }

    private void callChatMsg2UI(Intent intent) {
        String result = intent.getStringExtra(SOCKET_MSG);
        Intent mIntent = new Intent();
        mIntent.setAction(SocketManager.msgBRCallReceiver.ACTION);
        mIntent.putExtra(SocketManager.msgBRCallReceiver.RESULT, result);
        sendBroadcast(mIntent);
    }

    private void callMsg2UI(Intent intent) {
        String msg_id = intent.getStringExtra(SOCKET_PID);
        String result = intent.getStringExtra(SOCKET_DATA);
        Intent mIntent = new Intent();
        mIntent.setAction(SocketManager.sendMsgBRCallReceiver.ACTION);
        mIntent.putExtra(SocketManager.sendMsgBRCallReceiver.MSG_ID, msg_id);
        mIntent.putExtra(SocketManager.sendMsgBRCallReceiver.RESULT, result);
        sendBroadcast(mIntent);
    }

    private void sendMsgSocket(Intent intent) {
        String msg = intent.getStringExtra(SOCKET_MSG);
        String msg_id = intent.getStringExtra(SOCKET_PID);
        ImSocketClient.sendMsg(this, msg, msg_id);
    }

    private void initSocket(Intent intent) {
        String token = intent.getStringExtra(SOCKET_DATA);
        ImSocketClient.initSocket(token, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
