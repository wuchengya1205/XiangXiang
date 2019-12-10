package com.xiang.main.fragment;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.main.MainActivity;
import com.xiang.main.R;
import com.xiang.main.contract.MenuContract;
import com.xiang.main.presenter.MenuPresenter;
import com.xiang.main.utils.FileUpLoadManager;


/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/12/9
 * time   :16:01
 * desc   :ohuo
 * version: 1.0
 */
public class MenuFragment extends BaseMvpFragment<MenuContract.IPresenter> implements MenuContract.IView, View.OnClickListener, CompoundButton.OnCheckedChangeListener, FileUpLoadManager.FileUpLoadCallBack  {

    private ImageView iv_close;
    private ImageView iv_voice;
    private ImageView iv_shake;
    private Switch sw_voice;
    private Switch sw_shake;
    private ImageView iv_icon;
    private FileUpLoadManager fileUpLoadManager;

    @Override
    protected int getLayoutId() {
        return R.layout.navigationview_head;
    }

    @Override
    public void initView() {
        super.initView();
        iv_close = view.findViewById(R.id.iv_close);
        iv_icon = view.findViewById(R.id.iv_icon);
        iv_voice = view.findViewById(R.id.iv_voice);
        iv_shake = view.findViewById(R.id.iv_shake);
        sw_voice = view.findViewById(R.id.sw_voice);
        sw_shake = view.findViewById(R.id.sw_shake);
    }

    @Override
    public void initData() {
        super.initData();
        fileUpLoadManager = new FileUpLoadManager();
        iv_close.setOnClickListener(this);
        iv_icon.setOnClickListener(this);
        sw_voice.setOnCheckedChangeListener(this);
        sw_shake.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_close) {
            MainActivity.openHome();
        }
        if (v.getId() == R.id.iv_icon) {
            getPictureImage();
        }
    }

    private void getPictureImage() {
//        PictureSelector.create(this)
//                .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .maxSelectNum(10)// 最大图片选择数量
//                .minSelectNum(1)// 最小选择数量
//                .imageSpanCount(4)// 每行显示个数
//                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
//                .previewImage(true)// 是否可预览图片
//                .isCamera(true)// 是否显示拍照按钮
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }



    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onSuccess(String url) {

    }

    @Override
    public void onProgress(int pro, int position) {

    }

    @Override
    public Class<? extends MenuContract.IPresenter> registerPresenter() {
        return MenuPresenter.class;
    }
}
