package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.comment.AdapterDelCallback;
import com.chuxin.law.ui.viewholder.MyMsgListViewHolder;

import com.chuxin.law.R;

import com.chuxin.law.model.MyMsgModel;

/**
 * @author wangcc
 * @date 2018/1/3
 * @describe
 */

public class MyMsgListAdapter extends BaseAdapter<MyMsgModel> {
    public MyMsgListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_my_msg_list;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        MyMsgListViewHolder viewHolder=new MyMsgListViewHolder(view,context);
        viewHolder.setDelCallback(callback);
        return (E)viewHolder;
    }

    private AdapterDelCallback callback;

    public void setCallback(AdapterDelCallback callback) {
        this.callback = callback;
    }
}
