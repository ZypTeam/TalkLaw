package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.ui.viewholder.AccountDetailModel;
import com.chuxin.law.ui.viewholder.AccountRecordsViewHolder;
import com.chuxin.law.ui.viewholder.ExchangeRecordsViewHolder;

/**
 * @author zhaoyapeng
 * @version create time:18/3/2017:21
 * @Email zyp@jusfoun.com
 * @Description ${账户明细adapter}
 */
public class AccountDetailAdapter extends BaseAdapter<AccountDetailModel.AccountItemModel> {
    public AccountDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.layout_item_account;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new AccountRecordsViewHolder(view, context);
    }


}
