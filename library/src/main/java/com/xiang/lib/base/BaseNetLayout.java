package com.xiang.lib.base;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.lib.library.R;


public class BaseNetLayout extends FrameLayout implements View.OnClickListener {

    private Context mContext;
    private FrameLayout net;

    public BaseNetLayout(Context context) {
        this(context,null);
        this.mContext = context;
    }

    public BaseNetLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        this.mContext = context;
    }

    public BaseNetLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        View view =  LayoutInflater.from(context).inflate(R.layout.layout_net_not_connect,this,true);
        init(view);


    }

    private void init(View view) {
        net = view.findViewById(R.id.fl_net);
        net.setVisibility(GONE);
        net.setOnClickListener(this);
    }

    public void setNetChange(Boolean b){
        net.setVisibility( b ? GONE : VISIBLE);
        Log.i("Net","BaseLinearLayout::::" + b);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fl_net){
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            mContext.startActivity(intent);
        }
    }
}
