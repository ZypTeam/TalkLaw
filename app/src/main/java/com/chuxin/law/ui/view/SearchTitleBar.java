package com.chuxin.law.ui.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseView;
import com.jusfoun.baselibrary.Util.KeyBoardUtil;
import com.jusfoun.baselibrary.widget.TitleStatusBarView;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1820:53
 * @Email zyp@jusfoun.com
 * @Description ${搜索titleBar}
 */
public class SearchTitleBar extends BaseView {
    protected ImageView back;
    protected EditText editSearch;
    protected TitleStatusBarView titleBar;
    private TextView cancleText;

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
        LayoutInflater.from(mContext).inflate(R.layout.view_search_titlebar, this, true);

        back = (ImageView) findViewById(R.id.back);
        editSearch = (EditText) findViewById(R.id.edit_search);
        titleBar = (TitleStatusBarView) findViewById(R.id.title_bar);
        cancleText = (TextView)findViewById(R.id.text_cancle);
    }

    @Override
    protected void initActions() {

//        KeyBoardUtil.showSoftKeyboard(editSearch,mContext);
        cancleText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(editSearch.getText())){
                    editSearch.setText("");
                }else{
                    ((Activity)mContext).finish();
                }
            }
        });
    }

}
