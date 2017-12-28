package cn.com.talklaw.ui.adapter;

import android.content.Context;
import android.view.View;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseAdapter;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.ProductModel;
import cn.com.talklaw.ui.viewholder.HomeNeedHolder;
import cn.com.talklaw.ui.viewholder.ProductViewHolder;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 专区 产品 list adapter
 */

public class HomeNeedAdapter extends BaseAdapter<ProductModel> {
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
