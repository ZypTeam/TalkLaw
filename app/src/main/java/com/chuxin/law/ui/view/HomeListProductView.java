package com.chuxin.law.ui.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chuxin.law.model.ProductItemModel;

import java.util.List;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseView;
import com.chuxin.law.ui.activity.HotAllProductActivity;
import com.chuxin.law.ui.adapter.OpinionAdapter;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2509:26
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class HomeListProductView extends BaseView {
    protected RecyclerView recyclerView;
    protected OpinionAdapter adapter;
    protected TextView textTitle,allText;
    protected LimitedTimeView viewLimited;
    private LinearLayoutManager layoutManager;

    public HomeListProductView(Context context) {
        super(context);
    }

    public HomeListProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeListProductView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {
        adapter = new OpinionAdapter(mContext);
    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_product_home_list, this, true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        textTitle = (TextView) findViewById(R.id.text_title);
        viewLimited = (LimitedTimeView)findViewById(R.id.view_limited);
        allText = (TextView)findViewById(R.id.text_all);
    }

    @Override
    protected void initActions() {
        layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                // 直接禁止垂直滑动
                return false;
            }
        };

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    public void setData(List<ProductItemModel> list, int type, long time) {
        // type 1 热门产品  2 限时免费
        adapter.refresh(list, type);
        if (type == 1) {
            textTitle.setText("热门产品");
            allText.setVisibility(VISIBLE);
            viewLimited.setVisibility(GONE);
            allText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, HotAllProductActivity.class);
                    mContext.startActivity(intent);
                }
            });
        } else if (type == 2) {
            textTitle.setText("限时免费");
            viewLimited.setVisibility(VISIBLE);
            allText.setVisibility(GONE);
            viewLimited.setData(time);
        }


    }

}
