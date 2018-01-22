package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.MyConsultModel;

/**
 * @author wangcc
 * @date 2018/1/18
 * @describe
 */

public class MyConsultViewHolder extends BaseViewHolder<MyConsultModel> {
    private ImageView iconHead;
    public MyConsultViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        iconHead=itemView.findViewById(R.id.icon_head);
    }

    @Override
    public void update(MyConsultModel model) {
        if (model==null){
            return;
        }

        Glide.with(mContext)
                .load("http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg")
                .placeholder(R.mipmap.logo)
                .error(R.mipmap.logo)
                .transform(new CenterCrop(mContext),new GlideCircleTransform(mContext))
                .crossFade()
                .into(iconHead);
    }
}
