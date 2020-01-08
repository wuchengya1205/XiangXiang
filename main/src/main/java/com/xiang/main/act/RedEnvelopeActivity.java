package com.xiang.main.act;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.xiang.lib.ARouterPath;
import com.xiang.lib.base.ac.BaseMvpActivity;
import com.xiang.lib.utils.EditTextUtil;
import com.xiang.main.R;
import com.xiang.main.contract.RedEnvelopeContract;
import com.xiang.main.presenter.RedEnvelopePresenter;

@Route(path = ARouterPath.ROUTER_RED_ENVELOPE)
public class RedEnvelopeActivity extends BaseMvpActivity<RedEnvelopeContract.IPresenter> implements RedEnvelopeContract.IView, View.OnClickListener {


    private EditText ed_money;
    private ImageView iv_back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_envelope;
    }

    @Override
    public Class<? extends RedEnvelopeContract.IPresenter> registerPresenter() {
        return RedEnvelopePresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        ed_money = findViewById(R.id.ed_money);
        iv_back = findViewById(R.id.iv_back);
    }

    @Override
    public void initData() {
        super.initData();
        ed_money.addTextChangedListener(textWatcher);
        iv_back.setOnClickListener(this);
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

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            EditTextUtil.keepTwoDecimals(ed_money, 5);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back){
            finish();
        }
    }
}
