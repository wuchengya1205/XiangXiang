package com.xiang.main.chat.holder;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shehuan.niv.NiceImageView;
import com.xiang.main.R;

/**
 * author : fengzhangwei
 * date : 2019/12/27
 */
public class ChatTextSendHolder extends RecyclerView.ViewHolder {
    public NiceImageView iv_icon;
    public TextView tv_time;
    public TextView tv_content;
    public ProgressBar pb_state;

    public ChatTextSendHolder(@NonNull View itemView) {
        super(itemView);
        iv_icon = itemView.findViewById(R.id.iv_icon);
        tv_time = itemView.findViewById(R.id.tv_chat_time);
        tv_content = itemView.findViewById(R.id.tv_content);
        pb_state = itemView.findViewById(R.id.pb_state);
    }
}
