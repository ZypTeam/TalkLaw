package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;

import com.chuxin.law.comment.AdapterDelCallback;
import com.chuxin.law.comment.ApiService;
import com.chuxin.law.comment.CommentConstant;
import com.chuxin.law.model.MyAttentionListModel;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.model.MyAttentionModel;
import com.chuxin.law.ui.adapter.MyAttentionAdapter;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe 我的关注
 */

public class MyAttentionActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected XRecyclerView attentionList;
    private MyAttentionAdapter adapter;
    private int page=1;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_attention;
    }

    @Override
    public void initDatas() {

        adapter=new MyAttentionAdapter(mContext);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        attentionList = (XRecyclerView) findViewById(R.id.attention_list);

        attentionList.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void initAction() {

        titleView.setTitle("我的关注");

        attentionList.setAdapter(adapter);

        getFollowList(true,true);
        adapter.setCallback(new AdapterDelCallback() {
            @Override
            public void del(Object model, int position) {
                delFollow((MyAttentionModel) model);
            }
        });
    }


    private void delFollow(final MyAttentionModel model){
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("touserid",model.getId());
        addNetwork(Api.getInstance().getService(ApiService.class).addFollow(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        adapter.remove(model);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void getFollowList(boolean isShow, final boolean isRefresh){
        if (isShow){
            showLoadDialog();
        }
        HashMap<String,String> params=new HashMap<>();
        params.put("size", CommentConstant.LIST_PAGE_SIZE);
        params.put("page",(isRefresh?1:page+1)+"");
        addNetwork(Api.getInstance().getService(ApiService.class).getFollowList(params)
                , new Action1<MyAttentionListModel>() {
                    @Override
                    public void call(MyAttentionListModel myAttentionListModel) {
                        hideLoadDialog();
                        attentionList.refreshComplete();
                        attentionList.loadMoreComplete();
                       if (myAttentionListModel.getCode()==CommentConstant.NET_SUC_CODE
                               &&myAttentionListModel.getData()!=null){
                           if (isRefresh){
                               adapter.refreshList(myAttentionListModel.getData());
                           }else {
                               adapter.addList(myAttentionListModel.getData());
                           }
                           page++;
                       }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        attentionList.refreshComplete();
                        attentionList.loadMoreComplete();
                    }
                });
    }
}
