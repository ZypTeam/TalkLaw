package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.model.ArrondiProductModel;
import com.chuxin.law.model.ProductModel;
import com.chuxin.law.ui.adapter.ProductListAdapter;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author wangcc
 * @date 2017/12/24
 * @describe 专区 产品 list
 */

public class ProductiListActivity extends BaseTalkLawActivity {

    public static final String PRODUCT_MODEL="productmodel";
    protected BackTitleView titleView;
    protected RecyclerView list;
    private ProductListAdapter adapter;
    private ArrondiProductModel model;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_product_list;
    }

    @Override
    public void initDatas() {
        adapter = new ProductListAdapter(mContext);
        model= (ArrondiProductModel) getIntent().getExtras().getSerializable(PRODUCT_MODEL);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        list = (RecyclerView) findViewById(R.id.list);

    }

    @Override
    public void initAction() {

        if (model!=null){
            titleView.setTitle(model.getName()+"案例");
        }
        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setAdapter(adapter);

        List<ProductModel> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductModel model=new ProductModel();
            list.add(model);
        }
        adapter.refreshList(list);
    }
}
