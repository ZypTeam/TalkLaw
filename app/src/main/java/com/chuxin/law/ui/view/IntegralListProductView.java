package com.chuxin.law.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseView;
import com.chuxin.law.model.IntegralModel;
import com.chuxin.law.ui.activity.RecommendedProductActivity;
import com.chuxin.law.ui.adapter.IntegralProductAdapter;

import cn.com.talklaw.ui.activity.AllGoodsActivity;

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
    private IntegralModel integralModel;

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
        textAll = (TextView) findViewById(R.id.text_all);
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
                if (integralModel != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("integralModel", integralModel);
                    Intent intent = new Intent(mContext, RecommendedProductActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    public void setData(IntegralModel integralModel) {
        textTitle.setText("精选推荐");
        this.integralModel = integralModel;
        if (integralModel != null && integralModel.data != null && integralModel.data.goods != null)
            adapter.refresh(integralModel.data.goods);
    }

}
