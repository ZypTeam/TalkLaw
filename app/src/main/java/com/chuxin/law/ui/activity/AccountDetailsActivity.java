package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.HotListData;
import com.chuxin.law.model.PayValidateModel;
import com.chuxin.law.ui.adapter.AccountDetailAdapter;
import com.chuxin.law.ui.viewholder.AccountDetailModel;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:18/2/720:14
 * @Email zyp@jusfoun.com
 * @Description ${账户明细}
 */
public class AccountDetailsActivity extends BaseTalkLawActivity {
    protected BackTitleView backTitleView;
    protected XRecyclerView recyclerView;

    private AccountDetailAdapter adapter;
    private int page=1;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_account_details;
    }

    @Override
    public void initDatas() {
        adapter = new AccountDetailAdapter(mContext);
    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public void initAction() {

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        backTitleView.setTitle("账户明细");

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                delMsg(1);
            }

            @Override
            public void onLoadMore() {
                page++;
                delMsg(1);
            }
        });
        delMsg(page);
    }

    private void delMsg(final int page) {
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("size", CommonConstant.LIST_PAGE_SIZE);
        params.put("page",page+"");
        addNetwork(Api.getInstance().getService(ApiService.class).getAccountDetailList(params)
                , new Action1<AccountDetailModel>() {
                    @Override
                    public void call(AccountDetailModel model) {
                        hideLoadDialog();
                        recyclerView.refreshComplete();
                        recyclerView.loadMoreComplete();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            if (model.data != null) {
                                if (page==1){
                                    adapter.refreshList(model.data);
                                }else {
                                    adapter.addList(model.data);
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
