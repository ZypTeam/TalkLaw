package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.comment.ApiService;
import com.chuxin.law.model.ProductListModel;
import com.chuxin.law.ui.adapter.ExchangeRecordsAdapter;
import com.chuxin.law.ui.widget.BackTitleView;

import java.util.ArrayList;
import java.util.List;

import com.chuxin.law.R;

import com.chuxin.law.model.ExchangeRecordsDataModel;
import com.jusfoun.baselibrary.net.Api;

import rx.functions.Action1;

import static com.chuxin.law.comment.CommentConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2811:23
 * @Email zyp@jusfoun.com
 * @Description ${兑换记录}
 */
public class ExchangeRecordsActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected RecyclerView recyclerView;
    private ExchangeRecordsAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_exchange_records;
    }

    @Override
    public void initDatas() {
        adapter = new ExchangeRecordsAdapter(mContext);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public void initAction() {
        titleView.setTitle("兑换记录");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        delMsg();
    }

    private void delMsg() {
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).getExchangeRecords()
                , new Action1<ExchangeRecordsDataModel>() {
                    @Override
                    public void call(ExchangeRecordsDataModel model) {
                        hideLoadDialog();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            adapter.refreshList(model.data);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }
}
