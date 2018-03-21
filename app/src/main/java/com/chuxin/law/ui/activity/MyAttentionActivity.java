package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;

import com.chuxin.law.common.AdapterCallback;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.MyAttentionListModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;

import java.util.HashMap;

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
    public static final String FOLLOW_COUNT="follow_count";
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

        titleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
                onBackPressed();
            }
        });

        attentionList.setAdapter(adapter);
        attentionList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getFollowList(false,true);
            }

            @Override
            public void onLoadMore() {
                getFollowList(false,false);
            }
        });

        getFollowList(true,true);
        adapter.setCallback(new AdapterCallback() {
            @Override
            public void callback(Object model, int position) {
                delFollow((UserModel) model);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            back();
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back(){
        Intent intent=new Intent();
        intent.putExtra(FOLLOW_COUNT,adapter.getItemCount()+"");
        setResult(RESULT_OK,intent);
    }

    private void delFollow(final UserModel model){
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("touserid",model.getId());
        addNetwork(Api.getInstance().getService(ApiService.class).delFollow(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode()==CommonConstant.NET_SUC_CODE) {
                            adapter.remove(model);
                        }else {
                            showToast(noDataModel.getMessage());
                        }
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
        params.put("size", CommonConstant.LIST_PAGE_SIZE);
        params.put("page",(isRefresh?1:page+1)+"");
        addNetwork(Api.getInstance().getService(ApiService.class).getFollowList(params)
                , new Action1<MyAttentionListModel>() {
                    @Override
                    public void call(MyAttentionListModel myAttentionListModel) {
                        hideLoadDialog();
                        attentionList.refreshComplete();
                        attentionList.loadMoreComplete();
                       if (myAttentionListModel.getCode()== CommonConstant.NET_SUC_CODE
                               &&myAttentionListModel.getData()!=null){
                           if (isRefresh){
                               page=1;
                               adapter.refreshList(myAttentionListModel.getData());
                           }else {
                               adapter.addList(myAttentionListModel.getData());
                               page++;
                           }
                           if (adapter.getItemCount()>myAttentionListModel.total){
                               attentionList.setLoadingMoreEnabled(true);
                           }else {
                               attentionList.setLoadingMoreEnabled(false);
                           }
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
