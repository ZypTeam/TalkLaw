package cn.com.talklaw.ui.adapter;

import android.content.Context;
import android.view.View;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseAdapter;
import cn.com.talklaw.base.BaseViewHolder;
import cn.com.talklaw.model.MyConsultModel;
import cn.com.talklaw.ui.viewholder.MyConsultViewHolder;

/**
 * @author wangcc
 * @date 2018/1/18
 * @describe 我的咨询 list adapter
 */

public class MyConsultListAdapter extends BaseAdapter<MyConsultModel> {
    public MyConsultListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_my_consult;
    }

    @Override
    protected <E extends BaseViewHolder> E getViewHolder(int viewType, View view) {
        return (E) new MyConsultViewHolder(view,context);
    }
}
