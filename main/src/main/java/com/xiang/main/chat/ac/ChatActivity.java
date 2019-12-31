package com.xiang.main.chat.ac;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.codebear.keyboard.CBEmoticonsKeyBoard;
import com.codebear.keyboard.data.AppFuncBean;
import com.codebear.keyboard.data.EmoticonsBean;
import com.codebear.keyboard.widget.CBAppFuncView;
import com.codebear.keyboard.widget.CBEmoticonsView;
import com.codebear.keyboard.widget.FuncLayout;
import com.codebear.keyboard.widget.RecordIndicator;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lib.xiangxiang.im.GsonUtil;
import com.lib.xiangxiang.im.ImSendMessageUtils;
import com.lib.xiangxiang.im.SocketManager;
import com.xiang.lib.ARouterPath;
import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.ac.BaseMvpActivity;
import com.xiang.lib.chatBean.ChatMessage;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.NetState;
import com.xiang.lib.utils.OfTenUtils;
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
public class ChatActivity extends BaseMvpActivity<ChatContract.IPresenter> implements ChatContract.IView, RecordIndicator.OnRecordListener, CBEmoticonsView.OnEmoticonClickListener, SocketManager.SendMsgCallBack, FuncLayout.OnFuncKeyBoardListener {

    private CBEmoticonsKeyBoard cb_kb;
    private RecordIndicator recordIndicator;
    private RecyclerView rv_chat;
    public static final String KEY_TO_UID = "key_to_uid";
    private String to_uid;
    private ChatAdapter chatAdapter;
    private TextView tv_chat_name;
    private TextView tv_net_state;
    private Boolean rv_isLat = false;
    private int pageNo = 1;
    private int pageSize = 30;
    private String conviction;

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
        tv_chat_name = findViewById(R.id.tv_chat_name);
        tv_net_state = findViewById(R.id.tv_net_state);
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
        Bundle bundle = getIntent().getBundleExtra(BUNDLE);
        to_uid = bundle.getString(KEY_TO_UID);
        getPresenter().getUserInfo(to_uid);
        conviction = OfTenUtils.getConviction(SPUtils.getInstance().getString(Constant.SPKey_UID), to_uid);
        getPresenter().getHistory(conviction,pageNo,pageSize);
    }

    private void initRecyclerView() {
        rv_chat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (cb_kb != null){
                    cb_kb.reset();
                }
                return false;
            }
        });
        rv_chat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            Boolean isToLast = false;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
               LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();//从0开始
                    int totalItemCount = layoutManager.getItemCount();
                    // 判断是否滚动到底部，并且是向下滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isToLast ) {
                        //加载更多功能的代码
                        rv_isLat = true;
                    }else {
                        rv_isLat = false;
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isToLast = dy > 0;
            }
        });
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
        cb_kb.addOnFuncKeyBoardListener(this);
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
            String json = ImSendMessageUtils.getChatMessage(input,from_uid,to_uid,ChatMessage.MSG_BODY_TYPE_TEXT,chatAdapter.getLastItemDisplayTime());
            ChatMessage message = GsonUtil.GsonToBean(json, ChatMessage.class);
            chatAdapter.setData(message);
            toLastItem();
            Log.i("chat","---size---" + chatAdapter.getItemCount());
            SocketManager.sendMsgSocket(this,json,this);
        }
    }

    private void toLastItem(){
        chatAdapter.notifyItemChanged(chatAdapter.getItemCount()-1);
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
        chatAdapter.notifyChatMessage(bean);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiverMsg(ChatMessage msg) {
        if (chatAdapter != null){
            chatAdapter.setData(msg);
            toLastItem();
        }
    }

    @Override
    public void onSuccessInfo(LoginBean bean) {
        rv_chat.setLayoutManager(new LinearLayoutManager(this));
        String json = SPUtils.getInstance().getString(Constant.SPKey_USERINFO);
        LoginBean from_bean = GsonUtil.GsonToBean(json, LoginBean.class);
        Log.i("chat","------url-------" + bean.getImageUrl());
        if (chatAdapter == null){
            chatAdapter = new ChatAdapter(new ArrayList<ChatMessage>(), this, from_bean.getImageUrl(), bean.getImageUrl());
        }
        rv_chat.setAdapter(chatAdapter);
        tv_chat_name.setText(bean.getUsername());
        tv_net_state.setText(NetState.getNetState(bean.getOnline()));

    }

    @Override
    public void onSuccessHistory(List<com.xiang.lib.chatBean.ChatMessage> list) {
        Log.i("chat","------历史记录-------" + list.size());
        int size = list.size();
        chatAdapter.setData(list);
        Log.i("chat","------历史记录-------" + chatAdapter.getData().size());
//        rv_chat.scrollToPosition(chatAdapter.getItemCount()-1);

    }


    @Override
    public void onFuncPop(int height) {
        if (rv_isLat){
            toLastItem();
        }else {
            rv_chat.scrollToPosition(chatAdapter.getItemCount()-1);
        }
    }

    @Override
    public void onFuncClose() {

    }
}
