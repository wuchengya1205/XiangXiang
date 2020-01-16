package com.xiang.main.chat.ac;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.codebear.keyboard.CBEmoticonsKeyBoard;
import com.codebear.keyboard.data.AppFuncBean;
import com.codebear.keyboard.data.EmoticonsBean;
import com.codebear.keyboard.utils.EmoticonsKeyboardUtils;
import com.codebear.keyboard.widget.CBAppFuncView;
import com.codebear.keyboard.widget.CBEmoticonsView;
import com.codebear.keyboard.widget.FuncLayout;
import com.codebear.keyboard.widget.RecordIndicator;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.labo.kaji.relativepopupwindow.RelativePopupWindow;
import com.lib.xiangxiang.im.GsonUtil;
import com.lib.xiangxiang.im.ImSendMessageUtils;
import com.lib.xiangxiang.im.ImSocketClient;
import com.lib.xiangxiang.im.SocketManager;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiang.lib.ARouterPath;
import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.base.ac.BaseMvpActivity;
import com.xiang.lib.chatBean.ChatMessage;
import com.xiang.lib.chatBean.RedEnvelopeBody;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.NetState;
import com.xiang.lib.utils.OfTenUtils;
import com.xiang.lib.utils.SPUtils;
import com.xiang.main.R;
import com.xiang.main.chat.adapter.ChatAdapter;
import com.xiang.main.chat.contract.ChatContract;
import com.xiang.main.chat.presenter.ChatPresenter;
import com.xiang.main.utils.FileUpLoadManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.ROUTER_CHAT)
public class ChatActivity extends BaseMvpActivity<ChatContract.IPresenter> implements ChatContract.IView, RecordIndicator.OnRecordListener, CBEmoticonsView.OnEmoticonClickListener, SocketManager.SendMsgCallBack, FuncLayout.OnFuncKeyBoardListener, OnRefreshListener, View.OnClickListener, ChatAdapter.ChatItem {

