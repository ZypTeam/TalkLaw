package cn.com.talklaw.ui.adapter;

import android.content.Context;
import android.view.View;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseAdapter;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.ArrondiProductModel;
import cn.com.talklaw.ui.viewholder.ArrondiProductListViewHolder;

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
