package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chuxin.law.model.CaseTypeModel;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.ui.adapter.CaseTypeAdapter;

/**
 * @author wangcc
 * @date 2017/12/24
 * @describe
 */

public class CaseTypeTitleViewHolder extends BaseViewHolder<CaseTypeModel.CaseTypeItemModel> {
    private Context context;
    private TextView name;

    private CaseTypeAdapter.CallBack callBack;
    public CaseTypeTitleViewHolder(View itemView, Context mContext, CaseTypeAdapter.CallBack callBack) {
        super(itemView, mContext);
        context=mContext;
        name=itemView.findViewById(R.id.text_title);
        this.callBack =callBack;
    }

    @Override
    public void update(final CaseTypeModel.CaseTypeItemModel model) {
        name.setText(model.title);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack!=null)
                callBack.getCaseTyoe(model);
            }
        });
    }
}
