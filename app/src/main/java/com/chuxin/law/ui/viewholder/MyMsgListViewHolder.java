package com.chuxin.law.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chuxin.law.common.AdapterCallback;
import com.chuxin.law.model.MyMsgModel;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseViewHolder;
import com.chuxin.law.common.CommonConstant;

/**
 * @author wangcc
 * @date 2018/1/3
 * @describe
 */

public class MyMsgListViewHolder extends BaseViewHolder<MyMsgModel> {
    private TextView del, time, title, content;

    public MyMsgListViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        del = itemView.findViewById(R.id.del);
        time = itemView.findViewById(R.id.time);
        title = itemView.findViewById(R.id.title);
        content = itemView.findViewById(R.id.content);
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
