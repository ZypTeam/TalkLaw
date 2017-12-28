package cn.com.talklaw.ui.adapter;

import android.content.Context;
import android.view.View;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseAdapter;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.ExchangeRecordsItemModel;
import cn.com.talklaw.ui.viewholder.ExchangeDetailsViewHolder;
import cn.com.talklaw.ui.viewholder.ExchangeRecordsViewHolder;

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
