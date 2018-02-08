package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.ProductsModel;
import com.chuxin.law.ui.adapter.ProductListAdapter;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe 已经购买
 */

public class AlreadyPurchaseActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected XRecyclerView list;
    private ProductListAdapter adapter;
    private int page=1;

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
        titleView.setTitle("我的购买");
        list.setLoadingMoreEnabled(false);
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

    }

    private void getData(boolean isShow, final boolean isRefresh){
        if (isShow){
            showLoadDialog();
        }
        HashMap<String,String> params=new HashMap<>();
        params.put("size", CommonConstant.LIST_PAGE_SIZE);
        params.put("page",(isRefresh?1:page+1)+"");
        addNetwork(Api.getInstance().getService(ApiService.class).myBuyList(params)
                , new Action1<ProductsModel>() {
                    @Override
                    public void call(ProductsModel productsModel) {
                        hideLoadDialog();
                        list.refreshComplete();
                        list.loadMoreComplete();
                        if (productsModel.getCode()== CommonConstant.NET_SUC_CODE
                                &&productsModel.getData()!=null){
                            if (isRefresh){
                                adapter.refreshList(productsModel.getData());
                            }else {
                                adapter.addList(productsModel.getData());
                            }
                            page++;
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
