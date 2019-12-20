package com.lib.xiangxiang.im;

import android.content.Context;
import android.util.Log;

import com.xiang.lib.utils.Constant;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * author : fengzhangwei
 * date : 2019/12/19
 */
public class ImSocketClient {

    private static String SocketEvent = "chat";
    private static Socket mSocket;
    private static String mToken;
    private static String mSocketUrl = Constant.BASE_CHAT_URL;
    public  static String TAG = "socket";
    private static Boolean isConnect = false;
    private static Emitter.Listener mDisConnectListener;  // 断开连接监听
    private static Emitter.Listener mConnectListener;     // 连接监听 / 成功
    private static Emitter.Listener mConnectErrorListener;// 连接错误
    private static Emitter.Listener mChatListener;        // 消息监听

    /**
     * 判断Socket是否可以复用
     *
     * @param token
     * @param context
     * @return
     */
    public static void initSocket(String token, Context context) {
        if (mSocket != null) {
            if (mToken != token) {
                newSocket(context, token);
            } else {
                Log.i(TAG, "-----复用Socket----");
            }
        } else {
            newSocket(context, token);
        }
    }

    /**
     * 断开Socket
     *
     * @return
     */
    public static void release() {
        if (mSocket != null) {
            closeSocket();
            removeSocketListener();
            mSocket = null;
            Log.i(TAG, "Socket --- 已释放.");
        }
    }

    /**
     * 清空监听
     */
    private static void removeSocketListener() {
        if (mSocket != null) {
            mSocket.off(SocketEvent, mChatListener);
            mSocket.off(Socket.EVENT_CONNECT, mConnectListener);
            mSocket.off(Socket.EVENT_DISCONNECT, mDisConnectListener);
            mSocket.off(Socket.EVENT_CONNECT_ERROR, mConnectErrorListener);
        }
    }

    /**
     * 关闭Socket
     */
    private static void closeSocket() {
        if (mSocket != null) {
            mSocket.disconnect();
            Log.i(TAG, "Socket --- 链接已关闭.");
        }
    }

    /**
     * 发送消息
     *
     * @return
     */
    public static void sendMsg(Context context, String msg, String msgId) {
        sendMsgSocket(context, msg, msgId);
    }

    /**
     * Socket 发送消息
     *
     * @param context
     * @param msg
     * @param msgId
     */
    private static void sendMsgSocket(Context context, String msg, String msgId) {
        if (checkSocket()) {
            if (mSocket != null) {
                Log.i(TAG, "Socket send msg -------- " + msg);
                mSocket.emit(SocketEvent, msg, new SocketSendMsgCallBackAck(context, msgId));
            }
        } else {
            Log.i(TAG, "Socket is null");
        }
    }

    /**
     * 创建Socket
     *
     * @param context
     * @param token
     */
    private static void newSocket(Context context, String token) {
        mToken = token;
        IO.Options options = new IO.Options();
        options.forceNew = false;
        options.reconnection = true;
        options.reconnectionDelay = 3000;
        options.reconnectionDelayMax = 5000;
        options.timeout = -1;
        options.query = token;
        if (!mSocketUrl.isEmpty()) {
            try {
                mSocket = IO.socket(mSocketUrl, options);
                Log.i(TAG, "socket 创建-----" + token);
                initSocketListener(context);
                openSocket();
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "socket 创建失败-----" + e.toString());
            }
        } else {
            Log.i(TAG, "socket 创建失败-----url Null");
        }

    }

    private static void openSocket() {
        if (mSocket != null) {
            mSocket.connect();
        }
    }

    /**
     * 创建监听
     *
     * @param context
     */
    private static void initSocketListener(final Context context) {

        mChatListener = new SocketChatMsgListener(context);

        mDisConnectListener = new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                isConnect = false;
                Log.i(TAG, "socket ---断开连接---");
            }
        };

        mConnectListener = new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                isConnect = true;
                Log.i(TAG, "socket ---连接成功---" + checkSocket());
            }
        };

        mConnectErrorListener = new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                isConnect = false;
                if (args.toString().isEmpty()) {
                    Log.i(TAG, "socket ---连接错误---" + args.toString());
                } else {
                    Log.i(TAG, "socket ---连接错误---" + args[0].toString());
                }

            }
        };
        addSocketListener();
    }

    /**
     * Socket添加监听
     */
    private static void addSocketListener() {
        if (mSocket != null) {
            mSocket.on(SocketEvent, mChatListener);
            mSocket.on(Socket.EVENT_CONNECT, mConnectListener);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, mConnectErrorListener);
            mSocket.on(Socket.EVENT_DISCONNECT, mDisConnectListener);
        }
    }

    /**
     * 判断Socket是否可用
     *
     * @return
     */
    private static Boolean checkSocket() {
        return mSocket != null && isConnect;
    }


}
