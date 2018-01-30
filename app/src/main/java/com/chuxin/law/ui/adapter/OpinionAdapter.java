package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chuxin.law.constant.OpinionItemConstant;
import com.chuxin.law.model.ProductItemModel;
import com.chuxin.law.ui.viewholder.CarouselViewHolder;
import com.chuxin.law.ui.viewholder.LimitedTimeFreeViewHolder;

import java.util.ArrayList;
import java.util.List;

import com.chuxin.law.R;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2216:17
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class OpinionAdapter extends RecyclerView.Adapter implements OpinionItemConstant {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ProductItemModel> list;

    private int type = 1;//1. 热门产品 2.限时免费

    public OpinionAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        list = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CAROUSEL:
                return new CarouselViewHolder(
                        mInflater.inflate(R.layout.item_opinion_carousel, parent, false),
                        mContext);
            case TYPE_TIME_FREE:
                return new LimitedTimeFreeViewHolder(
                        mInflater.inflate(R.layout.item_limit_time_free, parent, false),
                        mContext);


        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_CAROUSEL) {
            ((CarouselViewHolder) holder).update(list.get(position));
        } else if (getItemViewType(position) == TYPE_TIME_FREE) {
            ((LimitedTimeFreeViewHolder) holder).update(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return (int) mDataList.get(position).getNewsType();
        if (type == 1) {
            return TYPE_CAROUSEL;
        } else if (type == 2) {
            return TYPE_TIME_FREE;
        }
        return TYPE_CAROUSEL;
    }

    public void refresh(List<ProductItemModel> list, int type) {
        this.type = type;
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();

    }
}
