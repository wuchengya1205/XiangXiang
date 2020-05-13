package com.lib.loginandregister;


import android.Manifest;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lib.loginandregister.contract.LoginContract;
import com.lib.loginandregister.presenter.LoginPresenter;
import com.lib.net.HttpConfig;
import com.xiang.lib.ARouterPath;
import com.xiang.lib.application.BaseApplication;
import com.xiang.lib.base.ac.BaseMvpActivity;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.SPUtils;
import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;

import java.util.List;

@Route(path = ARouterPath.ROUTER_LOGIN)
public class LoginActivity extends BaseMvpActivity<LoginContract.IPresenter> implements LoginContract.IView, View.OnClickListener  {

    private EditText mobile;
    private EditText password;
    private Button btn_login;
    private TextView tv_regs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void loginSuccess() {
       goActivity(ARouterPath.ROUTER_MAIN);
       finish();
    }

    @Override
    public void loginError(String msg) {
        showToast(msg);
    }


    @Override
    public String getPhone() {
        return mobile.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public Class<? extends LoginContract.IPresenter> registerPresenter() {
        return LoginPresenter.class;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (EasyPermission
                .build()
                .hasPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
            login();
        }else {
            arvixe();
        }

    }

    private void arvixe() {
        EasyPermission.build()
                .mRequestCode(10010)
                .mContext(this)
                .mPerms(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        login();
                    }

                    @Override
                    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                        super.onPermissionsDismiss(requestCode, permissions);
                        finish();
                    }
                }).requestPermission();
    }

    private void login() {
        showLoading();
        String m = SPUtils.getInstance().getString(Constant.SPKey_PHONE);
        String p = SPUtils.getInstance().getString(Constant.SPKey_PWD);
        if (m.isEmpty() || p.isEmpty()){
            dismissLoading();
            return;
        }
        mobile.setText(m);
        password.setText(p);
        getPresenter().login();
    }

    @Override
    public void initView() {
        super.initView();
        mobile = findViewById(R.id.ed_mobile);
        password = findViewById(R.id.ed_pwd);
        btn_login = findViewById(R.id.btn_login);
        tv_regs = findViewById(R.id.tv_regs);
    }

    @Override
    public void initData() {
        super.initData();
        HttpConfig.INSTANCE.init(Constant.BASE_TOMACT_URL, BaseApplication.getHeader(),BaseApplication.getParams(),true);
        btn_login.setOnClickListener(this);
        tv_regs.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){
            getPresenter().login();
        }
        if (v.getId() == R.id.tv_regs){
            goActivity(RegisterActivity.class);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
