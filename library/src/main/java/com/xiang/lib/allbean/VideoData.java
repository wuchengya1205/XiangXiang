package com.xiang.lib.allbean;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/11/5
 * time   :14:49
 * desc   :ohuo
 * version: 1.0
 */
public class VideoData {
    private String coverUrl;

    private String videoUrl;

    private int width;

    private int height;

    public VideoData(String coverUrl, String videoUrl, int width, int height) {
        this.coverUrl = coverUrl;
        this.videoUrl = videoUrl;
        this.width = width;
        this.height = height;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
