package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chuxin.law.model.ProductItemModel;
import com.chuxin.law.ui.viewholder.HomeNeedHolder;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 专区 产品 list adapter
 */

public class HomeNeedAdapter extends BaseAdapter<ProductItemModel> {
    public HomeNeedAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_need;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new HomeNeedHolder(view,context);
    }
}
