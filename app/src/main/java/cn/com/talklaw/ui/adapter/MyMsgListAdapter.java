package cn.com.talklaw.ui.adapter;

import android.content.Context;
import android.view.View;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseAdapter;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.comment.OnMsgDelCallback;
import cn.com.talklaw.model.MyMsgModel;
import cn.com.talklaw.ui.viewholder.MyMsgListViewHolder;

/**
 * @author wangcc
 * @date 2018/1/3
 * @describe
 */

public class MyMsgListAdapter extends BaseAdapter<MyMsgModel> {
    public MyMsgListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_my_msg_list;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        MyMsgListViewHolder viewHolder=new MyMsgListViewHolder(view,context);
        viewHolder.setDelCallback(callback);
        return (E)viewHolder;
    }

    private OnMsgDelCallback callback;

    public void setCallback(OnMsgDelCallback callback) {
        this.callback = callback;
    }
}
