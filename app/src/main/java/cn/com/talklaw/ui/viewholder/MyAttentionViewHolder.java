package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.MyAttentionModel;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe
 */

public class MyAttentionViewHolder extends BaseViewHolder<MyAttentionModel> {
    private TextView level,haoping,yiban,suc;
    public MyAttentionViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        level=itemView.findViewById(R.id.level);
        haoping=itemView.findViewById(R.id.haoping);
        yiban=itemView.findViewById(R.id.yiban);
        suc=itemView.findViewById(R.id.suc);
    }

    @Override
    public void update(MyAttentionModel model) {
        level.setText(getText("1254","已办"));
        haoping.setText(getText("专业级","等级"));
        yiban.setText(getText("100%","好评"));
        suc.setText(getText("100%","胜率"));

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
