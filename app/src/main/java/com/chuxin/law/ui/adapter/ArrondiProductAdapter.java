package com.chuxin.law.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.chuxin.law.R;
import com.chuxin.law.model.ArrondiProductModel;

/**
 * @author wangcc
 * @date 2017/12/24
 * @describe 分区产品分类 adapter
 */

public class  ArrondiProductAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<List<ArrondiProductModel>> lists=new ArrayList<>();
    public ArrondiProductAdapter(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=inflater.inflate(R.layout.item_arrondi_product,null);
        RecyclerView list=view.findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(context,4));
        ArrondiProductListAdapter adapter=new ArrondiProductListAdapter(context);
        list.setAdapter(adapter);
        adapter.refreshList(lists.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    public void refresh(List<List<ArrondiProductModel>> lists){
        if (lists==null){
            lists=new ArrayList<>();
        }
        this.lists.clear();
        this.lists.addAll(lists);
        notifyDataSetChanged();
    }
}
