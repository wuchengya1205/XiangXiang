package com.xiang.main.chat.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiang.main.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author : fengzhangwei
 * date : 2019/12/27
 */
public class ChatEmojiSendHolder extends RecyclerView.ViewHolder {
    public CircleImageView iv_icon;
    public TextView tv_time;
    public ImageView iv_emoji;
    public ProgressBar pb_state;

    public ChatEmojiSendHolder(@NonNull View itemView) {
        super(itemView);
        iv_icon = itemView.findViewById(R.id.iv_icon);
        tv_time = itemView.findViewById(R.id.tv_chat_time);
        iv_emoji = itemView.findViewById(R.id.iv_emoji);
        pb_state = itemView.findViewById(R.id.pb_state);
    }
}
