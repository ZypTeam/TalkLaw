package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.IntegralModel;
import com.chuxin.law.util.ImageLoderUtil;

import com.chuxin.law.R;

/**
 * @author wangcc
 * @date 2017/12/zyp
 * @describe 积分产品adapter
 */

public class IntegralGoodsAdapter extends BaseAdapter<IntegralModel.CatItemModel> {
    protected View rootView;
    protected ImageView imgItemGoods;
    protected TextView textTitle;

    public IntegralGoodsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_integral_goods;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new IntegralGoodsViewHolder(view, context);
    }


    public class IntegralGoodsViewHolder extends BaseViewHolder<IntegralModel.CatItemModel> {
        private Context context;

        public IntegralGoodsViewHolder(View itemView, Context mContext) {
            super(itemView, mContext);
            this.context = mContext;
            initView(itemView);
        }

        @Override
        public void update(IntegralModel.CatItemModel model) {
            ImageLoderUtil.loadRoundSmailImage(mContext, imgItemGoods, model.img,R.drawable.img_defail_dianzichanpin);
            textTitle.setText(model.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(callBack!=null){
                        callBack.click(0);
                    }
                }
            });
        }

        private void initView(View rootView) {
            imgItemGoods = (ImageView) rootView.findViewById(R.id.img_item_goods);
            textTitle = (TextView) rootView.findViewById(R.id.text_title);

        }
    }

    public interface CallBack {
        void click(int position);
    }

    public CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
