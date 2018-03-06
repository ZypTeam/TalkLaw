package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.model.CommentModel;
import com.chuxin.law.util.ImageLoderUtil;

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
        if (model.getUser()!=null){
            name.setText(model.getUser().getName());
            ImageLoderUtil.loadCircleImage(mContext,head,model.getUser().getHeadimg(),R.mipmap.icon_head_def_cir);
        }
        time.setText(model.getCreatetime());
    }
}
