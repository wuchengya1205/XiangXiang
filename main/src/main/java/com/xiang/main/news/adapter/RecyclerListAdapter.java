package com.xiang.main.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.xiang.lib.allbean.CommonBean;
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
    private List<CommonBean.DataBean> data;
    private setOnItemClickListener setOnItemClickListener;

    public RecyclerListAdapter(Context context, List<CommonBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerListHolder(LayoutInflater.from(context).inflate(R.layout.recycler_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerListHolder holder, final int position) {
        final CommonBean.DataBean dataBean = this.data.get(position);
        holder.title.setText(dataBean.getTitle());
        holder.content.setText(dataBean.getAuthor_name());
        holder.date.setText(dataBean.getDate());
        if (dataBean.getThumbnail_pic_s() != null){
            holder.image1.setVisibility(View.VISIBLE);
            Glide.with(context).load(dataBean.getThumbnail_pic_s()).into(holder.image1);
        }
        if (dataBean.getThumbnail_pic_s02() != null){
            holder.image2.setVisibility(View.VISIBLE);
            Glide.with(context).load(dataBean.getThumbnail_pic_s()).into(holder.image2);
        }else {
            holder.image2.setVisibility(View.GONE);
        }
        if (dataBean.getThumbnail_pic_s03() != null){
            holder.image3.setVisibility(View.VISIBLE);
            Glide.with(context).load(dataBean.getThumbnail_pic_s()).into(holder.image3);
        }else {
            holder.image3.setVisibility(View.GONE);
        }
        holder.image1.setScaleType(ImageView.ScaleType.CENTER);
        holder.image2.setScaleType(ImageView.ScaleType.CENTER);
        holder.image3.setScaleType(ImageView.ScaleType.CENTER);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setOnItemClickListener != null){
                    setOnItemClickListener.onItemClickListener(position,holder, dataBean.getUrl());
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

        public TextView title;
        public  TextView content,date;
        public  ImageView image1,image2,image3;

        public RecyclerListHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.tv_author);
            date = itemView.findViewById(R.id.tv_date);
            image1 = itemView.findViewById(R.id.image1);
            image2 = itemView.findViewById(R.id.image2);
            image3 = itemView.findViewById(R.id.image3);
        }
    }
    public void setOnItem(setOnItemClickListener setOnItemClickListener){
        this.setOnItemClickListener = setOnItemClickListener;

    }

    public void setData(List<CommonBean.DataBean> data){
        if (data == null) return;
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<CommonBean.DataBean> getData(){
        return data;
    }

    public interface  setOnItemClickListener{
        void onItemClickListener(int position, RecyclerListHolder holder, String url);
    }
}
