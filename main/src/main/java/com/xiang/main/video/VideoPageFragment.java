package com.xiang.main.video;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiang.lib.allbean.MockData;
import com.xiang.lib.allbean.VideoData;
import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.main.R;
import com.xiang.main.video.adapter.VideoAdapter;
import com.xiang.main.video.adapter.VideoListAdapter;
import com.xiang.main.video.contract.VideoPageContract;
import com.xiang.main.video.listener.OnViewPagerListener;
import com.xiang.main.video.presenter.VideoPagePresenter;
import com.xiang.main.video.view.ListVideoView;
import com.xiang.main.video.view.ViewPagerLayoutManager;

import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;


/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/10/24
 * time   :14:57
 * desc   :ohuo
 * version: 1.0
 */
public class VideoPageFragment extends BaseMvpFragment<VideoPageContract.IPresenter> implements VideoPageContract.IView, View.OnClickListener, View.OnTouchListener, OnViewPagerListener {

    private RecyclerView video_list;
    private ImageView qiehuan;
    private boolean isRotation = false;
    private ObjectAnimator rotation;
    private RecyclerView video_list2;
    private List<VideoData> mockVideoData;
    private ViewPagerLayoutManager pagerLayoutManager;
    private VideoAdapter videoAdapter;
    private boolean isPause = false;
    private boolean isLove = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    public void initView() {
        super.initView();
        video_list = getActivity().findViewById(R.id.video_list);
        video_list2 = getActivity().findViewById(R.id.video_list2);
        qiehuan = getActivity().findViewById(R.id.qiehuan);
        qiehuan.setOnClickListener(this);

    }

    @Override
    public void initData() {
        super.initData();
        mockVideoData = MockData.getMockVideoData();
        setRecyclerView();
    }

    private void setRecyclerView() {
        video_list.setVisibility(View.VISIBLE);
        video_list.setLayoutManager(new LinearLayoutManager(getContext()));
        VideoListAdapter videoListAdapter = new VideoListAdapter(getActivity(), mockVideoData);
        video_list.setAdapter(videoListAdapter);
    }

    private void setRecyclerView2() {
        if (pagerLayoutManager == null){
            pagerLayoutManager = new ViewPagerLayoutManager(getContext(), LinearLayoutManager.VERTICAL);
        }
        pagerLayoutManager.setOnViewPagerListener(this);
        videoAdapter = new VideoAdapter(getActivity(), video_list2,mockVideoData);
        video_list2.setLayoutManager(pagerLayoutManager);
        video_list2.setAdapter(videoAdapter);
        video_list2.setOnTouchListener(this);
        videoAdapter.itemClickListener(new VideoAdapter.setOnItemClickListener() {
            @Override
            public void onItemClickListener(int position, VideoAdapter.VideoViewHolder holder) {
                if (isPause){
                    startVideo(position);
                    isPause = false;
                    holder.startorstop.setVisibility(View.GONE);
                }else {
                    pauseVideo(position);
                    isPause = true;
                    holder.startorstop.setVisibility(View.VISIBLE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDoubleClick(int position, VideoAdapter.VideoViewHolder holder) {
                    if (isLove){
                        holder.love.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.mipmap.heart_icon,null),null,null);
                        isLove = false;
                    } else {
                        holder.love.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.mipmap.icon_home_like_after,null),null,null);
                        isLove = true;
                    }
            }
        });
    }

    @Override
    public Class<? extends VideoPageContract.IPresenter> registerPresenter() {
        return VideoPagePresenter.class;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.qiehuan){
            xuanzhuan();
        }
    }

    private void xuanzhuan() {
        AnimatorSet animatorSet = new AnimatorSet();
        if (!isRotation){
            rotation = ObjectAnimator.ofFloat(qiehuan, "rotation", 0f,-90f);
            isRotation = true;
        }else {
            rotation = ObjectAnimator.ofFloat(qiehuan, "rotation", -90f,0f);
            isRotation = false;
        }
        animatorSet.play(rotation);
        animatorSet.setDuration(1000);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isRotation){
                    video_list.setVisibility(View.GONE);
                    video_list2.setVisibility(View.VISIBLE);
                    setRecyclerView2();
                    showToast("转过来");
                }else {
                    video_list.setVisibility(View.VISIBLE);
                    video_list2.setVisibility(View.GONE);
                    setRecyclerView();
                    showToast("转回去");
                }
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int action = motionEvent.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN://手指按下
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_MOVE://手指移动（从手指按下到抬起 move多次执行）
                break;
            case MotionEvent.ACTION_UP://手指抬起
                if (video_list2.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING &&
                        pagerLayoutManager.findSnapPosition() == 0) {
                    if (video_list2.getChildAt(0).getY() == 0 &&
                            video_list2.canScrollVertically(1)) {//下滑操作
                        video_list2.stopScroll();
                    }
    }
                break;
    default:
                break;
        }
        return false;
    }

    @Override
    public void onInitComplete() {
        playVideo(0);
        yingcang(0);
    }

    @Override
    public void onPageSelected(int position, boolean isBottom) {
        playVideo(position);
        yingcang(position);
    }

    @Override
    public void onPageRelease(boolean isNext, int position) {
        releaseVideo(position);
        yingcang(position);
    }
    //开始播放
    private void playVideo(int position) {
        final VideoAdapter.VideoViewHolder viewHolder = (VideoAdapter.VideoViewHolder) video_list2.findViewHolderForLayoutPosition(position);
        VideoData videoData = videoAdapter.getDataByPosition(position);
        if (viewHolder != null && !viewHolder.videoView.isPlaying()) {
            viewHolder.videoView.setVideoPath(videoData.getVideoUrl());
            viewHolder.videoView.getMediaPlayer().setOnInfoListener(new IMediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
                    if (what == IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                        viewHolder.sdvCover.setVisibility(View.INVISIBLE);
                    }
                    return false;
                }
            });
            viewHolder.videoView.setOnVideoProgressUpdateListener(new ListVideoView.OnVideoProgressListener() {
                @Override
                public void onProgress(float progress, long currentTime) {
                    Log.d("youzai", "progresss---->" + progress + "\t" + "currentTime---->" + currentTime);
                }
            });
            viewHolder.videoView.setLooping(true);
            viewHolder.videoView.prepareAsync();
        }
    }
    private void yingcang(int position){
        VideoAdapter.VideoViewHolder viewHolder = (VideoAdapter.VideoViewHolder) video_list2.findViewHolderForLayoutPosition(position);
        viewHolder.startorstop.setVisibility(View.GONE);
    }

    //暂停播放
    private  void  pauseVideo(int position){
        final VideoAdapter.VideoViewHolder viewHolder = (VideoAdapter.VideoViewHolder) video_list2.findViewHolderForLayoutPosition(position);
        if (viewHolder != null && viewHolder.videoView.isPlaying()) {
            viewHolder.videoView.pause();
        }
    }
    //继续播放
    private void startVideo(int position){
        final VideoAdapter.VideoViewHolder viewHolder = (VideoAdapter.VideoViewHolder) video_list2.findViewHolderForLayoutPosition(position);
        if (viewHolder != null && !viewHolder.videoView.isPlaying()) {
            viewHolder.videoView.start();
        }
    }

    //释放视频资源
    private void releaseVideo(int position) {
        VideoAdapter.VideoViewHolder viewHolder = (VideoAdapter.VideoViewHolder) video_list2.findViewHolderForLayoutPosition(position);
        if (viewHolder != null) {
            viewHolder.videoView.stopPlayback();
            viewHolder.sdvCover.setVisibility(View.VISIBLE);
        }
    }
}
