package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.ProductModel;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 产品vh
 */

public class ProductViewHolder extends BaseViewHolder<ProductModel> {
    private ImageView image;
    private Context context;
    public ProductViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        image=itemView.findViewById(R.id.image);
        this.context=mContext;
    }

    @Override
    public void update(ProductModel model) {
        Glide.with(context)
        .load(R.mipmap.logo)
        .into(image);
    }
}
