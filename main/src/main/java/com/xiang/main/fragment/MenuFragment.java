package com.xiang.main.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.xiang.lib.ARouterPath;
import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.lib.utils.ActivityUtils;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.SPUtils;
import com.xiang.main.MainActivity;
import com.xiang.main.R;
import com.xiang.main.contract.MenuContract;
import com.xiang.main.presenter.MenuPresenter;
import com.xiang.main.utils.FileUpLoadManager;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.icu.text.DateTimePatternGenerator.PatternInfo.OK;


/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/12/9
 * time   :16:01
 * desc   :ohuo
 * version: 1.0
 */
public class MenuFragment extends BaseMvpFragment<MenuContract.IPresenter> implements MenuContract.IView, View.OnClickListener, CompoundButton.OnCheckedChangeListener, FileUpLoadManager.FileUpLoadCallBack {

    private ImageView iv_close;
    private ImageView iv_voice;
    private ImageView iv_shake;
    private Switch sw_voice;
    private Switch sw_shake;
    private CircleImageView iv_icon;
    private LoginBean userInfo;
    private TextView tv_name;
    private TextView tv_sign;
    private LinearLayout linear_info;
    private LinearLayout linear_logout;

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
        tv_name = view.findViewById(R.id.tv_name);
        tv_sign = view.findViewById(R.id.tv_sign);
        linear_info = view.findViewById(R.id.linear_info);
        linear_logout = view.findViewById(R.id.linear_logout);
    }

    @Override
    public void initData() {
        super.initData();
        iv_close.setOnClickListener(this);
        sw_voice.setOnCheckedChangeListener(this);
        sw_shake.setOnCheckedChangeListener(this);
        linear_info.setOnClickListener(this);
        linear_logout.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        String json = SPUtils.getInstance().getString(Constant.SPKey_USERINFO);
        if (!json.isEmpty()) {
            userInfo = new Gson().fromJson(json, LoginBean.class);
        }
        if (userInfo != null) {
            if (userInfo.getSex().equals("男")){
                Glide.with(getContext()).load(userInfo.getImageUrl()).apply(new RequestOptions().placeholder(R.mipmap.icon_user_man).error(R.mipmap.icon_user_man).centerCrop()).into(iv_icon);
            }else {
                Glide.with(getContext()).load(userInfo.getImageUrl()).apply(new RequestOptions().placeholder(R.mipmap.icon_user_woman).error(R.mipmap.icon_user_woman).centerCrop()).into(iv_icon);
            }
            tv_name.setText(userInfo.getUsername());
            tv_sign.setText(userInfo.getSign());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_close) {
            MainActivity.openHome();
        }
        if (v.getId() == R.id.linear_info) {
            goActivity(ARouterPath.ROUTER_INFO);
        }
        if (v.getId() == R.id.linear_logout){
            goActivity(ARouterPath.ROUTER_LOGIN);
            ActivityUtils.getInstance().popAllActivity();
            SPUtils.getInstance().put(Constant.SPKey_PWD,"");
            SPUtils.getInstance().put(Constant.SPKey_PHONE,"");
            SPUtils.getInstance().put(Constant.SPKey_UID,"");
            SPUtils.getInstance().put(Constant.SPKey_USERINFO,"");
        }
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


    @Override
    public void initBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();
    }

}
