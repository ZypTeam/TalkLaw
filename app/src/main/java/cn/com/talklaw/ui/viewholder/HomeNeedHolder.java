package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.ProductModel;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 产品vh
 */

public class HomeNeedHolder extends BaseViewHolder<ProductModel> {
    protected ImageView imgAvatar;
    protected TextView textTitle,countText;

    public HomeNeedHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        textTitle = (TextView) itemView.findViewById(R.id.text_title);
        countText = (TextView)itemView.findViewById(R.id.text_count);
    }

    @Override
    public void update(ProductModel model) {
        Glide.with(mContext)
                .load(R.mipmap.logo)
                .into(imgAvatar);

        textTitle.setText("那些年的圣诞节 第一批90后的圣诞回忆");
        countText.setText("1000");
    }

}
