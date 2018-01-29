package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.ArrondiProductModel;
import com.chuxin.law.ui.viewholder.ArrondiProductListViewHolder;

/**
 * @author wangcc
 * @date 2017/12/24
 * @describe
 */

public class ArrondiProductListAdapter extends BaseAdapter<ArrondiProductModel> {
    public ArrondiProductListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_arrondi_product_list;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new ArrondiProductListViewHolder(view,context);
    }
}
