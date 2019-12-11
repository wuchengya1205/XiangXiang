package com.xiang.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xiang.lib.ARouterPath;
import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.ac.BaseMvpActivity;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.SPUtils;
import com.xiang.main.contract.UserInfoContract;
import com.xiang.main.presenter.UserInfoPresenter;
import com.xiang.main.utils.FileUpLoadManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = ARouterPath.ROUTER_INFO)
public class UserInfoActivity extends BaseMvpActivity<UserInfoContract.IPresenter> implements UserInfoContract.IView, View.OnClickListener, FileUpLoadManager.FileUpLoadCallBack {

    private ImageView iv_sex_info;
    private CircleImageView iv_icon_info;
    private EditText ed_name_info;
    private TextView ed_mobile_info;
    private TextView ed_age_info;
    private EditText ed_location_info;
    private TextView tv_birthday_info;
    private EditText ed_sign_info;
    private EditText ed_email_info;
    private LoginBean userInfo;
    private ImageView toolbar_back;
    private String image_url = "";
    private Button btn_change_info;
    private FileUpLoadManager fileUpLoadManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public Class<? extends UserInfoContract.IPresenter> registerPresenter() {
        return UserInfoPresenter.class;
    }

    @Override
    public void initBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR) // 隐藏导航栏或者状态栏
                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为 true)
                .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为 true）
                .statusBarAlpha(0.5f)  //状态栏透明度，不写默认 0.0f
                .navigationBarAlpha(0.2f)  //导航栏透明度，不写默认 0.0F
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .init();
    }


    @Override
    public void initView() {
        super.initView();
        toolbar_back = findViewById(R.id.toolbar_back);
        iv_icon_info = findViewById(R.id.iv_icon_info);
        iv_sex_info = findViewById(R.id.iv_sex_info);
        ed_name_info = findViewById(R.id.ed_name_info);
        ed_mobile_info = findViewById(R.id.ed_mobile_info);
        ed_age_info = findViewById(R.id.ed_age_info);
        ed_location_info = findViewById(R.id.ed_location_info);
        tv_birthday_info = findViewById(R.id.tv_birthday_info);
        ed_sign_info = findViewById(R.id.ed_sign_info);
        ed_email_info = findViewById(R.id.ed_email_info);
        btn_change_info = findViewById(R.id.btn_change_info);
    }

    @Override
    public void initData() {
        super.initData();
        String json = SPUtils.getInstance().getString(Constant.SPKey_USERINFO);
        if (!json.isEmpty()) {
            userInfo = new Gson().fromJson(json, LoginBean.class);
        }
        setUserInfo();
        fileUpLoadManager = new FileUpLoadManager();
        iv_icon_info.setOnClickListener(this);
        toolbar_back.setOnClickListener(this);
        btn_change_info.setOnClickListener(this);
    }

    private void setUserInfo() {
        if (userInfo != null) {
            if (userInfo.getSex().equals("男")) {
                Glide.with(this).load(userInfo.getImageUrl()).apply(new RequestOptions().placeholder(R.mipmap.icon_user_man).error(R.mipmap.icon_user_man).centerCrop()).into(iv_icon_info);
                iv_sex_info.setImageResource(R.mipmap.icon_sex_man);
            } else {
                Glide.with(this).load(userInfo.getImageUrl()).apply(new RequestOptions().placeholder(R.mipmap.icon_user_woman).error(R.mipmap.icon_user_woman).centerCrop()).into(iv_icon_info);
                iv_sex_info.setImageResource(R.mipmap.icon_sex_woman);
            }
            ed_name_info.setText(userInfo.getUsername());
            ed_mobile_info.setText(userInfo.getMobile());
            ed_age_info.setText(userInfo.getAge() + "");
            ed_location_info.setText(userInfo.getLocation());
            tv_birthday_info.setText(userInfo.getBirthday());
            ed_sign_info.setText(userInfo.getSign());
            ed_email_info.setText(userInfo.getEmail());
        }
    }

    private void getPictureImage() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(10)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PictureConfig.CHOOSE_REQUEST){return;}
        List<LocalMedia> images;
        ArrayList<String> list = new ArrayList<>();
        if (resultCode == RESULT_OK) {
            showLoading();
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                images = PictureSelector.obtainMultipleResult(data);
                list.add(images.get(0).getPath());
                if (list.size() <= 0){dismissLoading();return;}
                fileUpLoadManager.upLoadFile(list,this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_icon_info) {
            getPictureImage();
        }
        if (view.getId() == R.id.toolbar_back) {
            finish();
        }
        if (view.getId() == R.id.btn_change_info) {
            showLoading();
            getPresenter().changeUserInfo(userInfo);
        }

    }

    @Override
    public String getIconUrl() {
        return image_url;
    }

    @Override
    public String getName() {
        return ed_name_info.getText().toString().trim();
    }

    @Override
    public String getLocation() {
        return ed_location_info.getText().toString().trim();
    }

    @Override
    public String getSign() {
        return ed_sign_info.getText().toString().trim();
    }

    @Override
    public String getEmail() {
        return ed_email_info.getText().toString().trim();
    }

    @Override
    public void onError(String msg) {
        showToast(msg);
    }

    @Override
    public void onSuccess() {
        showToast("修改成功");
        finish();
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onSuccess(String url) {
        image_url = url;
        if (url != null) {
            if (userInfo.getSex().equals("男")) {
                Glide.with(this).load(url).apply(new RequestOptions().placeholder(R.mipmap.icon_user_man).error(R.mipmap.icon_user_man).centerCrop()).into(iv_icon_info);
            } else {
                Glide.with(this).load(url).apply(new RequestOptions().placeholder(R.mipmap.icon_user_woman).error(R.mipmap.icon_user_woman).centerCrop()).into(iv_icon_info);
            }
        }
        dismissLoading();
    }

    @Override
    public void onProgress(int pro, int position) {

    }
}
