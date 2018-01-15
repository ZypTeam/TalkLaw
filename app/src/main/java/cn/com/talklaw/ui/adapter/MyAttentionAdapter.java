package cn.com.talklaw.ui.adapter;

import android.content.Context;
import android.view.View;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseAdapter;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.MyAttentionModel;
import cn.com.talklaw.ui.viewholder.MyAttentionViewHolder;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe
 */

public class MyAttentionAdapter extends BaseAdapter<MyAttentionModel> {
    public MyAttentionAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_my_attention;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new MyAttentionViewHolder(view,context);
    }
}
