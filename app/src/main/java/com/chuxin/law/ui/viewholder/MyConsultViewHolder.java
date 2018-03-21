package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.MyConsultModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.sharedpreferences.FriendsSp;
import com.chuxin.law.util.ImageLoderUtil;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;

import com.chuxin.law.R;

import java.util.Locale;

import io.rong.imkit.RongContext;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

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
        iconHead=itemView.findViewById(R.id.icon_head);
        name=itemView.findViewById(R.id.name);
        time=itemView.findViewById(R.id.time);
        content=itemView.findViewById(R.id.content);
        result=itemView.findViewById(R.id.result);
    }

    @Override
    public void update(final MyConsultModel model) {
        if (model==null){
            return;
        }

        time.setText(model.getCreatetime());
        result.setText(model.getState());

        if (model.getUser()!=null){
            name.setText(model.getUser().getName());
            ImageLoderUtil.loadCircleImage(mContext,iconHead,model.getUser().getHeadimg(),R.mipmap.icon_head_def_cir);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model!=null&&model.getUser()!=null){
//                    goNext(model.getUser(),model.getSession());
                    goNext(model.getUser(),"166");
                }
            }
        });
    }

    private void goNext(UserModel userModel,String rongId){
        FriendsSp.saveFriedns(mContext,new UserInfo(userModel.getUserid(),userModel.getName(), Uri.parse(userModel.getHeadimg())));
        startChatRoomChat(mContext,rongId,userModel.getName(),true);
    }

    public void startChatRoomChat(Context context, String chatRoomId, String title, boolean createIfNotExist) {
        if(context != null && !TextUtils.isEmpty(chatRoomId)) {
            if(RongContext.getInstance() == null) {
                throw new ExceptionInInitializerError("RongCloud SDK not init");
            } else {
                Uri uri = Uri.parse("rong://" + context.getApplicationInfo().packageName).buildUpon().appendPath("conversation").appendPath(Conversation.ConversationType.CHATROOM.getName().toLowerCase(Locale.US)).appendQueryParameter("targetId", chatRoomId).appendQueryParameter("title", title).build();
                Intent intent = new Intent("android.intent.action.VIEW", uri);
                intent.putExtra("createIfNotExist", createIfNotExist);
                context.startActivity(intent);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
