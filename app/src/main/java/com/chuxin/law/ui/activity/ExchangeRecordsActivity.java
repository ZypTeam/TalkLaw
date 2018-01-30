package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.adapter.ExchangeRecordsAdapter;
import com.chuxin.law.ui.widget.BackTitleView;

import java.util.ArrayList;
import java.util.List;

import com.chuxin.law.R;

import com.chuxin.law.model.ExchangeRecordsItemModel;

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
        List<ExchangeRecordsItemModel>list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new ExchangeRecordsItemModel());
        }
        adapter.refreshList(list);
    }
}
