package cn.com.talklaw.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.IntegralModel;
import com.chuxin.law.ui.util.ImageLoderUtil;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 积分商城 产品
 */

public class GoodsListAdapter extends BaseAdapter<IntegralModel.GoodsItemModel> {
    public GoodsListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_goods_all;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new GoodsViewHolser(view, context);
    }


    public class GoodsViewHolser extends BaseViewHolder<IntegralModel.GoodsItemModel> {

        protected View rootView;
        protected ImageView imgTitle;
        protected TextView textTitle;
        protected TextView textIntegral;
        protected TextView textDuihuan;

        public GoodsViewHolser(View itemView, Context mContext) {
            super(itemView, mContext);
            initView(itemView);
        }

        @Override
        public void update(IntegralModel.GoodsItemModel model) {
                ImageLoderUtil.loadRoundSmailImage(mContext, imgTitle, model.img);
            textTitle.setText(model.title);
            textIntegral.setText(model.point + "积分");
        }

        private void initView(View rootView) {
            imgTitle = (ImageView) rootView.findViewById(R.id.img_title);
            textTitle = (TextView) rootView.findViewById(R.id.text_title);
            textIntegral = (TextView) rootView.findViewById(R.id.text_integral);
            textDuihuan = (TextView) rootView.findViewById(R.id.text_duihuan);
        }
    }
}
