package com.chuxin.law.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.model.LawyerIntroModel;
import com.chuxin.law.sharedpreferences.FriendsSp;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.task.WeakHandler;

import java.util.Locale;

import io.rong.imkit.RongContext;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;


/**
 * @author wangcc
 * @date 2018/2/9
 * @describe 支付成功
 */

public class PaySucActivity extends BaseTalkLawActivity {
    public static final String DATA = "data";
    protected BackTitleView titleView;
    protected TextView suc;
    protected TextView timer;
    protected TextView click;
    private int second=5;
    private LawyerIntroModel.LawyerIntroData data;

    private WeakHandler handler=new WeakHandler();
    private Runnable task=new Runnable() {
        @Override
        public void run() {
            if (second==0){
                handler.removeCallbacks(task);
                goNext();
                return;
            }
            second--;
            timer.setText(getTimerTxt(second));
            handler.postDelayed(task,1000);
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_pay_suc;
    }

    @Override
    public void initDatas() {
        data = (LawyerIntroModel.LawyerIntroData) getIntent().getExtras().getSerializable(DATA);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.title_view);
        suc = (TextView) findViewById(R.id.suc);
        timer = (TextView) findViewById(R.id.timer);
        click = (TextView) findViewById(R.id.click);

    }

    @Override
    public void initAction() {
        titleView.setTitle("支付成功");

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(task);
                goNext();
            }
        });

        timer.setText(getTimerTxt(second));
        handler.postDelayed(task,1000);
    }

    private void goNext(){
        FriendsSp.saveFriedns(mContext,new UserInfo(data.getLaw().getUserid(),data.getLaw().getName(), Uri.parse(data.getLaw().getHeadimg())));
        startChatRoomChat(mContext,"1497704102201803131347231",data.getLaw().getName(),true);
        onBackPressed();
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

    private SpannableStringBuilder getTimerTxt(int second){
        SpannableStringBuilder builder=new SpannableStringBuilder(second+"秒后自动进入咨询页面...");
        int len1=(second+"秒").length();
        int len2="后自动进入咨询页面...".length()+len1;

        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#fda263")),0,len1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")),len1,len2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
}
