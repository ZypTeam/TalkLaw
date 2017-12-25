package cn.com.talklaw.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import cn.com.talklaw.R;
import cn.com.talklaw.constant.OpinionItemConstant;
import cn.com.talklaw.ui.viewholder.CarouselViewHolder;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2216:17
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class OpinionAdapter extends RecyclerView.Adapter implements OpinionItemConstant{
    private Context mContext;
    private LayoutInflater mInflater;

    public OpinionAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CAROUSEL:
                return new CarouselViewHolder(
                        mInflater.inflate(R.layout.item_opinion_carousel, parent, false),
                        mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_CAROUSEL) {
            ((CarouselViewHolder) holder).update(null);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
//        return (int) mDataList.get(position).getNewsType();
        return TYPE_CAROUSEL;
    }

    public void refresh(){

    }
}
