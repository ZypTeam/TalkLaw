package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chuxin.law.model.ProductItemModel;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.ui.activity.LawyerDefautActivity;
import com.chuxin.law.ui.util.ImageLoderUtil;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2216:43
 * @Email zyp@jusfoun.com
 * @Description ${限时免费viewHolder}
 */
public class LimitedTimeFreeViewHolder extends BaseViewHolder<ProductItemModel> {


    protected View rootView;
    protected ImageView imgAvatar;
    protected TextView textTitle;
    protected TextView textMoney1;
    protected TextView textMoney2;
    protected LinearLayout layoutTitle;
    protected ImageView imgIconCount;
    protected TextView textCount;

    public LimitedTimeFreeViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        initView(itemView);
    }

    @Override
    public void update(ProductItemModel model) {

        ImageLoderUtil.loadRoundSmailImage(mContext,imgAvatar,model.img,R.drawable.img_product_normal);
        textCount.setText(model.comment_num);
        textTitle.setText(model.title);
        textMoney1.setText("￥"+model.price);
        textMoney2.setText("￥0");
        textMoney1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void initView(View rootView) {
        imgAvatar = (ImageView) rootView.findViewById(R.id.img_avatar);
        textTitle = (TextView) rootView.findViewById(R.id.text_title);
        textMoney1 = (TextView) rootView.findViewById(R.id.text_money1);
        textMoney2 = (TextView) rootView.findViewById(R.id.text_money2);
        layoutTitle = (LinearLayout) rootView.findViewById(R.id.layout_title);
        imgIconCount = (ImageView) rootView.findViewById(R.id.img_icon_count);
        textCount = (TextView) rootView.findViewById(R.id.text_count);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LawyerDefautActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
