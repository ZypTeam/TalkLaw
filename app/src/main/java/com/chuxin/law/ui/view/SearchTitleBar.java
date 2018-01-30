package com.chuxin.law.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseView;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1820:53
 * @Email zyp@jusfoun.com
 * @Description ${搜索titleBar}
 */
public class SearchTitleBar extends BaseView {
    public SearchTitleBar(Context context) {
        super(context);
    }

    public SearchTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate( R.layout.view_search_titlebar,this,true);
    }

    @Override
    protected void initActions() {

    }
}
