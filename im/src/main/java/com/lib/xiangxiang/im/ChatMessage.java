package com.lib.xiangxiang.im;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : fengzhangwei
 * date : 2019/12/19
 */
public class ChatMessage  {


    // 消息类型
    public static int MSG_BODY_TYPE_TEXT = 1;   // 文本
    public static int MSG_BODY_TYPE_IMAGE = 2;  // 图片
    public static int MSG_BODY_TYPE_VOICE = 3;  // 语音
    public static int MSG_BODY_TYPE_VOIDE = 4;  // 视频
    public static int MSG_BODY_TYPE_LOCATION = 5;//位置

    // 消息状态
    public static int MSG_SEND_LOADING = 1;  // 正在发送
    public static int MSG_SEND_SUCCESS = 2;  // 成功
    public static int MSG_SEND_ERROR = 3;    // 失败

    private String fromId;
    private String toId;
    private String pid;
    private int bodyType;
    private String body;
    private int msgStatus;
    private Long time;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getBodyType() {
        return bodyType;
    }

    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        this.msgStatus = msgStatus;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
