package com.chuxin.law.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jusfoun.baselibrary.Util.PhoneUtil;
import com.jusfoun.baselibrary.Util.TouchUtil;
import com.jusfoun.baselibrary.widget.TitleStatusBarView;

import com.chuxin.law.R;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe
 */

public class BackTitleView extends FrameLayout {
    protected ImageView back,right;
    protected TextView title,rightText;
    protected TitleStatusBarView titleBar;
    private Context context;

    public BackTitleView(@NonNull Context context) {
        this(context, null);
    }

    public BackTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackTitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAction();
    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_back_title, this, true);
        back = (ImageView) findViewById(R.id.back);
        right = (ImageView) findViewById(R.id.right);
        title = (TextView) findViewById(R.id.title);
        titleBar = (TitleStatusBarView) findViewById(R.id.title_bar);
        rightText = findViewById(R.id.right_text);
        rightText.setVisibility(View.GONE);
        right.setVisibility(GONE);
    }

    private void initAction(){
        TouchUtil.createTouchDelegate(right, PhoneUtil.dip2px(context,10));
        TouchUtil.createTouchDelegate(back, PhoneUtil.dip2px(context,10));
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Activity){
                    ((Activity) context).onBackPressed();
                }
            }
        });
    }

    public void setRightIcon(int resId){
        right.setVisibility(VISIBLE);
        right.setImageResource(resId);
    }

    public void setRightListener(OnClickListener listener){
        right.setOnClickListener(listener);
    }

    public void setBackIcon(int resId){
        back.setImageResource(resId);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setTitleColor(int color){
        title.setTextColor(color);
    }

    public void setTitle(int resId){
        this.title.setText(resId);
    }

    public void setRightText(String text, View.OnClickListener listener){
        if(TextUtils.isEmpty(text)){
            rightText.setVisibility(View.GONE);
        } else {
            rightText.setVisibility(VISIBLE);
            rightText.setText(text);
        }
        rightText.setOnClickListener(listener);
    }

}
