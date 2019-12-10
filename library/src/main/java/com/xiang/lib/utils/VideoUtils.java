package com.xiang.lib.utils;

import android.util.Pair;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/11/5
 * time   :15:13
 * desc   :ohuo
 * version: 1.0
 */
public class VideoUtils {
    private static final float RATIO_FIT_CENTER = 1.6f;

    public enum ScaleType {
        /**
         * 对应于ImageView FIT_CENTER
         */
        FIT_CENTER,
        /**
         * 对应于ImageView CENTER_CROP
         */
        CENTER_CROP
    }

    public static ScaleType getImageCropType(Pair<Integer, Integer> layoutSize,
                                             Pair<Integer, Integer> videoSize) {
        int layoutWidth = layoutSize.first;
        int layoutHeight = layoutSize.second;
        int videoWidth = videoSize.first;
        int videoHeight = videoSize.second;

        float layoutAspectRatio = (float) layoutHeight / (float) layoutWidth;
        float videoAspectRatio = (float) videoHeight / (float) videoWidth;

        if (layoutAspectRatio / videoAspectRatio >= RATIO_FIT_CENTER) {
            return ScaleType.FIT_CENTER;
        } else {
            return ScaleType.CENTER_CROP;
        }
    }


    public static Pair<Integer, Integer> getFitSize(Pair<Integer, Integer> layoutSize,
                                                    Pair<Integer, Integer> videoSize) {

        int layoutWidth = layoutSize.first;
        int layoutHeight = layoutSize.second;
        int videoWidth = videoSize.first;
        int videoHeight = videoSize.second;

        float layoutAspectRatio = (float) layoutHeight / (float) layoutWidth;
        float videoAspectRatio = (float) videoHeight / (float) videoWidth;
//
//        if (layoutAspectRatio > videoAspectRatio || (videoAspectRatio / layoutAspectRatio < 1.6)) {
//            return new Pair<>(layoutWidth, layoutHeight);
//        } else {
//            int fitWidth = layoutWidth;
//            int fitHeight = (int) (layoutWidth / videoAspectRatio);
//            return new Pair<>(fitWidth, fitHeight);
//        }

        if (layoutAspectRatio >= videoAspectRatio && (layoutAspectRatio / videoAspectRatio < RATIO_FIT_CENTER)) {
            int fitHeight = layoutHeight;
            int fitWidth = (int) (fitHeight / videoAspectRatio);
            return new Pair<>(fitWidth, fitHeight);
        } else {
            int fitWidth = layoutWidth;
            int fitHeight = (int) (layoutWidth * videoAspectRatio);
            return new Pair<>(fitWidth, fitHeight);
        }
    }
}
