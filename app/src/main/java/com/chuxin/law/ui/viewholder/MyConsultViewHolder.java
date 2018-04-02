package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.MyConsultModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.sharedpreferences.FriendsSp;
import com.chuxin.law.util.ImageLoderUtil;

import java.util.List;
import java.util.Locale;

import io.rong.imkit.RongContext;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;

/**
 * @author wangcc
 * @date 2018/1/18
 * @describe
 */

public class MyConsultViewHolder extends BaseViewHolder<MyConsultModel> {
    private ImageView iconHead;
    private TextView name;
    private TextView time;
    private TextView content;
    private TextView result;

    public MyConsultViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        iconHead = itemView.findViewById(R.id.icon_head);
        name = itemView.findViewById(R.id.name);
        time = itemView.findViewById(R.id.time);
        content = itemView.findViewById(R.id.content);
        result = itemView.findViewById(R.id.result);
    }

    @Override
    public void update(final MyConsultModel model) {
        if (model == null) {
            return;
        }

        time.setText(model.getCreatetime());
        result.setText(model.getState());

        if (model.getUser() != null) {
            name.setText(model.getUser().getName());
            ImageLoderUtil.loadCircleImage(mContext, iconHead, model.getUser().getHeadimg(), R.mipmap.icon_head_def_cir);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model != null && model.getUser() != null) {
                    goNext(model.getUser(), model.getId() + "");
//                    goNext(model.getUser(),"195");
                }
            }
        });


//        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
//            @Override
//            public void onSuccess(List<Conversation> conversations) {
//                Log.e("tag","getConversationList="+conversations);
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        });



        RongIMClient.getInstance().getLatestMessages(Conversation.ConversationType.GROUP, model.getId() + "", 10, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                if (messages != null && messages.size() > 0) {
                    MessageContent messageContent = messages.get(0).getContent();
                    if ("RC:TxtMsg".equals(messages.get(0).getObjectName())) {
                        content.setText(((TextMessage) (messageContent)).getContent());
                    } else if ("RC:ImgMsg".equals(messages.get(0).getObjectName())) {
                        content.setText("[图片]");
                    } else if ("APP:MyPay".equals(messages.get(0).getObjectName())) {
                        content.setText("[保证金]");
                    }
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    private void goNext(UserModel userModel, String rongId) {
        FriendsSp.saveFriedns(mContext, userModel);
        startChatRoomChat(mContext, rongId, userModel.getName(), userModel.getUserid(), true);


    }

    public void startChatRoomChat(Context context, String chatRoomId, String title, String touserid, boolean createIfNotExist) {
        if (context != null && !TextUtils.isEmpty(chatRoomId)) {
            if (RongContext.getInstance() == null) {
                throw new ExceptionInInitializerError("RongCloud SDK not init");
            } else {
                Log.e("tag", "touserid1=" + touserid);
                Uri uri = Uri.parse("rong://" + context.getApplicationInfo().packageName).buildUpon().appendPath("conversation")
                        .appendPath(Conversation.ConversationType.GROUP.getName().toLowerCase(Locale.US))
                        .appendQueryParameter("targetId", chatRoomId)
                        .appendQueryParameter("title", title)
                        .appendQueryParameter("touserid", touserid).build();
                Intent intent = new Intent("android.intent.action.VIEW", uri);
                intent.putExtra("createIfNotExist", createIfNotExist);
                context.startActivity(intent);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
