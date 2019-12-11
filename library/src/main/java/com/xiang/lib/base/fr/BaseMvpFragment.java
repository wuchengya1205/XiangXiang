package com.xiang.lib.base.fr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.coder.circlebar.CircleBar;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lib.library.R;
import com.xiang.lib.base.LoadingDialog;

import mvp.ljb.kt.contract.IPresenterContract;
import mvp.ljb.kt.view.MvpFragment;


public abstract class BaseMvpFragment<P extends IPresenterContract> extends MvpFragment<P> {


    public View view;
    private LoadingDialog mLoading;
    private LoadingDialog mUpLoading;
    private RelativeLayout mUpView;
    private CircleBar mPbCircle;
    private TextView mUpCount;
    private int[] mColors = new int[]{0xFF123456, 0xFF369852, 0xFF147852};
    private TextView tv_pb;
    private int upLoadCount = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null){
            ViewGroup parent = (ViewGroup)view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initBar();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

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

    public void initData() {
        mLoading = new LoadingDialog(getContext(),false);
        mUpLoading = new LoadingDialog(getContext(),false, R.layout.layout_pb);
        mUpView = (RelativeLayout) mUpLoading.getView();
        mPbCircle = mUpView.findViewById(R.id.pb_circle);
        mUpCount = mUpView.findViewById(R.id.tv_up_count);
        tv_pb = mUpView.findViewById(R.id.tv_pb);
        mPbCircle.setMaxstepnumber(100);
        mPbCircle.setShaderColor(mColors);
    }

    public void initView() {

    }

    @Override
    public void showLoading() {
        if (mLoading != null && !mLoading.isShowing()){
            mLoading.show();
        }
    }

    public void showUpLoading(){
        if (mUpLoading != null && !mUpLoading.isShowing()){
            tv_pb.setText("0%");
            mUpLoading.show();
        }
    }

    public void dismissUpLoading(){
        if (mUpLoading != null && mUpLoading.isShowing()){
            mUpLoading.dismiss();
        }
    }

    public void setUpLoadCount(int count){
        if (count < 1){return;}
        this.upLoadCount = count;
    }

    public void updatePb(final int position, final int pro){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_pb.setText(pro+"%");
                mUpCount.setText(position+"/" + upLoadCount);
                mPbCircle.update(pro,0);
            }
        });
    }

    @Override
    public void dismissLoading() {
        if (mLoading != null && mLoading.isShowing()){
            mLoading.dismiss();
        }
    }

    public void goActivity(Class cls){
        goActivity(cls,null);
    }

    public void goActivity(String arPath){
        if (arPath.isEmpty()){return;}
        ARouter.getInstance()
                .build(arPath)
                .withTransition(R.anim.fade_in, R.anim.fade_out)
                .navigation();
    }

    public void goActivity(String arPath,int code){
        if (arPath.isEmpty()){return;}
        ARouter.getInstance()
                .build(arPath)
                .withTransition(R.anim.fade_in, R.anim.fade_out)
                .navigation(getActivity(),code);
    }

    public void goActivity(Class cls,Bundle bundle){
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null){
            intent.putExtra("bundle",bundle);
        }
        this.startActivity(intent);
    }

    protected abstract int getLayoutId();

    private void init(Bundle savedInstanceState) {

    }

    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
