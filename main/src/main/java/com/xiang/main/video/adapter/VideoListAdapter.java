package com.xiang.main.video.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xiang.lib.allbean.VideoData;
import com.xiang.main.R;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;


/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/10/28
 * time   :18:05
 * desc   :ohuo
 * version: 1.0
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoHolder> {
    private Context context;
    private List<VideoData> mockVideoData;

    public VideoListAdapter(Context context, List<VideoData> mockVideoData) {
        this.context = context;
        this.mockVideoData = mockVideoData;
    }

    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(context).inflate(R.layout.video_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
            holder.jzplayer.setUp(mockVideoData.get(position).getVideoUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            Glide.with(context).load(mockVideoData.get(position).getCoverUrl()).into(holder.jzplayer.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return mockVideoData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class VideoHolder extends RecyclerView.ViewHolder {

        public JZVideoPlayerStandard jzplayer;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            jzplayer = itemView.findViewById(R.id.jzplayer);
        }
    }
}
