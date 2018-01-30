package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.CommentModel;
import com.chuxin.law.ui.util.ImageLoderUtil;

/**
 * @author wangcc
 * @date 2018/1/31
 * @describe
 */

public class CommentListViewHolder extends BaseViewHolder<CommentModel> {
    private TextView name,time,content;
    private ImageView head;
    public CommentListViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        name=itemView.findViewById(R.id.name);
        time=itemView.findViewById(R.id.time);
        content=itemView.findViewById(R.id.content);
        head=itemView.findViewById(R.id.icon_head);
    }

    @Override
    public void update(CommentModel model) {
        if (model==null){
            return;
        }

        content.setText(model.getContent());
//        name.setText(model.getUserid());
        time.setText(model.getCreatetime());
        ImageLoderUtil.loadCircleImage(mContext,head,"http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg",R.mipmap.icon_head_def_cir);
    }
}
