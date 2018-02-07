package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.common.AdapterCallback;
import com.chuxin.law.model.BackCardModel;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe 银行卡adapter
 */

public class SelectBankCardAdapter extends BaseAdapter<BackCardModel.BackCardItemModel> {
    public SelectBankCardAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_back_card;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        SelectBankCardViewHolder viewHolder = new SelectBankCardViewHolder(view, context);
        return (E) viewHolder;
    }

    private AdapterCallback callback;

    public void setCallback(AdapterCallback callback) {
        this.callback = callback;
    }


    public class SelectBankCardViewHolder extends BaseViewHolder<BackCardModel.BackCardItemModel> {
        protected View rootView;
        protected ImageView imgTitle;
        protected TextView textNum;
        protected LinearLayout layoutCard;
        private Context context;
        private ImageView img_select;

        public SelectBankCardViewHolder(View itemView, Context mContext) {
            super(itemView, mContext);
            this.context = mContext;
            initView(itemView);

        }

        @Override
        public void update(final BackCardModel.BackCardItemModel model) {
//            ImageLoderUtil.loadRoundSmailImage(mContext, imgItemGoods, model.img);


            if (model.card.length() > 4) {
                textNum.setText(model.card.substring(model.card.length() - 4, model.card.length() ));
            }else{
                textNum.setText(model.card);
            }


            layoutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        callBack.click(model);
                    }
                }
            });

            if (model.isSelect) {
                img_select.setImageResource(R.drawable.img_back_select);
            } else {
                img_select.setImageResource(R.drawable.img_back_select_no);
            }


        }

        private void initView(View rootView) {
            imgTitle = (ImageView) rootView.findViewById(R.id.img_title);
            textNum = (TextView) rootView.findViewById(R.id.text_num);
            layoutCard = (LinearLayout) rootView.findViewById(R.id.layout_card);
            img_select = (ImageView) rootView.findViewById(R.id.img_select);

        }
    }

    public interface CallBack {
        void click(BackCardModel.BackCardItemModel model);
    }

    public CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
