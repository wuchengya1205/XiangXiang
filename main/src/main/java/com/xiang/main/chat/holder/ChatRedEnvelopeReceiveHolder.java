package com.xiang.main.chat.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shehuan.niv.NiceImageView;
import com.xiang.main.R;

/**
 * author : fengzhangwei
 * date : 2019/12/27
 */
public class ChatRedEnvelopeReceiveHolder extends RecyclerView.ViewHolder {

    public NiceImageView iv_icon;
    public TextView tv_time;
    public ImageView iv_red_envelope;

    public ChatRedEnvelopeReceiveHolder(@NonNull View itemView) {
        super(itemView);
        iv_icon = itemView.findViewById(R.id.iv_icon);
        tv_time = itemView.findViewById(R.id.tv_chat_time);
        iv_red_envelope = itemView.findViewById(R.id.iv_red_envelope);
    }
}
