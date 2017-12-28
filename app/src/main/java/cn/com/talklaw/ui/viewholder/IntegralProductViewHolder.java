package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2216:43
 * @Email zyp@jusfoun.com
 * @Description ${轮播图viewpager}
 */
public class IntegralProductViewHolder extends BaseViewHolder {


    protected ImageView imgProduct;
    protected TextView textTitle;
    protected TextView textCount;
    protected Button btnExchange;

    public IntegralProductViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        initView(itemView);
    }

    @Override
    public void update(Serializable model) {
        Glide.with(mContext).load("http://img5.imgtn.bdimg.com/it/u=2137958015,4291978384&fm=27&gp=0.jpg").into(imgProduct);
        textTitle.setText("点在产品电子产品");
        textCount.setText("100");
    }

    private void initView(View rootView) {
        imgProduct = (ImageView) rootView.findViewById(R.id.img_product);
        textTitle = (TextView) rootView.findViewById(R.id.text_title);
        textCount = (TextView) rootView.findViewById(R.id.text_count);
        btnExchange = (Button) rootView.findViewById(R.id.btn_exchange);

    }
}
