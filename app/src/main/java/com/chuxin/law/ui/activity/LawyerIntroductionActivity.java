package com.chuxin.law.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.audioplayer.util.StorageListUtil;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.CheckConsultModel;
import com.chuxin.law.model.LawyerIntroModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ry.db.Friend;
import com.chuxin.law.ry.ui.activity.ConversationActivity;
import com.chuxin.law.sharedpreferences.FriendsSp;
import com.chuxin.law.ui.adapter.ProductListAdapter;
import com.chuxin.law.ui.view.PresentInstructionsDialog;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.util.ImageLoderUtil;
import com.chuxin.law.util.UIUtils;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;
import java.util.Locale;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2018/1/26
 * @describe 律师介绍
 */

public class LawyerIntroductionActivity extends BaseTalkLawActivity {
    public static final String ID = "id";
    protected BackTitleView titleView;
    protected ImageView iconHead;
    protected TextView name;
    protected TextView attention;
    protected TextView yiban;
    protected TextView level;
    protected TextView haoping;
    protected TextView suc;
    protected TextView jianjieContent;
    protected RecyclerView list;
    protected TextView no;
    protected TextView zixun;
    protected TextView textBushnegsu;
    private String id;

    private ProductListAdapter adapter;
    private LawyerIntroModel.LawyerIntroData data;
    private PresentInstructionsDialog dialog;
    private String type;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_lawyer_introduction;
    }

    @Override
    public void initDatas() {
        dialog = new PresentInstructionsDialog(mContext);
        adapter = new ProductListAdapter(mContext);
        id = getIntent().getStringExtra(ID);
        if (TextUtils.isEmpty(id)) {
            id = "5";
        }
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.title_view);
        iconHead = (ImageView) findViewById(R.id.icon_head);
        name = (TextView) findViewById(R.id.name);
        attention = (TextView) findViewById(R.id.attention);
        yiban = (TextView) findViewById(R.id.yiban);
        level = (TextView) findViewById(R.id.level);
        haoping = (TextView) findViewById(R.id.haoping);
        suc = (TextView) findViewById(R.id.suc);
        jianjieContent = (TextView) findViewById(R.id.jianjie_content);
        list = (RecyclerView) findViewById(R.id.list);
        no = (TextView) findViewById(R.id.no);
        zixun = (TextView) findViewById(R.id.zixun);
        textBushnegsu = (TextView) findViewById(R.id.text_bushnegsu);

    }

    @Override
    public void initAction() {
        titleView.setTitle("律师介绍");
        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setAdapter(adapter);

        dialog.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (data == null || data.getLaw() == null) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(BuyIntroductionActivity.TYPE, type);
                bundle.putSerializable(BuyIntroductionActivity.DATA, data);
                goActivityForResult(bundle, BuyIntroductionActivity.class, CommonConstant.REQUEST_PAY_SUCCUSE);
            }
        });

        dialog.setTextTitle("温馨提示");
        dialog.setContent("尊敬的用户您好！\n1.本次咨询1小时\n2.可向律师咨询1小时");

        zixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="1";
                checkConsult();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent  = new Intent(LawyerIntroductionActivity.this, ChatActivity.class);
//                intent.putExtra("userId", "20");
//                intent.putExtra("userName", "王律师");
//                startActivity(intent);

//                if (data != null && data.getLaw() != null) {
//
////                    Intent intent = new Intent(mContext, ConversationActivity.class);
////                    intent.putExtra("targetId",data.getLaw().getUserid());
////                    intent.putExtra("title",data.getLaw().getName());
////                    mContext.startActivity(intent);
////                    data.getLaw().getUserid()
//
//                    FriendsSp.saveFriedns(mContext, new UserInfo(data.getLaw().getUserid(), data.getLaw().getName(), Uri.parse(data.getLaw().getHeadimg())));
//
////                    RongIM.getInstance().startPrivateChat(mContext, "64", data.getLaw().getName());
////                    RongIM.getInstance().startChatRoomChat(mContext,"1497704102201803131347231",true);
////                    1497704102201803131347231
//
//
////                    startChatRoomChat(mContext, data.order.order, data.getLaw().getName(), true);
//
//                    startChatRoomChat(mContext, "1497704102201803131347231", data.getLaw().getName(), true);
//
////                    public void joinChatRoom(final String chatroomId, final int defMessageCount, final RongIMClient.OperationCallback callback)
////                    RongIM.getInstance().startPrivateChat(mContext, , data.getLaw().getName());

