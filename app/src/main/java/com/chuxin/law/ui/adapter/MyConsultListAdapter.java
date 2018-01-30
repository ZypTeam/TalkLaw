package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.ui.viewholder.MyConsultViewHolder;

import com.chuxin.law.R;

import com.chuxin.law.model.MyConsultModel;

/**
 * @author wangcc
 * @date 2018/1/18
 * @describe 我的咨询 list adapter
 */

public class MyConsultListAdapter extends BaseAdapter<MyConsultModel> {
    public MyConsultListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_my_consult;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new MyConsultViewHolder(view,context);
    }
}
