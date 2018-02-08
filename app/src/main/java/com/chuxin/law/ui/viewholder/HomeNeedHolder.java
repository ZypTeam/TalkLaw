package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.model.ProductItemModel;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.ui.util.ImageLoderUtil;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 产品vh
 */

public class HomeNeedHolder extends BaseViewHolder<ProductItemModel> {
    protected ImageView imgAvatar;
    protected TextView textTitle, countText;

    public HomeNeedHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        textTitle = (TextView) itemView.findViewById(R.id.text_title);
        countText = (TextView) itemView.findViewById(R.id.text_count);
    }

    @Override
    public void update(ProductItemModel model) {
        ImageLoderUtil.loadRoundImage(mContext, imgAvatar, model.img,100,R.drawable.img_defail_cainixuyao);


        textTitle.setText(model.title);
        countText.setText(model.comment_num);
    }

}
