package com.lib.xiangxiang.im;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.xiang.lib.chatBean.ChatMessage;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * author : fengzhangwei
 * 操作Socket
 * date : 2019/12/19
 */
public class SocketManager {

    public interface SendMsgCallBack {
        void call(String msg);
    }

    private static sendMsgBRCallReceiver brCallReceiver;
    private static msgBRCallReceiver msgReceiver;
    private static HashMap<String, SendMsgCallBack> mCallBackMap = new HashMap<>();


    /**
     * 连接Socket
     *
     * @param context
     * @param token
     */
    public static void loginSocket(Context context, String token) {
        Context appContext = context.getApplicationContext();
        Intent intent = new Intent(appContext, ImService.class);
        intent.putExtra(ImService.SOCKET_CMD, ImService.SOCKET_INIT);
        intent.putExtra(ImService.SOCKET_DATA, token);
        startService(appContext, intent);

        // 注册发送消息广播
        if (brCallReceiver == null) {
            brCallReceiver = new sendMsgBRCallReceiver();
            IntentFilter filter = new IntentFilter(sendMsgBRCallReceiver.ACTION);
            appContext.registerReceiver(brCallReceiver, filter);
            mCallBackMap.clear();
        }
        // 注册接受消息广播
        if (msgReceiver == null) {
            msgReceiver = new msgBRCallReceiver();
            IntentFilter filter = new IntentFilter(msgBRCallReceiver.ACTION);
            appContext.registerReceiver(msgReceiver, filter);
        }
    }

    /**
     * 断开Socket
     */
    public static void logOutSocket(Context context){
        Context appContext = context.getApplicationContext();
        Intent intent = new Intent(appContext, ImService.class);
        intent.putExtra(ImService.SOCKET_CMD, ImService.SOCKET_RESET);
        startService(appContext, intent);
        // 注销广播
        if (brCallReceiver != null){
            appContext.unregisterReceiver(brCallReceiver);
            brCallReceiver = null;
            mCallBackMap.clear();
        }
        if (msgReceiver != null){
            appContext.unregisterReceiver(msgReceiver);
            msgReceiver = null;
        }
    }

    /**
     * 发送消息
     *
     * @param context
     * @param msg
     * @param back
     * @return
     */
    public static void sendMsgSocket(Context context, String msg, SendMsgCallBack back) {
        try {
            JSONObject object = new JSONObject(msg);
            String msg_id = object.getString(sendMsgBRCallReceiver.MSG_ID);
            Intent intent = new Intent(context, ImService.class);
            intent.putExtra(ImService.SOCKET_CMD, ImService.SOCKET_SEND_MSG);
            intent.putExtra(ImService.SOCKET_MSG, msg);
            intent.putExtra(ImService.SOCKET_PID, msg_id);
            startService(context, intent);
            mCallBackMap.put(msg_id, back);
        } catch (Exception o) {
            Log.i(ImSocketClient.TAG, "Socket ------  消息格式错误.");
        }
    }

    /**
     * 清空消息callback
     */
    public void clearCallBack() {
        if (mCallBackMap.size() != 0) {
            mCallBackMap.clear();
        }
    }

    private static void startService(Context appContext, Intent intent) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                appContext.startForegroundService(intent);
//            } else {
//                appContext.startService(intent);
//            }
        appContext.startService(intent);
    }


    /**
     * 发送消息的callback广播
     */
    static class sendMsgBRCallReceiver extends BroadcastReceiver {

        public static final String ACTION = BuildConfig.LIBRARY_PACKAGE_NAME + ".ACTION_CHAT_SEND_MSG_RECEIVER";
        public static final String MSG_ID = "pid";
        public static final String RESULT = "callResult";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != ACTION) {
                return;
            }
            String msg_id = intent.getStringExtra(MSG_ID);
            String result = intent.getStringExtra(RESULT);
            SendMsgCallBack callBack = mCallBackMap.get(msg_id);
            if (callBack != null) {
                callBack.call(result);
            }
            mCallBackMap.remove(msg_id);
        }
    }

    /**
     * 接受消息的广播
     */
    static class msgBRCallReceiver extends BroadcastReceiver {

        public static final String ACTION = BuildConfig.LIBRARY_PACKAGE_NAME + ".ACTION_CHAT_MSG_RECEIVER";
        public static final String ACTION_CONFLICT = BuildConfig.LIBRARY_PACKAGE_NAME + ".ACTION_CHAT_MSG_RESPONSE_CONFLICT";
        public static final String ACTION_CHAT = BuildConfig.LIBRARY_PACKAGE_NAME + ".ACTION_CHAT_MSG_RESPONSE_CHAT";
        public static final String RESULT = "callResult";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().isEmpty()) {
                return;
            }
            String result = intent.getStringExtra(RESULT);
            switch (intent.getAction()) {
                case ACTION:
                    receiverMsg(result);
                    break;
                default:
                    break;
            }
        }

        private void receiverMsg(String result) {
            if (result.isEmpty()) {
                return;
            }
            sendEvent(result);
        }

        private void sendEvent(String result) {
            ChatMessage chatMessage = GsonUtil.GsonToBean(result, ChatMessage.class);
            String id = SPUtils.getInstance().getString(Constant.SPKey_UID);
            if (!chatMessage.getFromId().equals(id)){
                if (chatMessage.getType() == ChatMessage.MSG_SEND_SYS){
                    EventBus.getDefault().postSticky( chatMessage.getBody());
                    Log.i(ImSocketClient.TAG,"------------" + chatMessage.getBody() + "------------");
                }else {
                    EventBus.getDefault().postSticky(chatMessage);
                }
            }else if (chatMessage.getFromId().equals(id)){
                if (chatMessage.getType() == ChatMessage.MSG_SEND_SYS){
                    if (chatMessage.getBodyType() == ChatMessage.MSG_BODY_TYPE_RED_ENVELOPE){
                        if (chatMessage.getMsgStatus() == ChatMessage.STATUS_OVERTIME){

                        }
                    }
                }
            }

        }
    }
}
