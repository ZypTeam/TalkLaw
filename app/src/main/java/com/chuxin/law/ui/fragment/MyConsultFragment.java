package com.chuxin.law.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chuxin.law.comment.CommentConstant;
import com.chuxin.law.model.MyConsultModel;
import com.chuxin.law.ui.util.MyConsultPagerUtils;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.ui.adapter.MyConsultListAdapter;

/**
 * @author wangcc
 * @date 2018/1/18
 * @describe 我的咨询 fragment
 */

public class MyConsultFragment extends BaseTalkLawFragment {
    protected XRecyclerView list;
    private int type;
    private String size= CommentConstant.LIST_PAGE_SIZE;
    private int page;

    private MyConsultListAdapter adapter;

    public static MyConsultFragment getInstance(Bundle args){
        MyConsultFragment fragment=new MyConsultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my_consult;
    }

    @Override
    public void initDatas() {
        if (getArguments()!=null) {
            type = getArguments().getInt(MyConsultPagerUtils.TYPE);
        }

        adapter=new MyConsultListAdapter(mContext);
    }

    @Override
    public void initView(View rootView) {
        list = (XRecyclerView) rootView.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void initAction() {

        list.setLoadingMoreEnabled(true);
        list.setPullRefreshEnabled(true);
        list.setAdapter(adapter);
        list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getDate(true,false);
            }

            @Override
            public void onLoadMore() {
                getDate(false,false);
            }
        });

    }

    @Override
    protected void refreshData() {
        getDate(true,true);
    }

    private void getDate(boolean isRefresh,boolean isShowLoading){
        /*if (isShowLoading){
            showLoadDialog();
        }*/
        Map<String,String> params=new HashMap<>();
        params.put("size",size);
        params.put("page",(isRefresh?"1":page+1)+"");

        List<MyConsultModel> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new MyConsultModel());
        }
        adapter.refreshList(list);

    }
}
