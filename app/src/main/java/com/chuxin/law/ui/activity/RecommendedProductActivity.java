package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.HotListData;
import com.chuxin.law.model.IntegralModel;
import com.chuxin.law.ui.adapter.OpinionAdapter;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import cn.com.talklaw.model.GoodsDataModel;
import cn.com.talklaw.ui.adapter.GoodsListAdapter;
import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:18/2/823:47
 * @Email zyp@jusfoun.com
 * @Description ${热门企业全部}
 */
public class RecommendedProductActivity extends BaseTalkLawActivity {
    protected BackTitleView backTitleView;
    private int page=1;

    protected XRecyclerView recyclerview;
    private GoodsListAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_hot_product;
    }

    @Override
    public void initDatas() {
        adapter = new GoodsListAdapter(mContext);

    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        recyclerview = (XRecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public void initAction() {
        backTitleView.setTitle("精选推荐");
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(adapter);
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        recyclerview.setLoadingMoreEnabled(false);
        delMsg(page);
    }


    private void delMsg(int page) {
        HashMap<String,String> params = new HashMap<>();
        params.put("size", CommonConstant.LIST_PAGE_SIZE);
        params.put("page",page+"");
        addNetwork(Api.getInstance().getService(ApiService.class).getTJGoods(params)
                , new Action1<GoodsDataModel>() {
                    @Override
                    public void call(GoodsDataModel model) {
                        Log.e("tag","delMsgdelMsgdelMsg2");
                        hideLoadDialog();
                        recyclerview.refreshComplete();
                        recyclerview.loadMoreComplete();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            adapter.refreshList(model.data);

                            if(adapter.getItemCount()>=model.total){
                                recyclerview.setLoadingMoreEnabled(false);
                            }else{
                                recyclerview.setLoadingMoreEnabled(true);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("tag","delMsgdelMsgdelMsg3="+throwable);
                        hideLoadDialog();
                    }
                });
    }
}
