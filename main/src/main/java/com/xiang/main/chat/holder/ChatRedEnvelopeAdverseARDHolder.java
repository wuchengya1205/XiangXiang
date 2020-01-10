package com.xiang.main.chat.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiang.main.R;

/**
 * author : fengzhangwei
 * date : 2019/12/27
 */
public class ChatRedEnvelopeAdverseARDHolder extends RecyclerView.ViewHolder {
    public TextView tv_content;

    public ChatRedEnvelopeAdverseARDHolder(@NonNull View itemView) {
        super(itemView);
        tv_content = itemView.findViewById(R.id.tv_content);
    }
}
