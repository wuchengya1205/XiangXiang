package com.xiang.lib.application;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lib.library.R;
import com.lib.net.HttpConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.xiang.lib.AppConfig;
import com.xiang.lib.base.NetConfig;
import com.xiang.lib.utils.Constant;

import java.util.HashMap;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/12/9
 * time   :11:42
 * desc   :ohuo
 * version: 1.0
 */
public class BaseApplication extends Application {

    public static BaseApplication instance;

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        HttpConfig.INSTANCE.init(Constant.BASE_URL,getHeader(),getParams(),true);
        CrashReport.initCrashReport(getApplicationContext(), "cc388b0013", false);
        Fresco.initialize(this);
        init();
    }

    private void init() {
        //组件化ARouter初始化
        if (AppConfig.isDebug) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    /**
     * 公共Header
     * @return
     */
    public static HashMap<String,String> getHeader(){
        HashMap<String, String> hashMap = new HashMap<>();
        return hashMap;
    }

    public static Context getContext(){
        return instance;
    }

    /**
     * 公共参数
     * @return
     */
    public static HashMap<String,String> getParams(){
        HashMap<String, String> hashMap = new HashMap<>();
        return hashMap;
    }
}
