package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chuxin.law.model.IntegralModel;
import com.chuxin.law.ui.viewholder.IntegralProductViewHolder;

import java.util.ArrayList;
import java.util.List;

import com.chuxin.law.R;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2216:17
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class IntegralProductAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<IntegralModel.GoodsItemModel> goods;

    public IntegralProductAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        goods= new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntegralProductViewHolder(
                mInflater.inflate(R.layout.item_prodycu_integral, parent, false),
                mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((IntegralProductViewHolder) holder).update(goods.get(position));
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }


    public void refresh(List<IntegralModel.GoodsItemModel> goods) {
        this.goods.clear();
        this.goods.addAll(goods);
        notifyDataSetChanged();
    }
}
