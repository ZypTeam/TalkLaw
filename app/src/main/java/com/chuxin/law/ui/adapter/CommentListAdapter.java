package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.CommentModel;
import com.chuxin.law.ui.viewholder.CommentListViewHolder;

/**
 * @author wangcc
 * @date 2018/1/31
 * @describe
 */

public class CommentListAdapter extends BaseAdapter<CommentModel> {

    public CommentListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_list_comment;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new CommentListViewHolder(view,context);
    }
}
