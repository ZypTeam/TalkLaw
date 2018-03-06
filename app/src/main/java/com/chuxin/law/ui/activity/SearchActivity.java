package com.chuxin.law.ui.activity;

import android.widget.EditText;

import com.chuxin.law.base.BaseTalkLawActivity;

import com.chuxin.law.R;

import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.ProductsModel;
import com.chuxin.law.ui.adapter.ProductListAdapter;
import com.chuxin.law.ui.view.SearchGuideView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1820:50
 * @Email zyp@jusfoun.com
 * @Description ${搜索页面}
 */
public class SearchActivity extends BaseTalkLawActivity {
    protected SearchGuideView viewSearchGuide;
    private ProductListAdapter adapter;
    private XRecyclerView result;
    private int page;
    private String keyword;
    private EditText editSearch;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    public void initDatas() {
        adapter=new ProductListAdapter(mContext);
    }

    @Override
    public void initView() {
        viewSearchGuide = (SearchGuideView) findViewById(R.id.view_search_guide);
        result=findViewById(R.id.result);

    }

    @Override
    public void initAction() {
        viewSearchGuide.setHistoryData();
        viewSearchGuide.setHotSearchData();

        result.setPullRefreshEnabled(true);
        result.setLoadingMoreEnabled(false);
        result.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                search(keyword,true);
            }

            @Override
            public void onLoadMore() {
                search(keyword,false);
            }
        });
    }

    private void search(String keyword, final boolean isRefrsh){
        HashMap<String,String> params=new HashMap<>();
        params.put("keyword",keyword);
        params.put("page",isRefrsh?"1":(page+1)+"");
        params.put("size",CommonConstant.LIST_PAGE_SIZE);
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).search(params)
                , new Action1<ProductsModel>() {
                    @Override
                    public void call(ProductsModel productsModel) {
                        hideLoadDialog();
                        if (productsModel.getCode()== CommonConstant.NET_SUC_CODE){
                            if (isRefrsh){
                                page=1;
                            }else {
                                page++;
                            }
                            adapter.refreshList(productsModel.getData());
                            return;
                        }
                        showToast(productsModel.getMsg());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }
}
