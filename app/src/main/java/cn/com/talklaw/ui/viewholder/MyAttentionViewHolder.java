package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.MyAttentionModel;
import cn.com.talklaw.ui.activity.LawyerDefautActivity;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe
 */

public class MyAttentionViewHolder extends BaseViewHolder<MyAttentionModel> {
    private TextView level,haoping,yiban,suc;
    private ImageView icon_head;
    public MyAttentionViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        level=itemView.findViewById(R.id.level);
        haoping=itemView.findViewById(R.id.haoping);
        yiban=itemView.findViewById(R.id.yiban);
        suc=itemView.findViewById(R.id.suc);
        icon_head=itemView.findViewById(R.id.icon_head);
    }

    @Override
    public void update(MyAttentionModel model) {
        level.setText(getText("1254","已办"));
        haoping.setText(getText("专业级","等级"));
        yiban.setText(getText("100%","好评"));
        suc.setText(getText("100%","胜率"));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, LawyerDefautActivity.class);
                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext)
                .load("http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg")
                .placeholder(R.mipmap.icon_head_def_cir)
                .error(R.mipmap.icon_head_def_cir)
                .transform(new CenterCrop(mContext),new GlideCircleTransform(mContext))
                .crossFade()
                .into(icon_head);

    }

    public SpannableStringBuilder getText(String txt1,String txt2){
        SpannableStringBuilder builder=new SpannableStringBuilder(txt1+"\n"+txt2);

        builder.setSpan(new AbsoluteSizeSpan(15,true),0,txt1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(12,true),txt1.length(),txt1.length()+txt2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")),0,txt1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")),txt1.length(),txt1.length()+txt2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
}
