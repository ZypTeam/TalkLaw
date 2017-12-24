package cn.com.talklaw.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.talklaw.R;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 专题页top 滚动adapter
 */

public class ArrondiTopAdapter extends PagerAdapter {
    private List<String> list=new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public ArrondiTopAdapter(Context context){
        inflater=LayoutInflater.from(context);
        this.context=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=inflater.inflate(R.layout.item_arrondi_top,null);
        ImageView imageView=view.findViewById(R.id.image);
        Glide.with(context)
                .load(list.get(position))
                .centerCrop()
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void refresh(List<String> list){
        if (list==null){
            list=new ArrayList<>();
        }
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
