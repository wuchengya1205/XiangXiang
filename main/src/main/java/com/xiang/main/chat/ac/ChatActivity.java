package com.xiang.main.chat.ac;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.codebear.keyboard.CBEmoticonsKeyBoard;
import com.codebear.keyboard.data.AppFuncBean;
import com.codebear.keyboard.data.EmoticonsBean;
import com.codebear.keyboard.widget.CBAppFuncView;
import com.codebear.keyboard.widget.CBEmoticonsView;
import com.codebear.keyboard.widget.RecordIndicator;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lib.xiangxiang.im.ChatMessage;
import com.lib.xiangxiang.im.GsonUtil;
import com.lib.xiangxiang.im.ImSendMessageUtils;
import com.lib.xiangxiang.im.SocketManager;
import com.xiang.lib.ARouterPath;
import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.ac.BaseMvpActivity;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.SPUtils;
import com.xiang.main.R;
import com.xiang.main.chat.adapter.ChatAdapter;
import com.xiang.main.chat.contract.ChatContract;
import com.xiang.main.chat.presenter.ChatPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.ROUTER_CHAT)
public class ChatActivity extends BaseMvpActivity<ChatContract.IPresenter> implements ChatContract.IView, RecordIndicator.OnRecordListener, CBEmoticonsView.OnEmoticonClickListener, SocketManager.SendMsgCallBack {

    private CBEmoticonsKeyBoard cb_kb;
    private RecordIndicator recordIndicator;
    private RecyclerView rv_chat;
    public static final String KEY_TO_UID = "key_to_uid";
    public static final String KEY_TO_URL = "key_to_url";
    private String to_uid;
    private ChatAdapter chatAdapter;
    private String to_url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public Class<? extends ChatContract.IPresenter> registerPresenter() {
        return ChatPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        cb_kb = findViewById(R.id.cb_kb);
        rv_chat = findViewById(R.id.rv_chat);
        initRecyclerView();
        initKeyBoard();
        initOptions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_SHOW_BAR).init();
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        to_uid = bundle.getString(KEY_TO_UID);
        to_url = bundle.getString(KEY_TO_URL);

    }

    private void initRecyclerView() {
        rv_chat.setLayoutManager(new LinearLayoutManager(this));
        rv_chat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (cb_kb != null){
                    cb_kb.reset();
                }
                return false;
            }
        });
        String json = SPUtils.getInstance().getString(Constant.SPKey_USERINFO);
        LoginBean bean = GsonUtil.GsonToBean(json, LoginBean.class);
        Log.i("chat","------url-------" + to_url);
        chatAdapter = new ChatAdapter(new ArrayList<ChatMessage>(), this, bean.getImageUrl(), to_url);
        rv_chat.setAdapter(chatAdapter);
    }

    private void initOptions() {
        List<AppFuncBean> list = new ArrayList<>();
        list.add(new AppFuncBean(111001101,R.mipmap.ic_launcher,"图片"));
        list.add(new AppFuncBean(111001102,R.mipmap.ic_launcher,"视频"));
        list.add(new AppFuncBean(111001103,R.mipmap.ic_launcher,"位置"));
        CBAppFuncView appFuncView = new CBAppFuncView(this);
        appFuncView.setRol(3);
        appFuncView.setAppFuncBeanList(list);
        cb_kb.setAppFuncView(appFuncView);
        appFuncView.setOnAppFuncClickListener(new CBAppFuncView.OnAppFuncClickListener() {
            @Override
            public void onAppFunClick(AppFuncBean emoticon) {
                int id = emoticon.getId();
                switch (id){
                    case 111001101:
                        break;
                    case 111001102:
                        break;
                    case 111001103:
                        break;
                        default:
                            break;
                }
            }
        });
    }

    private void initKeyBoard() {
        cb_kb.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = cb_kb.getEtChat().getText().toString();
                sendText(input);
                cb_kb.getEtChat().setText("");
            }
        });
        recordIndicator = new RecordIndicator(this);
        cb_kb.setRecordIndicator(recordIndicator);
        recordIndicator.setOnRecordListener(this);
        recordIndicator.setMaxRecordTime(60);
        CBEmoticonsView cbEmoticonsView = new CBEmoticonsView(this);
        cbEmoticonsView.init(getSupportFragmentManager());
        cb_kb.setEmoticonFuncView(cbEmoticonsView);
        cbEmoticonsView.addEmoticonsWithName(new String[]{"default"});
        cbEmoticonsView.setOnEmoticonClickListener(this);
    }

    private void sendText(String input) {
        if (!to_uid.isEmpty()){
            String from_uid = SPUtils.getInstance().getString(Constant.SPKey_UID);
            String json = ImSendMessageUtils.getChatMessage(input,from_uid,to_uid,ChatMessage.MSG_BODY_TYPE_TEXT);
            ChatMessage message = GsonUtil.GsonToBean(json, ChatMessage.class);
            chatAdapter.setData(message);
            toLastItem();
            Log.i("chat","---size---" + chatAdapter.getItemCount());
            SocketManager.sendMsgSocket(this,json,this);
        }
    }

    private void toLastItem(){
        rv_chat.scrollToPosition(chatAdapter.getItemCount()-1);
    }

    @Override
    public void recordStart() {

    }

    @Override
    public void recordFinish() {

    }

    @Override
    public void recordCancel() {

    }

    @Override
    public long getRecordTime() {
        return 0;
    }

    @Override
    public int getRecordDecibel() {
        return 0;
    }

    @Override
    public void onEmoticonClick(EmoticonsBean emoticon, boolean isDel) {

    }

    @Override
    public void call(String msg) {
        ChatMessage bean = GsonUtil.GsonToBean(msg, ChatMessage.class);

    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiverMsg(ChatMessage msg) {
        if (chatAdapter != null){
            chatAdapter.setData(msg);
            toLastItem();
        }
    }
}
