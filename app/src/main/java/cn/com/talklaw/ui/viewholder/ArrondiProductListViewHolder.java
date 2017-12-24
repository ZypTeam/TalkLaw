package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.microedition.khronos.opengles.GL;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.ArrondiProductModel;
import cn.com.talklaw.ui.activity.ProductiListActivity;

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
    public void update(ArrondiProductModel model) {
        Glide.with(context)
                .load(model.getImageResId())
                .into(icon);
        name.setText(model.getName());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context, ProductiListActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
