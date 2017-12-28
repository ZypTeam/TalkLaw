package cn.com.talklaw.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cn.com.talklaw.R;
import cn.com.talklaw.constant.OpinionItemConstant;
import cn.com.talklaw.ui.viewholder.CarouselViewHolder;
import cn.com.talklaw.ui.viewholder.IntegralProductViewHolder;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2216:17
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class IntegralProductAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public IntegralProductAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new IntegralProductViewHolder(
                        mInflater.inflate(R.layout.item_prodycu_integral, parent, false),
                        mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((IntegralProductViewHolder) holder).update(null);
    }

    @Override
    public int getItemCount() {
        return 6;
    }


    public void refresh(){

    }
}
