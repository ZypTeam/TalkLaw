package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;

import java.util.HashMap;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.ArrondiProductModel;
import com.chuxin.law.model.ProductsModel;
import com.chuxin.law.ui.adapter.ProductListAdapter;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.jusfoun.baselibrary.net.Api;

import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2017/12/24
 * @describe 专区 产品 list
 */

public class ProductiListActivity extends BaseTalkLawActivity {

    public static final String PRODUCT_MODEL="productmodel";
    protected BackTitleView titleView;
    protected XRecyclerView list;
    private ProductListAdapter adapter;
    private ArrondiProductModel model;

    private int page=1;

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
        list = (XRecyclerView) findViewById(R.id.list);

    }

    @Override
    public void initAction() {

        if (model!=null){
            titleView.setTitle(model.getName()+"案例");
        }
        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setAdapter(adapter);

        list.setPullRefreshEnabled(true);
        list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData(false,true);
            }

            @Override
            public void onLoadMore() {
                getData(false,false);
            }
        });


        getData(true,true);

        rxManage.on(CommonConstant.EVNET_LIKE, new Action1<Object>() {
            @Override
            public void call(Object o) {
                getData(false,true);
            }
        });
    }

    private void getData(boolean isShow, final boolean isRefresh){
        if (isShow){
            showLoadDialog();
        }
        HashMap<String,String> params=new HashMap<>();
        params.put("catid",model.getId());
        params.put("size", CommonConstant.LIST_PAGE_SIZE);
        params.put("page",(isRefresh?1:page+1)+"");
        addNetwork(Api.getInstance().getService(ApiService.class).getProductList(params)
                , new Action1<ProductsModel>() {
                    @Override
                    public void call(ProductsModel productListModel) {
                        hideLoadDialog();
                        list.refreshComplete();
                        list.loadMoreComplete();
                        if (productListModel.getCode()== CommonConstant.NET_SUC_CODE){
                            if (isRefresh){
                                page=1;
                                adapter.refreshList(productListModel.getData());
                            }else {
                                adapter.addList(productListModel.getData());
                                page++;
                            }

                            if (adapter.getItemCount()>productListModel.total){
                                list.setLoadingMoreEnabled(true);
                            }else {
                                list.setLoadingMoreEnabled(false);
                            }

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        list.refreshComplete();
                        list.loadMoreComplete();
                    }
                });
    }
}
