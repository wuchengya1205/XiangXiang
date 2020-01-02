package com.xiang.lib.base.ac;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.xiang.lib.utils.ActivityUtils;


public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init(savedInstanceState);
        ActivityUtils.getInstance().pushActivity(this);
        initView();
        initData();
    }

    public void initData() {

    }

    public void initView() {

    }

    public void init(Bundle savedInstanceState) {

    }

    protected void goActivity(Class cls){
        goActivity(cls,null);
    }

    protected void goActivity(Class cls,Bundle bundle){
        Intent intent = new Intent(this, cls);
        if (bundle != null){
            intent.putExtra("bundle",bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.getInstance().popActivity(this);
    }

    protected abstract int getLayoutId();
}
