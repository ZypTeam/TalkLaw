package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuxin.law.model.ProductModel;
import com.chuxin.law.ui.activity.AudioDetailsActivity;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 产品vh
 */

public class ProductViewHolder extends BaseViewHolder<ProductModel> {
    protected TextView title;
    protected TextView date;
    protected View line;
    protected TextView thumbsUp;
    protected TextView comment;
    private ImageView image;
    private Context context;

    public ProductViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        this.context = mContext;
        initView(itemView);
    }

    @Override
    public void update(ProductModel model) {
        Glide.with(context)
                .load(model.img)
                .into(image);


        title.setText(model.title);
        date.setText(model.lawyer+" "+model.createtime);
        comment.setText(model.comment_num);
        thumbsUp.setText(model.like_num);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, AudioDetailsActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    private void initView(View rootView) {
        image = (ImageView) rootView.findViewById(R.id.image);
        title = (TextView) rootView.findViewById(R.id.title);
        date = (TextView) rootView.findViewById(R.id.date);
        line = (View) rootView.findViewById(R.id.line);
        thumbsUp = (TextView) rootView.findViewById(R.id.thumbs_up);
        comment = (TextView) rootView.findViewById(R.id.comment);
    }
}
