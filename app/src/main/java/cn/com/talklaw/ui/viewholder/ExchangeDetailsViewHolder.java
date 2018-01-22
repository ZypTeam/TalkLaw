package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jusfoun.baselibrary.widget.GlideRoundTransform;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.ExchangeRecordsItemModel;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 兑换记录aviewholder
 */

public class ExchangeDetailsViewHolder extends BaseViewHolder<ExchangeRecordsItemModel> {

    protected TextView textTitle;
    protected TextView textIntegral;
    protected TextView textCount;
    protected ImageView imgTitle;

    public ExchangeDetailsViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        initView(itemView);
    }

    @Override
    public void update(ExchangeRecordsItemModel model) {
        Glide.with(mContext)
                .load(R.mipmap.logo)
                .transform(new CenterCrop(mContext),new GlideRoundTransform(mContext,5))
                .into(imgTitle);

        textTitle.setText("常律师书籍，详细讲解婚姻案例如何");
        textIntegral.setText("100积分");
        textCount.setText("x1");

    }

    private void initView(View rootView) {
        textTitle = (TextView) rootView.findViewById(R.id.text_title);
        textIntegral = (TextView) rootView.findViewById(R.id.text_integral);
        textCount = (TextView) rootView.findViewById(R.id.text_count);
        imgTitle = (ImageView) rootView.findViewById(R.id.img_title);
    }
}