    private final int REQUEST_CODE_RED_ENVELOPE = 123;
    private final int APP_FUNC_IMAGE = 123456780;
    private final int APP_FUNC_VIDEO = 123456781;
    private final int APP_FUNC_LOCATION =123456782;
    private final int APP_FUNC_RED_ENVELOPE = 123456783;
    private CBEmoticonsKeyBoard cb_kb;
    private RecordIndicator recordIndicator;
    private RecyclerView rv_chat;
    public static final String KEY_TO_UID = "key_to_uid";
    private String to_uid;
    private ChatAdapter chatAdapter;
    private TextView tv_chat_name;
    private TextView tv_net_state;
    private int pageNo = 1;
    private int pageSize = 30;
    private String conviction;
    private SmartRefreshLayout smart_refresh;
    private LinearLayoutManager layoutManager;
    private ImageView iv_back;
    private FrameLayout fl_socket_hint;
    private FileUpLoadManager fileUpLoadManager;

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
        iv_back = findViewById(R.id.iv_back);
        cb_kb = findViewById(R.id.cb_kb);
        rv_chat = findViewById(R.id.rv_chat);
        tv_chat_name = findViewById(R.id.tv_chat_name);
        tv_net_state = findViewById(R.id.tv_net_state);
        fl_socket_hint = findViewById(R.id.fl_socket_hint);
        smart_refresh = findViewById(R.id.smart_refresh);
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
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_SHOW_BAR)
                .init();
    }

    @Override
    public void initData() {
        super.initData();
        fileUpLoadManager = new FileUpLoadManager();
        Bundle bundle = getIntent().getBundleExtra(BUNDLE);
        to_uid = bundle.getString(KEY_TO_UID);
        getPresenter().getUserInfo(to_uid);
        conviction = OfTenUtils.getConviction(SPUtils.getInstance().getString(Constant.SPKey_UID), to_uid);
        showLoading();
        getPresenter().getHistory(conviction, pageNo, pageSize);
        smart_refresh.setOnRefreshListener(this);
        iv_back.setOnClickListener(this);
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        rv_chat.setLayoutManager(layoutManager);
        rv_chat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (cb_kb != null) {
                    cb_kb.reset();
                }
                return false;
            }
        });

        rv_chat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    rv_chat.post(new Runnable() {
                        @Override
                        public void run() {
                            if (chatAdapter.getItemCount() > 0) {
                                rv_chat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
    }

    private void initOptions() {
        List<AppFuncBean> list = new ArrayList<>();
        list.add(new AppFuncBean(APP_FUNC_IMAGE, R.mipmap.icon_image, "图片"));
        list.add(new AppFuncBean(APP_FUNC_VIDEO, R.mipmap.icon_video, "视频"));
        list.add(new AppFuncBean(APP_FUNC_LOCATION, R.mipmap.icon_location, "位置"));
        list.add(new AppFuncBean(APP_FUNC_RED_ENVELOPE, R.mipmap.icon_hb, "红包"));
        CBAppFuncView appFuncView = new CBAppFuncView(this);
        appFuncView.setRol(3);
        appFuncView.setAppFuncBeanList(list);
        cb_kb.setAppFuncView(appFuncView);
        appFuncView.setOnAppFuncClickListener(new CBAppFuncView.OnAppFuncClickListener() {
            @Override
            public void onAppFunClick(AppFuncBean emoticon) {
                int id = emoticon.getId();
                switch (id) {
                    case APP_FUNC_IMAGE:
                        selectorPic();
                        break;
                    case APP_FUNC_VIDEO:
                        break;
                    case APP_FUNC_LOCATION:
                        break;
                    case APP_FUNC_RED_ENVELOPE:
                        goActivity(ARouterPath.ROUTER_RED_ENVELOPE, REQUEST_CODE_RED_ENVELOPE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void selectorPic() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
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
        cbEmoticonsView.addEmoticonsWithName(new String[]{"emoji"});
        cbEmoticonsView.setOnEmoticonClickListener(this);
    }

    private void sendText(String input) {
        if (!to_uid.isEmpty()) {
            String from_uid = SPUtils.getInstance().getString(Constant.SPKey_UID);
            String json = ImSendMessageUtils.getChatMessageText(input, from_uid, to_uid, ChatMessage.MSG_BODY_TYPE_TEXT, chatAdapter.getLastItemDisplayTime());
            ChatMessage message = GsonUtil.GsonToBean(json, ChatMessage.class);
            chatAdapter.setData(message);
            toLastItem();
            SocketSendJson(json);
        }
    }

    private void sendEmoji(String url) {
        if (!to_uid.isEmpty()) {
            String from_uid = SPUtils.getInstance().getString(Constant.SPKey_UID);
            String json = ImSendMessageUtils.getChatMessageEmoji(url, from_uid, to_uid, ChatMessage.MSG_BODY_TYPE_EMOJI, chatAdapter.getLastItemDisplayTime());
            ChatMessage message = GsonUtil.GsonToBean(json, ChatMessage.class);
            chatAdapter.setData(message);
            toLastItem();
            SocketSendJson(json);
        }
    }

    private void sendRedEnvelope(String money) {
        if (!to_uid.isEmpty()) {
            String from_uid = SPUtils.getInstance().getString(Constant.SPKey_UID);
            String json = ImSendMessageUtils.getChatMessageRedEnvelope(money, from_uid, to_uid, ChatMessage.MSG_BODY_TYPE_RED_ENVELOPE, chatAdapter.getLastItemDisplayTime());
            ChatMessage message = GsonUtil.GsonToBean(json, ChatMessage.class);
            chatAdapter.setData(message);
            toLastItem();
            SocketSendJson(json);
        }
    }


    private void SocketSendJson(String json) {
        SocketManager.sendMsgSocket(this, json, this);
    }

    private void toLastItem() {
        chatAdapter.notifyItemChanged(chatAdapter.getItemCount() - 1);
        rv_chat.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

    private void showPopWindow(View view) {
        RelativePopupWindow popupWindow = new RelativePopupWindow();
        popupWindow.setContentView(View.inflate(this, R.layout.chat_pop, null));
        popupWindow.setFocusable(true);
        popupWindow.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.CENTER);
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
        sendEmoji(emoticon.getIconUri().toString());
    }

    @Override
    public void call(String msg) {
        ChatMessage bean = GsonUtil.GsonToBean(msg, ChatMessage.class);
        chatAdapter.notifyChatMessage(bean);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiverMsg(ChatMessage msg) {
        if (chatAdapter != null) {
            chatAdapter.setData(msg);
            toLastItem();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void socketError(String msg) {
        if (fl_socket_hint != null) {
            if (ImSocketClient.checkSocket()) {
                if (fl_socket_hint.getVisibility() == View.VISIBLE) {
                    fl_socket_hint.setVisibility(View.GONE);
                }
                List<ChatMessage> data = chatAdapter.getData();
                int size = chatAdapter.getData().size();
                for (int i = 0; i < size; i++) {
                    ChatMessage message = data.get(i);
                    int msgStatus = message.getMsgStatus();
                    int bodyType = message.getBodyType();
                    if (msgStatus != ChatMessage.MSG_SEND_SUCCESS) {
                        if (bodyType == ChatMessage.MSG_BODY_TYPE_TEXT) {
                            String json = GsonUtil.BeanToJson(message);
                            SocketSendJson(json);
                        }
                    }
                }
            } else {
                if (fl_socket_hint.getVisibility() == View.GONE) {
                    fl_socket_hint.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onError(String msg) {
        showToast(msg);
        smart_refresh.finishRefresh();
    }

    @Override
    public void onSuccessInfo(LoginBean bean) {
        String json = SPUtils.getInstance().getString(Constant.SPKey_USERINFO);
        LoginBean from_bean = GsonUtil.GsonToBean(json, LoginBean.class);
        if (chatAdapter == null) {
            chatAdapter = new ChatAdapter(new ArrayList<ChatMessage>(), this, from_bean.getImageUrl(), bean.getImageUrl());
        }
        rv_chat.setAdapter(chatAdapter);
        tv_chat_name.setText(bean.getUsername());
        tv_net_state.setText(NetState.getNetState(bean.getOnline()));
        chatAdapter.setOnItemListener(this);
    }

    @Override
    public void onSuccessHistory(List<com.xiang.lib.chatBean.ChatMessage> list) {
        int size = list.size();
        smart_refresh.finishRefresh();
        dismissLoading();
        if (size > 0) {
            chatAdapter.setData(list);
            layoutManager.scrollToPositionWithOffset(list.size(), 0);
        }
        if (pageNo == 1) {
            toLastItem();
        }

    }

    // 修改红包状态成功
    @Override
    public void onSuccessRedEnvelope(ChatMessage message) {
        message.setToId(message.getFromId());
        message.setFromId(SPUtils.getInstance().getString(Constant.SPKey_UID));
        message.setBodyType(ChatMessage.MSG_BODY_TYPE_RED_ENVELOPE_HINT);
        SocketSendJson(GsonUtil.BeanToJson(message));
        chatAdapter.setData(message);
        toLastItem();
    }

    @Override
    public void onSuccessNull() {
        smart_refresh.finishRefresh();
        if (pageNo > 1) {
            pageNo--;
        }
    }


    @Override
    public void onFuncPop(int height) {

    }

    @Override
    public void onFuncClose() {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (chatAdapter.getData().size() > 0) {
            pageNo++;
        }
        getPresenter().getHistory(conviction, pageNo, pageSize);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            EmoticonsKeyboardUtils.closeSoftKeyboard(this);
            finish();
        }
    }

    // item 点击事件
    @Override
    public void onClickIcon(String url) {

    }

    @Override
    public void onLongClickSend(View view) {
        // showPopWindow(view);
    }


    @Override
    public void onLongClickReceive(View view) {

    }

    @Override
    public void onClickRedEnvelope(ChatMessage message) {
        String body = message.getBody();
        if (message.getMsgStatus() != ChatMessage.STATUS_ALREADY_RECEIVED) {
            showLoading();
            getPresenter().updateEnvelope(message.getFromId(), message.getToId(), message.getPid());
            RedEnvelopeBody envelopeBody = GsonUtil.GsonToBean(body, RedEnvelopeBody.class);
            getPresenter().updateMoney(envelopeBody.getMoney());
            message.setMsgStatus(ChatMessage.STATUS_ALREADY_RECEIVED);
            chatAdapter.notifyChatMessage(message);
        } else {
            showToast("您已领取红包");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            List<LocalMedia> images;
            if (requestCode == REQUEST_CODE_RED_ENVELOPE){
                String money = data.getStringExtra("money");
                sendRedEnvelope(money);
            }
            if (requestCode == PictureConfig.CHOOSE_REQUEST){
                images = PictureSelector.obtainMultipleResult(data);
                String path = images.get(0).getPath();
                fileUpLoadManager.upLoadImageFile(path, new FileUpLoadManager.FileUpLoadCallBack() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onSuccess(String url) {
                        Log.i("image","----------" + url);
                    }

                    @Override
                    public void onProgress(int pro, int position) {
                        Log.i("image","----pro------" + pro);
                    }
                });
            }
        }
    }
}
