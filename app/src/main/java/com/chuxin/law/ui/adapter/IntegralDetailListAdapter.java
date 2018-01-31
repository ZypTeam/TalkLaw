package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuxin.law.R;
import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.ArrondiProductModel;
import com.chuxin.law.model.IntegralDetailDataModel;

/**
 * @author wangcc
 * @date 2017/12/24
 * @describe 积分详情 list
 */

public class IntegralDetailListAdapter extends BaseAdapter<IntegralDetailDataModel.IntegralDetailItemModel> {
    public IntegralDetailListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_integral_detial;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new IntegralDetailViewHolder(view, context);
    }


    public class IntegralDetailViewHolder extends BaseViewHolder<IntegralDetailDataModel.IntegralDetailItemModel> {
        protected View rootView;
        protected TextView textTitle;
        protected TextView textTime;
        protected TextView textCount;
        private Context context;

        public IntegralDetailViewHolder(View itemView, Context mContext) {
            super(itemView, mContext);
            context = mContext;
            initView(itemView);

        }

        @Override
        public void update(final IntegralDetailDataModel.IntegralDetailItemModel model) {
            textTitle.setText(model.remarks);
            textTime.setText(model.createtime);
            textCount.setText("+"+model.point);
        }

        private void initView(View rootView) {
            textTitle = (TextView) rootView.findViewById(R.id.text_title);
            textTime = (TextView) rootView.findViewById(R.id.text_time);
            textCount = (TextView) rootView.findViewById(R.id.text_count);
        }
    }
}
