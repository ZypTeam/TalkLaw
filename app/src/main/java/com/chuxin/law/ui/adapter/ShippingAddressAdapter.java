package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseAdapter;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.ShippingAddressModel;

/**
 * @author wangcc
 * @date 2018/1/31
 * @describe 收货地址adapter
 */

public class ShippingAddressAdapter extends BaseAdapter<ShippingAddressModel.ShippingAddressItemModel> {

    public ShippingAddressAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_shipping_address;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new ShippingAddressViewHolder(view, context);
    }


    public class ShippingAddressViewHolder extends BaseViewHolder<ShippingAddressModel.ShippingAddressItemModel> {
        protected View rootView;
        protected TextView textAddress1;
        protected TextView textAddress2;
        protected TextView textName;
        protected LinearLayout layoutTitle;
        protected ImageView imgXiugai;

        public ShippingAddressViewHolder(View itemView, Context mContext) {
            super(itemView, mContext);
            initView(itemView);
        }

        @Override
        public void update(final ShippingAddressModel.ShippingAddressItemModel model) {
            if (model == null) {
                return;
            }

            textAddress1.setText(model.area);
            textAddress2.setText(model.address);
            textName.setText(model.name + " " + model.phone);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(callBack!=null){
                       callBack.selectAddress(model.id);
                   }
                }
            });
        }

        private void initView(View rootView) {
            textAddress1 = (TextView) rootView.findViewById(R.id.text_address1);
            textAddress2 = (TextView) rootView.findViewById(R.id.text_address2);
            textName = (TextView) rootView.findViewById(R.id.text_name);
            layoutTitle = (LinearLayout) rootView.findViewById(R.id.layout_title);
            imgXiugai = (ImageView) rootView.findViewById(R.id.img_xiugai);
        }

    }


    public interface CallBack {
        void selectAddress(int id);
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }


}
