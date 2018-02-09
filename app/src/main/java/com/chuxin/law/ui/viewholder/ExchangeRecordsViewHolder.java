package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.ExchangeRecordsDataModel;
import com.chuxin.law.util.ImageLoderUtil;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 兑换记录aviewholder
 */

public class ExchangeRecordsViewHolder extends BaseViewHolder<ExchangeRecordsDataModel.ExchangeRecordsItemModel> {
    protected TextView textNum;
    protected TextView textState;
    protected RelativeLayout layoutTitle;
    protected ImageView imgProductIcon;
    protected RelativeLayout layoutProduct;
    protected TextView textCount;
    protected TextView textIntegral;

    protected TextView desText;

    public ExchangeRecordsViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        initView(itemView);
    }

    @Override
    public void update(ExchangeRecordsDataModel.ExchangeRecordsItemModel model) {
        if(model.goods==null){
            return;
        }
        ImageLoderUtil.loadRoundSmailImage(mContext,imgProductIcon,model.img,R.drawable.img_project);

        textNum.setText("订单号："+model.createtime);
        textState.setText("交易成功");
        textCount.setText("");
        textIntegral.setText(model.goods.point+"积分");
        desText.setText(model.goods.title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, RedeemDetailsActivity.class);
//                mContext.startActivity(intent);
            }
        });

    }

    private void initView(View rootView) {
        textNum = (TextView) rootView.findViewById(R.id.text_num);
        textState = (TextView) rootView.findViewById(R.id.text_state);
        layoutTitle = (RelativeLayout) rootView.findViewById(R.id.layout_title);
        imgProductIcon = (ImageView) rootView.findViewById(R.id.img_product_icon);
        layoutProduct = (RelativeLayout) rootView.findViewById(R.id.layout_product);
        textCount = (TextView) rootView.findViewById(R.id.text_count);
        textIntegral = (TextView) rootView.findViewById(R.id.text_integral);
        desText = (TextView) rootView.findViewById(R.id.text_des);
    }
}
