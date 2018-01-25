package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.ProductItemModel;
import cn.com.talklaw.ui.util.ImageLoderUtil;

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
    public void update(ProductItemModel model) {
        ImageLoderUtil.loadRoundImage(mContext,imgAvatar,model.img,10);
        textName.setText(model.lawyer);
        textTime.setText(model.createtime);
        textCount.setText(model.comment_num);
        titleText.setText(model.title);
    }

}
