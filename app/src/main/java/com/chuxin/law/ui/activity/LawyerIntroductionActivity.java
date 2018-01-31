package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.comment.ApiService;
import com.chuxin.law.comment.CommentConstant;
import com.chuxin.law.model.LawyerIntroModel;
import com.chuxin.law.ui.adapter.ProductListAdapter;
import com.chuxin.law.ui.util.ImageLoderUtil;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import cn.com.talklaw.xh.ui.ChatActivity;
import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2018/1/26
 * @describe 律师介绍
 */

public class LawyerIntroductionActivity extends BaseTalkLawActivity {
    public static final String ID="id";
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

    @Override
    public int getLayoutResId() {
        return R.layout.activity_lawyer_introduction;
    }

    @Override
    public void initDatas() {
        adapter = new ProductListAdapter(mContext);
        id=getIntent().getStringExtra(ID);
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

        zixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(LawyerIntroductionActivity.this, ChatActivity.class);
                intent.putExtra("userId", "20");
                intent.putExtra("userName", "王律师");
                startActivity(intent);
            }
        });

        textBushnegsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(LawyerIntroductionActivity.this, ChatActivity.class);
                intent.putExtra("userId", "20");
                intent.putExtra("userName", "王律师");
                startActivity(intent);
            }
        });

        attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data!=null){
                    if (data.getLaw()!=null){

                    }
                }
            }
        });

        getData();
    }

    private void getData(){
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("id",id);
        addNetwork(Api.getInstance().getService(ApiService.class).getLawIntro(params)
                , new Action1<LawyerIntroModel>() {
                    @Override
                    public void call(LawyerIntroModel model) {
                        hideLoadDialog();
                        if (model.getCode()== CommentConstant.NET_SUC_CODE){
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

    private void updateView(LawyerIntroModel.LawyerIntroData data){
        if (data==null){
            return;
        }
        this.data=data;
        if (data.getLaw().getName()!=null) {
            name.setText(data.getLaw().getName());
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
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }
}
