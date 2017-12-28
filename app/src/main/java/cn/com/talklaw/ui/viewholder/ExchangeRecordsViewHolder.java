package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.ExchangeRecordsItemModel;
import cn.com.talklaw.model.ProductModel;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 兑换记录aviewholder
 */

public class ExchangeRecordsViewHolder extends BaseViewHolder<ExchangeRecordsItemModel> {
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
    public void update(ExchangeRecordsItemModel model) {
        Glide.with(mContext)
                .load(R.mipmap.logo)
                .into(imgProductIcon);

        textNum.setText("订单号：123456789000");
        textState.setText("交易成功");
        textCount.setText("共6件商品");
        textIntegral.setText("299积分");
        desText.setText("律师书籍");

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