//                }
                type="2";
                checkConsult();
            }
        });


        attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null && data.getLaw() != null) {
                    if (data.getIs_follow() == 1) {
                        delFollow(data.getLaw().getUserid());
                    } else {
                        addFollow(data.getLaw().getUserid());
                    }
                }
            }
        });

        getData();

        yiban.setText(UIUtils.getText("100", "已办"));
        level.setText(UIUtils.getText("专业级", "等级"));
        haoping.setText(UIUtils.getText("100%", "好评"));
        suc.setText(UIUtils.getText("100%", "胜率"));
        ImageLoderUtil.loadCircleImage(mContext, iconHead, "http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg", R.mipmap.icon_head_def_cir);


        Log.e("tag","StorageListUtil="+ StorageListUtil.listAvaliableStorage(this));
    }

    private void getData() {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        addNetwork(Api.getInstance().getService(ApiService.class).getLawIntro(params)
                , new Action1<LawyerIntroModel>() {
                    @Override
                    public void call(LawyerIntroModel model) {
                        Log.e("tag", "updateView=" + model.getCode() + " " + CommonConstant.NET_SUC_CODE + " " + (model.getCode() == CommonConstant.NET_SUC_CODE));
                        hideLoadDialog();
                        if (model.getCode() == CommonConstant.NET_SUC_CODE) {
                            Log.e("tag", "updateView1");
                            updateView(model.getData());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void updateView(LawyerIntroModel.LawyerIntroData data) {
        Log.e("tag", "updateView2=" + data);
        if (data == null) {
            return;
        }
        Log.e("tag", "updateView3=");
        this.data = data;
        UserModel userModel = data.getLaw();
        if (data.getIs_follow() == 0) {
            attention.setBackgroundResource(R.mipmap.icon_lawyer_follow);
            attention.setText("关注");
            attention.setTextColor(Color.parseColor("#ff8400"));
        } else {
            attention.setBackgroundResource(R.mipmap.icon_lawyer_follow_un);
            attention.setTextColor(Color.parseColor("#d7d7d7"));
            attention.setText("已关注");
        }
        if (userModel != null) {
            setNoTxt(userModel.getPrice());
            jianjieContent.setText(data.getLaw().getIntro());

            name.setText(data.getLaw().getName());
            yiban.setText(UIUtils.getText(userModel.getDonenum(), "已办"));
            level.setText(UIUtils.getText("专业级", "等级"));
            haoping.setText(UIUtils.getText(userModel.getPraise() + "%", "好评"));
            suc.setText(UIUtils.getText(userModel.getWin() + "%", "胜率"));
            ImageLoderUtil.loadCircleImage(mContext, iconHead, data.getLaw().getHeadimg(), R.mipmap.icon_head_def_cir);
        }
        adapter.refreshList(data.getList());
    }

    private void addFollow(String touserid) {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("touserid", touserid);
        addNetwork(Api.getInstance().getService(ApiService.class).addFollow(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode() == CommonConstant.NET_SUC_CODE) {
                            data.setIs_follow(1);
                            attention.setBackgroundResource(R.mipmap.icon_lawyer_follow_un);
                            attention.setTextColor(Color.parseColor("#d7d7d7"));
                            attention.setText("已关注");
                            showToast(noDataModel.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void delFollow(final String touserid) {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("touserid", touserid);
        addNetwork(Api.getInstance().getService(ApiService.class).addFollow(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        data.setIs_follow(0);
                        attention.setBackgroundResource(R.mipmap.icon_lawyer_follow);
                        attention.setText("关注");
                        attention.setTextColor(Color.parseColor("#ff8400"));
                        showToast(noDataModel.getMsg());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void checkConsult() {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("touserid", data.getLaw().getUserid());
        params.put("type",type);
        addNetwork(Api.getInstance().getService(ApiService.class).checkConsult(params)
                , new Action1<CheckConsultModel>() {
                    @Override
                    public void call(CheckConsultModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode() == CommonConstant.NET_SUC_CODE) {
                            if (noDataModel.getData() == null || StringUtil.isEmpty(noDataModel.getData().getOrder())) {
                                dialog.show();
                            } else {
                                goRongIM(noDataModel.getData().getOrder());
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void setNoTxt(String price) {
        String txt = "沟通咨询：1小时对话";
        String txt2 = "¥" + price + "/次";
        SpannableStringBuilder builder = new SpannableStringBuilder(txt + txt2);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, txt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#cb1e28")), txt.length(), txt.length() + txt2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textBushnegsu.setText(builder);
    }

    public void startChatRoomChat(Context context, String chatRoomId, String title, boolean createIfNotExist) {
        if (context != null && !TextUtils.isEmpty(chatRoomId)) {
            if (RongContext.getInstance() == null) {
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

    private void goRongIM(String id) {
        FriendsSp.saveFriedns(mContext, new UserInfo(data.getLaw().getUserid(), data.getLaw().getName(), Uri.parse(data.getLaw().getHeadimg())));
        startChatRoomChat(mContext, id, data.getLaw().getName(), true);
    }
}
