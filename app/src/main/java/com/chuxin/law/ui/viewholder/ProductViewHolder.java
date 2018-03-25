package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.common.AdapterCallback;
import com.chuxin.law.model.ProductModel;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.util.ImageLoderUtil;
import com.chuxin.law.util.UIUtils;

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
    private AdapterCallback commentCall;
    private AdapterCallback thumbsCall;

    public ProductViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        this.context = mContext;
        initView(itemView);
    }

    @Override
    public void update(final ProductModel model) {
//        Glide.with(context)
//                .load(model.img)
//                .into(image);

        ImageLoderUtil.loadRoundSmailImage(mContext,image, model.img,R.drawable.img_product_normal);

        itemView.setTag(model);

        title.setText(model.title);
        date.setText(model.getLawyer()+" "+model.createtime);
        comment.setText(model.comment_num);
        thumbsUp.setText(model.like_num);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.goLawyerDef(mContext,model.getId());
            }
        });

        /*comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentCall!=null){
                    commentCall.callback(model,getAdapterPosition());
                }
            }
        });

        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thumbsCall!=null){
                    thumbsCall.callback(model,getAdapterPosition());
                }
            }
        });*/
    }

    private void initView(View rootView) {
        image = (ImageView) rootView.findViewById(R.id.image);
        title = (TextView) rootView.findViewById(R.id.title);
        date = (TextView) rootView.findViewById(R.id.date);
        line = (View) rootView.findViewById(R.id.line);
        thumbsUp = (TextView) rootView.findViewById(R.id.thumbs_up);
        comment = (TextView) rootView.findViewById(R.id.comment);
    }

    public AdapterCallback getCommentCall() {
        return commentCall;
    }

    public void setCommentCall(AdapterCallback commentCall) {
        this.commentCall = commentCall;
    }

    public AdapterCallback getThumbsCall() {
        return thumbsCall;
    }

    public void setThumbsCall(AdapterCallback thumbsCall) {
        this.thumbsCall = thumbsCall;
    }
}
