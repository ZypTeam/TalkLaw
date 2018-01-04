package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.comment.OnMsgDelCallback;
import cn.com.talklaw.model.MyMsgModel;

/**
 * @author wangcc
 * @date 2018/1/3
 * @describe
 */

public class MyMsgListViewHolder extends BaseViewHolder<MyMsgModel> {
    private TextView del;
    public MyMsgListViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        del=itemView.findViewById(R.id.del);
    }

    @Override
    public void update(final MyMsgModel model) {
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delCallback!=null){
                    delCallback.del(model,getAdapterPosition());
                }
            }
        });
    }

    private OnMsgDelCallback delCallback;

    public MyMsgListViewHolder setDelCallback(OnMsgDelCallback delCallback) {
        this.delCallback = delCallback;
        return this;
    }
}
