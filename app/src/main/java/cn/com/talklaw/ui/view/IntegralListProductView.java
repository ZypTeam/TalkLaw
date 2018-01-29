package cn.com.talklaw.ui.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseView;
import cn.com.talklaw.model.IntegralModel;
import cn.com.talklaw.ui.activity.AllGoodsActivity;
import cn.com.talklaw.ui.adapter.IntegralProductAdapter;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2509:26
 * @Email zyp@jusfoun.com
 * @Description ${积分产品 列表}
 */
public class IntegralListProductView extends BaseView {
    protected RecyclerView recyclerView;
    protected IntegralProductAdapter adapter;
    protected TextView textTitle;
    protected TextView textAll;
    protected RelativeLayout layoutTitle;
    private LinearLayoutManager layoutManager;

    public IntegralListProductView(Context context) {
        super(context);
    }

    public IntegralListProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IntegralListProductView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {
        adapter = new IntegralProductAdapter(mContext);
    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_product_integral_list, this, true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        textTitle = (TextView) findViewById(R.id.text_title);
        textAll = (TextView)findViewById(R.id.text_all);
    }

    @Override
    protected void initActions() {
        layoutManager = new GridLayoutManager(mContext, 2) {
            @Override
            public boolean canScrollVertically() {
                // 直接禁止垂直滑动
                return false;
            }
        };

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        textAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(mContext, AllGoodsActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    public void setData(List<IntegralModel.GoodsItemModel> goods) {
        textTitle.setText("精选推荐");
        adapter.refresh(goods);
    }

}
