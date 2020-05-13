package com.xiang.main.chat.fragment;


import android.Manifest;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.location.com.SendLocationActivity;
import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.lib.chatBean.ChatMessage;
import com.xiang.main.R;
import com.xiang.main.chat.contract.ChatPageContract;
import com.xiang.main.chat.presenter.ChatPagePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/10/24
 * time   :15:06
 * desc   :ohuo
 * version: 1.0
 */
public class ChatPageFragment extends BaseMvpFragment<ChatPageContract.IPresenter> implements ChatPageContract.IView, View.OnClickListener {


    private TabLayout tab_chat;
    private ViewPager vp_chat;
    private List<String> list_title = new ArrayList<>();
    private List<Fragment> list_fragment = new ArrayList<>();
    private Button btn_send_location;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        tab_chat = view.findViewById(R.id.tab_chat);
        vp_chat = view.findViewById(R.id.vp_chat);
        btn_send_location = view.findViewById(R.id.btn_send_location);
    }

    @Override
    public void initData() {
        super.initData();
        list_title.add("消息");
        list_title.add("联系人");
        list_fragment.add(new ChatMsgFragment());
        list_fragment.add(new ChatAttnFragment());
        vp_chat.setAdapter(new myAdapter(getChildFragmentManager()));
        tab_chat.setupWithViewPager(vp_chat);
        btn_send_location.setOnClickListener(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public Class<? extends ChatPageContract.IPresenter> registerPresenter() {
        return ChatPagePresenter.class;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_send_location){
            goActivity(SendLocationActivity.class);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiverMsg(ChatMessage msg) {

    }

    class myAdapter extends FragmentPagerAdapter{

        public myAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_title.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return list_title.get(position);
        }
    }

}
