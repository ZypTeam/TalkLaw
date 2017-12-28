package cn.com.talklaw.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.model.ExchangeRecordsItemModel;
import cn.com.talklaw.ui.adapter.ExchangeDetailsAdapter;
import cn.com.talklaw.ui.widget.BackTitleView;
import cn.com.talklaw.ui.widget.xRecyclerView.XRecyclerView;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2811:23
 * @Email zyp@jusfoun.com
 * @Description ${兑换详情页面}
 */
public class RedeemDetailsActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected TextView textNum;
    protected TextView textState;
    protected RelativeLayout layoutTitle;
    protected View viewLine;
    protected XRecyclerView list;
    protected Button buy;

    private ExchangeDetailsAdapter adapter;
    private int listDy=0;
    private int disY;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_redeem_details;
    }

    @Override
    public void initDatas() {
        adapter = new ExchangeDetailsAdapter(mContext);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        textNum = (TextView) findViewById(R.id.text_num);
        textState = (TextView) findViewById(R.id.text_state);
        layoutTitle = (RelativeLayout) findViewById(R.id.layout_title);
        viewLine = (View) findViewById(R.id.view_line);
        list = (XRecyclerView) findViewById(R.id.list);
        buy = (Button) findViewById(R.id.buy);

    }

    @Override
    public void initAction() {
//        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                listDy+=dy;
//                if (listDy<0){
//                    listDy=0;
//                }
//                float alpha=listDy/(float)disY;
//                titleView.setAlpha(alpha);
//            }
//        });


        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setAdapter(adapter);

        List<ExchangeRecordsItemModel>list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new ExchangeRecordsItemModel());
        }
        adapter.refreshList(list);

        textNum.setText("订单号：123456789000");
        textState.setText("交易成功");
        titleView.setTitle("兑换订单详情");
    }
}
