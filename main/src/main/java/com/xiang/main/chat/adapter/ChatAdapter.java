package com.xiang.main.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lib.xiangxiang.im.ChatMessage;
import com.lib.xiangxiang.im.GsonUtil;
import com.xiang.lib.chatBean.TextBody;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.SPUtils;
import com.xiang.lib.utils.TimeUtil;
import com.xiang.main.R;
import com.xiang.main.chat.holder.ChatTextReceiveHolder;
import com.xiang.main.chat.holder.ChatTextSendHolder;

import java.util.List;

/**
 * author : fengzhangwei
 * date : 2019/12/27
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int TYPE_SEND_TEXT = 1;
    private final int TYPE_RECEIVE_TEXT = 2;


    private String mFromId = SPUtils.getInstance().getString(Constant.SPKey_UID);
    private List<ChatMessage> mList;
    private Context mContext;
    private String mFromUrl;
    private String mToUrl;

    public ChatAdapter(List<ChatMessage> mList, Context mContext, String from_url, String to_url) {
        this.mList = mList;
        this.mContext = mContext;
        this.mFromUrl = from_url;
        this.mToUrl = to_url;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        switch (viewType) {
            case TYPE_SEND_TEXT:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_send_text,parent,false);
                viewHolder = new ChatTextSendHolder(view);
                break;
            case TYPE_RECEIVE_TEXT:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_send_receive,parent,false);
                viewHolder = new ChatTextReceiveHolder(view);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = mList.get(position);
        setTime(holder, message);
        setIcon(holder);
        setContext(holder,message);
    }

    private void setContext(RecyclerView.ViewHolder holder, ChatMessage message) {
        String body = message.getBody();
        TextBody body1 = GsonUtil.GsonToBean(body, TextBody.class);
        if (holder instanceof ChatTextSendHolder) {
            ((ChatTextSendHolder) holder).tv_content.setText(body1.getMsg());
        }
        if (holder instanceof ChatTextReceiveHolder) {
            ((ChatTextReceiveHolder) holder).tv_content.setText(body1.getMsg());
        }
    }

    private void setIcon(RecyclerView.ViewHolder holder) {
        if (holder instanceof ChatTextSendHolder) {
            Glide.with(mContext).load(mFromUrl).into(((ChatTextSendHolder) holder).iv_icon);
        }
        if (holder instanceof ChatTextReceiveHolder) {
            Glide.with(mContext).load(mToUrl).into(((ChatTextReceiveHolder) holder).iv_icon);
        }
    }

    private void setTime(RecyclerView.ViewHolder holder, ChatMessage message) {

        Long time = message.getTime();
        String chatTime = TimeUtil.getChatTime(TimeUtil.getYearOfDate(time), time);
        if (holder instanceof ChatTextSendHolder) {
            ((ChatTextSendHolder) holder).tv_time.setText(chatTime);
        }
        if (holder instanceof ChatTextReceiveHolder) {
            ((ChatTextReceiveHolder) holder).tv_time.setText(chatTime);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = mList.get(position);
        String fromId = message.getFromId();
        int bodyType = message.getBodyType();
        if (fromId.equals(mFromId)) {
            if (bodyType == ChatMessage.MSG_BODY_TYPE_TEXT) {
                return TYPE_SEND_TEXT;
            }
        } else {
            if (bodyType == ChatMessage.MSG_BODY_TYPE_TEXT) {
                return TYPE_RECEIVE_TEXT;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(ChatMessage message){
        mList.add(message);
        notifyDataSetChanged();
    }
}
