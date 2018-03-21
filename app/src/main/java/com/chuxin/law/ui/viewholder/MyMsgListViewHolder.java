package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chuxin.law.common.AdapterCallback;
import com.chuxin.law.model.MyMsgModel;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.ui.activity.MsgDefActivity;

/**
 * @author wangcc
 * @date 2018/1/3
 * @describe
 */

public class MyMsgListViewHolder extends BaseViewHolder<MyMsgModel> {
    private TextView del, time, title, content;
    private ImageView icon;

    public MyMsgListViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        del = itemView.findViewById(R.id.del);
        time = itemView.findViewById(R.id.time);
        title = itemView.findViewById(R.id.title);
        content = itemView.findViewById(R.id.content);
        icon=itemView.findViewById(R.id.icon);
    }

    @Override
    public void update(final MyMsgModel model) {

        time.setText(model.getCreatetime());
        content.setText(model.getContent());
        switch (model.getType()) {
            case CommonConstant.MSG_TYPY_0:
                break;
            default:
                title.setText("系统消息");
                break;
        }
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MsgDefActivity.class);
                intent.putExtra(MsgDefActivity.MY_MSG_MODEL,model);
                mContext.startActivity(intent);
            }
        });
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MsgDefActivity.class);
                intent.putExtra(MsgDefActivity.MY_MSG_MODEL,model);
                mContext.startActivity(intent);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MsgDefActivity.class);
                intent.putExtra(MsgDefActivity.MY_MSG_MODEL,model);
                mContext.startActivity(intent);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MsgDefActivity.class);
                intent.putExtra(MsgDefActivity.MY_MSG_MODEL,model);
                mContext.startActivity(intent);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delCallback != null) {
                    delCallback.callback(model, getAdapterPosition());
                }
            }
        });
    }

    private AdapterCallback delCallback;

    public MyMsgListViewHolder setDelCallback(AdapterCallback delCallback) {
        this.delCallback = delCallback;
        return this;
    }
}
