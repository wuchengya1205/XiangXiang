package com.xiang.lib.base.ac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lib.library.R;
import com.xiang.lib.base.BaseNetLayout;
import com.xiang.lib.base.LoadingDialog;
import com.xiang.lib.base.NetBroadcastReceiver;
import com.xiang.lib.base.NetChangeListener;
import mvp.ljb.kt.contract.IPresenterContract;
import mvp.ljb.kt.view.MvpActivity;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public abstract class BaseMvpActivity<P extends IPresenterContract> extends MvpActivity<P> implements NetChangeListener {

    private ImageView toolbar_back;
    private TextView toolbar_title;
    private TextView toolbar_menu_tv;
    private ImageView toolbar_menu;
    private LoadingDialog mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init(savedInstanceState);
        initView();
        initData();
        initBar();
    }

    public void initBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为 true)
                .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为 true）
                .statusBarAlpha(0.3f)  //状态栏透明度，不写默认 0.0f
                .navigationBarAlpha(0.2f)  //导航栏透明度，不写默认 0.0F
                .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认 0.0f
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .init();
    }

    public void initData() {
        mLoading = new LoadingDialog(this,false);
        boolean connected = NetBroadcastReceiver.isNetworkConnected(this);
        setNetChange(connected);
    }

    public void initView() {
        toolbar_back = findViewById(R.id.toolbar_back);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_menu_tv = findViewById(R.id.toolbar_menu_tv);
        toolbar_menu = findViewById(R.id.toolbar_menu);

    }

    public void init(Bundle savedInstanceState) {
        NetBroadcastReceiver.setRegisterNetBRChange(this);

    }

    public void goActivity(Class cls) {
        goActivity(cls,null);
    }

    public void goActivity(Class cls,Bundle bundle){
        Intent intent = new Intent( this, cls);
        if (bundle != null){
            intent.putExtra("bundle",bundle);
        }
        startActivity(intent);
    }



    @Override
    public void onChangeListener(Boolean status)  {
        setNetChange(status);
    }

    private void setNetChange(Boolean status) {
    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoading() {
        if (mLoading != null && !mLoading.isShowing()){
            mLoading.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (mLoading != null && mLoading.isShowing()){
            mLoading.dismiss();
        }
    }

    public void initToolBar(String title){
        initToolBar(false,title,null);
    }

    public void initToolBar(String title, View.OnClickListener onClickListener){
        initToolBar(false,title,onClickListener);
    }

    public void initToolBarMenu(Boolean hideBack,String title,Boolean hideMenuIv){
        initToolBar(hideBack,title,null);
        initToolBarMenu(hideMenuIv,null);
    }

    public void initToolBarMenu(Boolean hideBack, String title, Boolean hideMenuIv, View.OnClickListener onClickListener){
        initToolBar(hideBack,title,onClickListener);
        initToolBarMenu(hideMenuIv,onClickListener);
    }

    public void initToolbarAction(Boolean hideBack, String title, Boolean hideMenuTv, View.OnClickListener onClickListener){
        initToolbarAction(hideMenuTv,onClickListener);
        initToolBar(hideBack,title,onClickListener);
    }

    public void initToolbarAction(Boolean hideMenuTv, View.OnClickListener onClickListener){
        if (toolbar_menu_tv == null){return;}
        toolbar_menu_tv.setVisibility(hideMenuTv ? VISIBLE : GONE);
        toolbar_menu_tv.setOnClickListener(onClickListener);
    }

    public void initToolBarMenu(Boolean hideMenuIv, View.OnClickListener onClickListener){
        if (toolbar_menu == null){return;}
        toolbar_menu.setVisibility(hideMenuIv ? VISIBLE : GONE);
        toolbar_menu.setOnClickListener(onClickListener);
    }

    public void initToolBar(Boolean hideBack, String title, View.OnClickListener onClickListener){
        if (toolbar_back == null || toolbar_title == null){return;}
        toolbar_back.setVisibility(hideBack ? VISIBLE : GONE);
        toolbar_title.setText(title);
        toolbar_back.setOnClickListener(onClickListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismissLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        dismissLoading();
        if (mLoading != null){
            mLoading = null;
        }
    }

    protected abstract int getLayoutId();
}
