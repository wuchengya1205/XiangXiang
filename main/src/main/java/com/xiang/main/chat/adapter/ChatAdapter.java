package com.xiang.main.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lib.xiangxiang.im.GsonUtil;
import com.xiang.lib.chatBean.ChatMessage;
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

    private ChatItem mClickListener;

    public interface ChatItem{
        void onClickIcon(String url);
        void onLongClickSend(View view);
        void onLongClickReceive(View view);
    }


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
        setTime(holder,mList,position);
        setIcon(holder);
        setContext(holder,message);
        setItemState(holder,message);
    }

    private void setItemState(RecyclerView.ViewHolder holder, ChatMessage message) {
        int msgStatus = message.getMsgStatus();
        if (holder instanceof ChatTextSendHolder){
            if (msgStatus == ChatMessage.MSG_SEND_SUCCESS){
                ((ChatTextSendHolder) holder).pb_state.setVisibility(View.GONE);
            }else if (msgStatus == ChatMessage.MSG_SEND_LOADING){
                ((ChatTextSendHolder) holder).pb_state.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setContext(final RecyclerView.ViewHolder holder, ChatMessage message) {
        String body = message.getBody();
        TextBody body1 = GsonUtil.GsonToBean(body, TextBody.class);
        if (holder instanceof ChatTextSendHolder) {
            ((ChatTextSendHolder) holder).tv_content.setText(body1.getMsg());
            ((ChatTextSendHolder) holder).tv_content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mClickListener != null){
                        mClickListener.onLongClickSend(((ChatTextSendHolder) holder).tv_content);
                        return true;
                    }
                    return false;
                }
            });
        }
        if (holder instanceof ChatTextReceiveHolder) {
            ((ChatTextReceiveHolder) holder).tv_content.setText(body1.getMsg());
            ((ChatTextReceiveHolder) holder).tv_content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mClickListener != null){
                        mClickListener.onLongClickReceive(((ChatTextReceiveHolder) holder).tv_content);
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private void setIcon(RecyclerView.ViewHolder holder) {
        if (holder instanceof ChatTextSendHolder) {
            Glide.with(mContext).load(mFromUrl).into(((ChatTextSendHolder) holder).iv_icon);
            ((ChatTextSendHolder)holder).iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null){
                        mClickListener.onClickIcon(mFromUrl);
                    }
                }
            });
        }
        if (holder instanceof ChatTextReceiveHolder) {
            Glide.with(mContext).load(mToUrl).into(((ChatTextReceiveHolder) holder).iv_icon);
            ((ChatTextReceiveHolder)holder).iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null){
                        mClickListener.onClickIcon(mFromUrl);
                    }
                }
            });
        }
    }

    private void setTime(RecyclerView.ViewHolder holder,List<ChatMessage> list,int position) {
        ChatMessage message = list.get(position);
        Long time = message.getTime();
        String chatTime = TimeUtil.getTimeString(time);
        int displayTime = message.getDisplaytime();
        if (displayTime == ChatMessage.MSG_TIME_FALSE){
            if (holder instanceof ChatTextSendHolder) {
                ((ChatTextSendHolder) holder).tv_time.setVisibility(View.GONE);
            }
            if (holder instanceof ChatTextReceiveHolder) {
                ((ChatTextReceiveHolder) holder).tv_time.setVisibility(View.GONE);
            }
        }else {
            if (holder instanceof ChatTextSendHolder) {
                ((ChatTextSendHolder) holder).tv_time.setVisibility(View.VISIBLE);
                ((ChatTextSendHolder) holder).tv_time.setText(chatTime);
            }
            if (holder instanceof ChatTextReceiveHolder) {
                ((ChatTextReceiveHolder) holder).tv_time.setVisibility(View.VISIBLE);
                ((ChatTextReceiveHolder) holder).tv_time.setText(chatTime);
            }
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
    }

    public void setData(List<ChatMessage> message){
        mList.addAll(0,message);
        notifyDataSetChanged();
    }

    public List<ChatMessage> getData(){
        return mList;
    }

    public int getLastItemDisplayTime(){
        int position = 0;
        if (mList.size() > 0){
            for (int i=mList.size()-1;i >= 0;i--){
                ChatMessage message = mList.get(i);
                int displayTime = message.getDisplaytime();
                if (displayTime == ChatMessage.MSG_TIME_TRUE){
                    position = i;
                    break;
                }
            }
            ChatMessage message = mList.get(position);
            Long time = message.getTime();
            Boolean expend = TimeUtil.getTimeExpend(System.currentTimeMillis(), time);
            if (expend){
                return ChatMessage.MSG_TIME_FALSE;
            }else {
                return ChatMessage.MSG_TIME_TRUE;
            }
        }else {
            return ChatMessage.MSG_TIME_TRUE;
        }
    }

    public void notifyChatMessage(ChatMessage message){
        if (mList.size() > 0){
            String pid = message.getPid();
            int status = message.getMsgStatus();
            for (int i=mList.size()-1;i>= 0;i--){
                String pid1 = mList.get(i).getPid();
                if (pid1.equals(pid)){
                    mList.get(i).setMsgStatus(status);
                    notifyItemChanged(i);
                    return;
                }
            }
        }
    }

    public void setOnItemListener(ChatItem itemClickListener){
        this.mClickListener = itemClickListener;
    }
}
