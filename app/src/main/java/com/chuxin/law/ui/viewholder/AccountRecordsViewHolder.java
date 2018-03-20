package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 交易明细 viewholder
 */

public class AccountRecordsViewHolder extends BaseViewHolder<AccountDetailModel.AccountItemModel> {

    protected View rootView;
    protected TextView textTitle;
    protected TextView textTime;
    protected TextView textMoney;

    public AccountRecordsViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        initView(itemView);
    }

    @Override
    public void update(AccountDetailModel.AccountItemModel model) {
//        textTitle.setOnClickListener(model.);
        textTime.setText(model.createtime);
        textMoney.setText("-"+model.money);
    }

    private void initView(View rootView) {
        textTitle = (TextView) rootView.findViewById(R.id.text_title);
        textTime = (TextView) rootView.findViewById(R.id.text_time);
        textMoney = (TextView) rootView.findViewById(R.id.text_money);
    }
}
