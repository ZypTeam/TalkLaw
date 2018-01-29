package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.ExchangeRecordsItemModel;
import com.chuxin.law.ui.viewholder.ExchangeDetailsViewHolder;

/**
 * @author zyp
 * @date 2017/12/23
 * @describe  兑换详情
 */

public class ExchangeDetailsAdapter extends BaseAdapter<ExchangeRecordsItemModel> {
    public ExchangeDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_exchange_records_details;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new ExchangeDetailsViewHolder(view, context);
    }

}
