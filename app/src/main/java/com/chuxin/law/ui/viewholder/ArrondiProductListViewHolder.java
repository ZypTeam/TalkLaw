package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.model.ArrondiProductModel;
import com.chuxin.law.ui.activity.ProductiListActivity;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.util.ImageLoderUtil;

/**
 * @author wangcc
 * @date 2017/12/24
 * @describe
 */

public class ArrondiProductListViewHolder extends BaseViewHolder<ArrondiProductModel> {
    private Context context;
    private ImageView icon;
    private TextView name;
    public ArrondiProductListViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        context=mContext;
        icon=itemView.findViewById(R.id.icon);
        name=itemView.findViewById(R.id.name);
    }

    @Override
    public void update(final ArrondiProductModel model) {
        name.setText(model.getName());
        ImageLoderUtil.loadNormalImg(context,icon,model.getImg(),R.mipmap.icon_def_zhuanqu);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context, ProductiListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(ProductiListActivity.PRODUCT_MODEL,model);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}
