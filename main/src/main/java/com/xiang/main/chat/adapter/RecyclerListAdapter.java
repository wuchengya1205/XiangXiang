package com.xiang.main.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xiang.lib.allbean.AttnBean;
import com.xiang.lib.allbean.CommonBean;
import com.xiang.lib.utils.NetState;
import com.xiang.main.R;

import java.util.List;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/10/22
 * time   :16:23
 * desc   :ohuo
 * version: 1.0
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.RecyclerListHolder> {
    private Context context;
    private List<AttnBean> data;
    private setOnItemClickListener setOnItemClickListener;

    public RecyclerListAdapter(Context context, List<AttnBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerListHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_attn_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerListHolder holder, final int position) {
        final AttnBean bean = data.get(position);
        int online = bean.getOnline();
        holder.name.setText(bean.getUsername());
        holder.content.setText(NetState.getNetState(online) +" "+bean.getSign());
        Glide.with(context).load(bean.getImageUrl()).apply(new RequestOptions().placeholder(R.mipmap.icon_user_man).error(R.mipmap.icon_user_man).centerCrop()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setOnItemClickListener != null){
                    setOnItemClickListener.onItemClickListener(bean.getUid());
                }
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerListHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public  TextView content,date;
        public  ImageView image;

        public RecyclerListHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.civ_attn_icon);
            name = itemView.findViewById(R.id.tv_attn_name);
            content = itemView.findViewById(R.id.tv_net_sign);
        }
    }
    public void setOnItem(setOnItemClickListener setOnItemClickListener){
        this.setOnItemClickListener = setOnItemClickListener;

    }

    public void setData(List<AttnBean> data){
        if (data == null) return;
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<AttnBean> getData(){
        return data;
    }

    public void clearData(){
        if (data != null){
            data.clear();
            notifyDataSetChanged();
        }
    }

    public interface  setOnItemClickListener{
        void onItemClickListener(String uid);
    }
}
