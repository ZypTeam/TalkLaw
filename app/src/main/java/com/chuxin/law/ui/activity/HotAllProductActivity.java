package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.HotListData;
import com.chuxin.law.model.StatementListModel;
import com.chuxin.law.ui.adapter.OpinionAdapter;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:18/2/823:47
 * @Email zyp@jusfoun.com
 * @Description ${热门企业全部}
 */
public class HotAllProductActivity extends BaseTalkLawActivity {
    protected BackTitleView backTitleView;
    protected XRecyclerView recyclerView;
    protected OpinionAdapter adapter;
    private int page=1;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_hot_product;
    }

    @Override
    public void initDatas() {
        adapter = new OpinionAdapter(mContext);

    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public void initAction() {
        backTitleView.setTitle("热门产品");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                delMsg(1);
            }

            @Override
            public void onLoadMore() {
                page++;
                delMsg(page);
            }
        });
        delMsg(1);
    }

    private void delMsg(final int page) {
        HashMap<String,String> params=new HashMap<>();
        params.put("size", CommonConstant.LIST_PAGE_SIZE);
        params.put("page",page+"");
        addNetwork(Api.getInstance().getService(ApiService.class).getHotList(params)
                , new Action1<HotListData>() {
                    @Override
                    public void call(HotListData model) {
                        hideLoadDialog();
                        recyclerView.refreshComplete();
                        recyclerView.loadMoreComplete();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            if (model.data != null) {
                                if (page==1){
                                    adapter.refresh(model.data,1);
                                }else {
                                    adapter.addData(model.data,1);
                                }
                            }
                        }

                        if(adapter.getItemCount()<model.total){
                            recyclerView.setLoadingMoreEnabled(true);
                        }else{
                            recyclerView.setLoadingMoreEnabled(false);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        recyclerView.refreshComplete();
                        recyclerView.loadMoreComplete();
                    }
                });
    }
}
