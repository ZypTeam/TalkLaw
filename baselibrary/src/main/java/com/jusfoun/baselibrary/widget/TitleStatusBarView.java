package com.jusfoun.baselibrary.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.jusfoun.baselibrary.Util.PhoneUtil;

/**
 * Created by wangcc on 2017/9/6.
 * describe 沉浸式状态栏 titleview
 */

public class TitleStatusBarView extends FrameLayout {
    private Context mContext;
    private int statusBarHeight;
    public TitleStatusBarView(Context context) {
        super(context);
        initView(context);
    }

    public TitleStatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TitleStatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        mContext=context;
        statusBarHeight= PhoneUtil.getStatusBarHeight(mContext);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int height=0;
        if (heightMode==MeasureSpec.EXACTLY){
            Log.e("tag","MeasureSpec1");
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                setPadding(0,statusBarHeight,0,0);
                height=heightSize+statusBarHeight;
            }else {
                height=heightSize;
            }
        }else if (heightMode==MeasureSpec.AT_MOST){
            Log.e("tag","MeasureSpec2");
            int count=getChildCount();
            if (count!=1){
                throw new IllegalArgumentException("titlebar child view count most is 1");
            }
            Log.e("tag","MeasureSpec3");
            View child=getChildAt(0);
            if (child.getVisibility()!=GONE){
                Log.e("tag","MeasureSpec4");
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                LayoutParams layoutParams= (LayoutParams) child.getLayoutParams();
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
                    Log.e("tag","MeasureSpec5");
                    height = child.getMeasuredHeight() + layoutParams.topMargin
                            + layoutParams.bottomMargin+ statusBarHeight;
                    setPadding(0,statusBarHeight,0,0);
                }else {
                    Log.e("tag","MeasureSpec6");
                    height = child.getMeasuredHeight() + layoutParams.topMargin
                            + layoutParams.bottomMargin;
                }
            }
        }
        setMeasuredDimension(widthSize,height);
    }
}
