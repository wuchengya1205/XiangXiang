package com.xiang.main.chat.ac;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xiang.lib.ARouterPath;
import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.ac.BaseMvpActivity;
import com.xiang.main.R;
import com.xiang.main.chat.contract.AttnInfoContract;
import com.xiang.main.chat.presenter.AttnInfoPresenter;

import de.hdodenhof.circleimageview.CircleImageView;


@Route(path = ARouterPath.ROUTER_USER_INFO)
public class AttnInfoActivity extends BaseMvpActivity<AttnInfoContract.IPresenter> implements AttnInfoContract.IView, View.OnClickListener {

    private CircleImageView iv_icon;
    private TextView tv_name;
    private TextView tv_context;
    private TextView tv_sign;
    private TextView tv_mobile;
    private TextView tv_age;
    private TextView tv_birthday;
    private TextView tv_email;
    private Button btn_send_msg;
    private ImageView iv_sex;
    private ImageView iv_back;
    private String uid;
    private String imageUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attn_info;
    }

    @Override
    public void onSuccess(LoginBean bean) {
        uid = bean.getUid();
        imageUrl = bean.getImageUrl();
        Glide.with(this).load(bean.getImageUrl()).apply(new RequestOptions().placeholder(R.mipmap.icon_user_man).error(R.mipmap.icon_user_man).centerCrop()).into(iv_icon);
        tv_name.setText(bean.getUsername());
        tv_context.setText("  " + bean.getSex() + "  " + bean.getLocation());
        tv_mobile.setText("账号:" + bean.getMobile());
        tv_sign.setText("签名:" + bean.getSign());
        tv_age.setText("年龄:" + bean.getAge());
        tv_birthday.setText("生日:" + bean.getBirthday());
        tv_email.setText("邮箱:" + bean.getEmail());
        if (bean.getSex().equals("男")){
            iv_sex.setImageResource(R.mipmap.icon_sex_man);
        }else {
            iv_sex.setImageResource(R.mipmap.icon_sex_woman);
        }
    }

    @Override
    public void onError(String msg) {

        showToast(msg);
    }

    @Override
    public Class<? extends AttnInfoContract.IPresenter> registerPresenter() {
        return AttnInfoPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        iv_back = findViewById(R.id.iv_back);
        iv_icon = findViewById(R.id.civ_attn_icon);
        tv_name = findViewById(R.id.tv_attn_name);
        tv_context = findViewById(R.id.tv_attn_context);
        tv_sign = findViewById(R.id.tv_attn_sign);
        tv_mobile = findViewById(R.id.tv_attn_mobile);
        tv_age = findViewById(R.id.tv_attn_age);
        tv_birthday = findViewById(R.id.tv_attn_birthday);
        tv_email = findViewById(R.id.tv_attn_email);
        iv_sex = findViewById(R.id.iv_sex);
        btn_send_msg = findViewById(R.id.btn_send_msg);
    }

    @Override
    public void initData() {
        super.initData();
        showLoading();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        String uid = bundle.getString("uid");
        getPresenter().getInfo(uid);
        btn_send_msg.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back){
            finish();
        }
        if (view.getId() == R.id.btn_send_msg){
            if (!uid.isEmpty()){
                Bundle bundle = new Bundle();
                bundle.putString(ChatActivity.KEY_TO_UID,uid);
                goActivity(ARouterPath.ROUTER_CHAT,bundle);
                finish();
            }
        }
    }
}
