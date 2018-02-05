package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.sharedpreferences.ShippingAddressModel;
import com.chuxin.law.ui.adapter.ShippingAddressAdapter;
import com.chuxin.law.model.ShippingAddressSp;
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
    private ShippingAddressAdapter addressAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_shipping_address;
    }

    @Override
    public void initDatas() {
        addressAdapter = new ShippingAddressAdapter(mContext);
    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public void initAction() {
        backTitleView.setTitle("收货地址");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(addressAdapter);


        backTitleView.setRightText("新增地址", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, SelectAreaActivity.class);
            }
        });

        addressAdapter.setCallBack(new ShippingAddressAdapter.CallBack() {
            @Override
            public void selectAddress(int id) {
                ShippingAddressModel model = ShippingAddressSp.getShippingAddress(mContext);
                if (model != null && model.list != null) {
                    for (int i = 0; i < model.list.size(); i++) {
                        if (model.list.get(i).id == id) {
                            model.list.get(i).isSelect = true;
                        } else {
                            model.list.get(i).isSelect = false;
                        }
                    }
                }

                ShippingAddressSp.saveShippingAddress(mContext, model);
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        ShippingAddressModel model = ShippingAddressSp.getShippingAddress(mContext);
        if (model != null && model.list != null) {
            addressAdapter.refreshList(model.list);
        }

    }
}
