package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chuxin.law.base.BaseAdapter;

import com.chuxin.law.R;

import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.comment.AdapterDelCallback;
import com.chuxin.law.model.MyAttentionModel;
import com.chuxin.law.ui.viewholder.MyAttentionViewHolder;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe
 */

public class MyAttentionAdapter extends BaseAdapter<MyAttentionModel> {
    public MyAttentionAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_my_attention;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        MyAttentionViewHolder viewHolder=new MyAttentionViewHolder(view,context);
        viewHolder.setDelCallBack(callback);
        return (E) viewHolder;
    }

    private AdapterDelCallback callback;

    public void setCallback(AdapterDelCallback callback) {
        this.callback = callback;
    }
}
