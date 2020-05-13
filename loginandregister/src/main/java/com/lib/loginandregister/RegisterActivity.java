package com.lib.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lib.loginandregister.contract.RegisterContract;
import com.lib.loginandregister.presenter.RegisterPresenter;
import com.xiang.lib.base.ac.BaseMvpActivity;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends BaseMvpActivity<RegisterContract.IPresenter> implements RegisterContract.IView, View.OnClickListener {

    private EditText ed_name;
    private EditText ed_email;
    private EditText ed_location;
    private EditText ed_mobile;
    private EditText ed_pwd;
    private Button btn_register;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @NotNull
    @Override
    public Class<? extends RegisterContract.IPresenter> registerPresenter() {
        return RegisterPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);
        ed_location = findViewById(R.id.ed_location);
        ed_mobile = findViewById(R.id.ed_mobile);
        ed_pwd = findViewById(R.id.ed_pwd);
        btn_register = findViewById(R.id.btn_register);
    }

    @Override
    public void initData() {
        super.initData();
        btn_register.setOnClickListener(this);
    }

    @Override
    public String getName() {
        return ed_name.getText().toString().trim();
    }

    @Override
    public String getEmail() {
        return ed_email.getText().toString().trim();
    }

    @Override
    public String getLocation() {
        return ed_location.getText().toString().trim();
    }

    @Override
    public String getMobile() {
        return ed_mobile.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return ed_pwd.getText().toString().trim();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this,"注册成功！",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register){
            getPresenter().register();
        }
    }
}
