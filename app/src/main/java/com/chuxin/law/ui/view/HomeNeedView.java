package com.chuxin.law.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.chuxin.law.base.BaseView;
import com.chuxin.law.model.ProductItemModel;
import com.chuxin.law.ui.adapter.HomeNeedAdapter;

import java.util.List;

import com.chuxin.law.R;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2509:26
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class HomeNeedView extends BaseView {
    protected RecyclerView recyclerView;
    protected HomeNeedAdapter adapter;
    protected TextView textTitle;
    private LinearLayoutManager layoutManager;

    public HomeNeedView(Context context) {
        super(context);
    }

    public HomeNeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeNeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {
        adapter = new HomeNeedAdapter(mContext);
    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_need_home_list, this, true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        textTitle = (TextView) findViewById(R.id.text_title);
    }

    @Override
    protected void initActions() {
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



    }

    public void setData( List<ProductItemModel> neeList ) {
        textTitle.setText("猜你需要");
        adapter.refreshList(neeList);
    }
}
