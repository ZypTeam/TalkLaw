package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.IntegralModel;
import com.chuxin.law.ui.activity.IntegralWebViewActivity;
import com.chuxin.law.ui.util.ImageLoderUtil;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2216:43
 * @Email zyp@jusfoun.com
 * @Description ${轮播图viewpager}
 */
public class IntegralProductViewHolder extends BaseViewHolder<IntegralModel.GoodsItemModel> {


    protected ImageView imgProduct;
    protected TextView textTitle;
    protected TextView textCount;
    protected Button btnExchange;

    public IntegralProductViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        initView(itemView);
    }

    @Override
    public void update(final IntegralModel.GoodsItemModel model) {
        ImageLoderUtil.loadRoundSmailImage(mContext,imgProduct,model.img);
        textTitle.setText(model.title);
        textCount.setText(model.point+"");

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, IntegralWebViewActivity.class);
                intent.putExtra("id",model.id);
                mContext.startActivity(intent);
            }
        });
    }

    private void initView(View rootView) {
        imgProduct = (ImageView) rootView.findViewById(R.id.img_product);
        textTitle = (TextView) rootView.findViewById(R.id.text_title);
        textCount = (TextView) rootView.findViewById(R.id.text_count);
        btnExchange = (Button) rootView.findViewById(R.id.btn_exchange);



    }
}
