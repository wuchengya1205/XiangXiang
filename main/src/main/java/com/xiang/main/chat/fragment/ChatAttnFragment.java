package com.xiang.main.chat.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiang.lib.ARouterPath;
import com.xiang.lib.allbean.AttnBean;
import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.main.R;
import com.xiang.main.chat.adapter.RecyclerListAdapter;
import com.xiang.main.chat.contract.ChatAttnContract;
import com.xiang.main.chat.presenter.ChatAttnPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fengzhangwei
 * date : 2019/12/23
 */
public class ChatAttnFragment extends BaseMvpFragment<ChatAttnContract.IPresenter> implements ChatAttnContract.IView, OnRefreshListener, RecyclerListAdapter.setOnItemClickListener {

    private RecyclerView rv_chat_attn;
    private RecyclerListAdapter recyclerListAdapter;
    private SmartRefreshLayout smart_refresh;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_attn;
    }

    @Override
    public Class<? extends ChatAttnContract.IPresenter> registerPresenter() {
        return ChatAttnPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        rv_chat_attn = view.findViewById(R.id.rv_chat_attn);
        smart_refresh = view.findViewById(R.id.smart_refresh);
    }

    @Override
    public void initData() {
        super.initData();
        recyclerListAdapter = new RecyclerListAdapter(getContext(), new ArrayList<AttnBean>());
        rv_chat_attn.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_chat_attn.setAdapter(recyclerListAdapter);
        smart_refresh.setOnRefreshListener(this);
        recyclerListAdapter.setOnItem(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getAllAttn();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiverMsg(String msg) {
        getPresenter().getAllAttn();
    }

    @Override
    public void onSuccess(List<AttnBean> data) {
        smart_refresh.finishRefresh();
        if (recyclerListAdapter.getData().size() > 0){
            recyclerListAdapter.clearData();
        }
        recyclerListAdapter.setData(data);
        recyclerListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().getAllAttn();
    }

    @Override
    public void onItemClickListener(String uid) {
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        goActivity(ARouterPath.ROUTER_USER_INFO,bundle);
    }
}
