package cn.com.talklaw.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import cn.com.talklaw.R;
import cn.com.talklaw.model.AreaBean;

/**
 * 学校城市 适配器
 *
 * @author 王鹏
 * @2015-5-7下午5:48:59
 */
@SuppressLint("ResourceAsColor")
public class BaseAreaCityAdapter extends BaseAdapter {

    public SparseBooleanArray selectionMap;
    List<AreaBean> list;
    private Context context;
    private LayoutInflater inflater;

    public BaseAreaCityAdapter(Context context, List<AreaBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.baseschool_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_text);
            holder.re_item = (RelativeLayout) convertView
                    .findViewById(R.id.re_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        boolean ischeck = selectionMap.get(position);
        if (ischeck) {
            holder.re_item.setBackgroundColor(context.getResources().getColor(
                    R.color.write));
        } else {
            holder.re_item.setBackgroundColor(context.getResources().getColor(
                    R.color.huise));

        }
        if (list != null) {
            holder.textView.setText(list.get(position).getName());
        }
        return convertView;
    }

    public void init() {
        for (int i = 0; i < list.size(); i++) {
            selectionMap = new SparseBooleanArray();
            selectionMap.put(i, false);
        }
    }

    private class ViewHolder {
        TextView textView;
        RelativeLayout re_item;
    }
}
