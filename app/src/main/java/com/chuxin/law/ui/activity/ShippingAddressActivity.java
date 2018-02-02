package com.chuxin.law.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author zhaoyapeng
 * @version create time:18/2/214:06
 * @Email zyp@jusfoun.com
 * @Description ${收货地址页面}
 */
public class ShippingAddressActivity extends BaseTalkLawActivity {
    protected BackTitleView backTitleView;
    protected RecyclerView recyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_shipping_address;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public void initAction() {
        backTitleView.setTitle("收货地址");
        backTitleView.setRightText("新增地址", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, SelectAreaActivity.class);
            }
        });
    }
}
