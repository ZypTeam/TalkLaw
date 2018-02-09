package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.model.ProductItemModel;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.util.ImageLoderUtil;
import com.chuxin.law.util.UIUtils;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2216:43
 * @Email zyp@jusfoun.com
 * @Description ${轮播图viewpager}
 */
public class CarouselViewHolder extends BaseViewHolder<ProductItemModel> {

    protected ImageView imgAvatar;
    protected TextView textName;
    protected TextView textTime;
    protected TextView textCount;
    protected TextView titleText;

    public CarouselViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        textName = (TextView) itemView.findViewById(R.id.text_name);
        textTime = (TextView) itemView.findViewById(R.id.text_time);
        textCount = (TextView) itemView.findViewById(R.id.text_count);
        titleText = (TextView)itemView.findViewById(R.id.text_title);
    }


    @Override
    public void update(final ProductItemModel model) {
        ImageLoderUtil.loadRoundSmailImage(mContext,imgAvatar,model.img,R.drawable.img_product_normal);
        textName.setText(model.lawyer);
        textTime.setText(model.createtime);
        textCount.setText(model.comment_num);
        titleText.setText(model.title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.goLawyerDef(mContext,model.id);

            }
        });
    }

}
