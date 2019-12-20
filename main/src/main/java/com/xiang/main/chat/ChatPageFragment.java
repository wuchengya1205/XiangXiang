package com.xiang.main.chat;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.xiangxiang.im.ChatMessage;
import com.lib.xiangxiang.im.GsonUtil;
import com.lib.xiangxiang.im.ImSendMessageUtils;
import com.lib.xiangxiang.im.SocketManager;
import com.xiang.lib.base.fr.BaseMvpFragment;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.utils.SPUtils;
import com.xiang.main.R;
import com.xiang.main.chat.contract.ChatPageContract;
import com.xiang.main.chat.presenter.ChatPagePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.Socket;

/**
 * author : wuchengya
 * e-mail : wucy1205@yeah.net
 * date   : 2019/10/24
 * time   :15:06
 * desc   :ohuo
 * version: 1.0
 */
public class ChatPageFragment extends BaseMvpFragment<ChatPageContract.IPresenter> implements ChatPageContract.IView, View.OnClickListener {

    private String TAG = "socket";
    private EditText ed_input;
    private Button btn_send;
    private TextView tv_context;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        ed_input = view.findViewById(R.id.ed_input);
        btn_send = view.findViewById(R.id.btn_send);
        tv_context = view.findViewById(R.id.tv_context);
    }

    @Override
    public void initData() {
        super.initData();
        btn_send.setOnClickListener(this);
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

        if (view.getId() == R.id.btn_send) {
            final String s = ed_input.getText().toString().trim();
            ChatMessage message = new ChatMessage();
            message.setFromId(Constant.TO_ID);
            message.setToId(Constant.FROM_ID);
            message.setBodyType(ChatMessage.MSG_BODY_TYPE_TEXT);
            message.setBody(s);
            message.setMsgStatus(ChatMessage.MSG_SEND_LOADING);
            message.setPid(ImSendMessageUtils.getPid());
            String json = GsonUtil.BeanToJson(message);
            SocketManager.sendMsgSocket(getContext(),json, new SocketManager.SendMsgCallBack() {
                @Override
                public void call(String msg) {
                    ChatMessage chatMessage = GsonUtil.GsonToBean(msg, ChatMessage.class);
                    int status = chatMessage.getMsgStatus();
                    if (status == ChatMessage.MSG_SEND_SUCCESS){
                        tv_context.setText(tv_context.getText().toString().trim()+"\n"+"我:"+s);
                        ed_input.setText("");
                    }
                }
            });
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receiverMsg(ChatMessage msg) {
        String body = msg.getBody();
        tv_context.setText(tv_context.getText().toString().trim()+"\n"+"对方:"+body);
    }

}
