package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.ProductItemModel;
import cn.com.talklaw.ui.util.ImageLoderUtil;

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
        ImageLoderUtil.loadRoundImage(mContext, imgAvatar, model.img,100);


        textTitle.setText(model.title);
        countText.setText(model.comment_num);
    }

}
