package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.graphics.Color;
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
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.LawyerIntroModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ry.ui.activity.ConversationActivity;
import com.chuxin.law.ui.adapter.ProductListAdapter;
import com.chuxin.law.ui.view.PresentInstructionsDialog;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.util.ImageLoderUtil;
import com.chuxin.law.util.UIUtils;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import io.rong.imkit.RongIM;
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
                Bundle bundle = new Bundle();
                bundle.putInt(BuyLawyerActivity.TYPE, 0);
                if (data == null || data.getLaw() == null) {
                    bundle.putString(BuyLawyerActivity.PRICE, "200");
                } else {
                    bundle.putString(BuyLawyerActivity.PRICE, data.getLaw().getPrice());
                }
                goActivityForResult(bundle, BuyLawyerActivity.class, CommonConstant.REQUEST_PAY_SUCCUSE);
            }
        });

        dialog.setTextTitle("温馨提示");
        dialog.setContent("尊敬的用户您好！\n1.本次咨询1小时\n2.可向律师咨询1小时");

        zixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent  = new Intent(LawyerIntroductionActivity.this, ChatActivity.class);
//                intent.putExtra("userId", "20");
//                intent.putExtra("userName", "王律师");
//                startActivity(intent);

                if (data != null && data.getLaw() != null) {

//                    Intent intent = new Intent(mContext, ConversationActivity.class);
//                    intent.putExtra("targetId",data.getLaw().getUserid());
//                    intent.putExtra("title",data.getLaw().getName());
//                    mContext.startActivity(intent);
//                    data.getLaw().getUserid()
                    RongIM.getInstance().startPrivateChat(mContext, "64", data.getLaw().getName());
                }
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
    }

    private void getData() {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        addNetwork(Api.getInstance().getService(ApiService.class).getLawIntro(params)
                , new Action1<LawyerIntroModel>() {
                    @Override
                    public void call(LawyerIntroModel model) {
                        Log.e("tag","updateView="+model.getCode()+ " "+CommonConstant.NET_SUC_CODE+" "+(model.getCode()==CommonConstant.NET_SUC_CODE) );
                        hideLoadDialog();
                        if (model.getCode() == CommonConstant.NET_SUC_CODE) {
                            Log.e("tag","updateView1");
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
        Log.e("tag","updateView2="+data);
        if (data == null) {
            return;
        }
        Log.e("tag","updateView3=");
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
            ImageLoderUtil.loadCircleImage(mContext, iconHead, "http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg", R.mipmap.icon_head_def_cir);
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

    private void setNoTxt(String price) {
        String txt = "沟通咨询：1小时对话";
        String txt2 = "¥" + price + "/次";
        SpannableStringBuilder builder = new SpannableStringBuilder(txt + txt2);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, txt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#cb1e28")), txt.length(), txt.length() + txt2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textBushnegsu.setText(builder);
    }
}
