package cn.com.talklaw.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.model.ExchangeRecordsItemModel;
import cn.com.talklaw.ui.adapter.ExchangeRecordsAdapter;
import cn.com.talklaw.ui.widget.BackTitleView;

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
