package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;

import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.model.ProductModel;
import com.chuxin.law.ui.adapter.ProductListAdapter;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.chuxin.law.R;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe 已经购买
 */

public class AlreadyPurchaseActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected XRecyclerView list;
    private ProductListAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_already_purchase;
    }

    @Override
    public void initDatas() {
        adapter=new ProductListAdapter(mContext);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        list = (XRecyclerView) findViewById(R.id.list);

        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setAdapter(adapter);
    }

    @Override
    public void initAction() {

        titleView.setTitle("我的关注");
        List<ProductModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductModel model = new ProductModel();
            list.add(model);
        }
        adapter.refreshList(list);
    }
}
