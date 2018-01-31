package com.chuxin.law.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.jusfoun.baselibrary.Util.PhoneUtil;


/**
 * @author wangcc
 * @date 2018/1/17
 * @describe 监听键盘展示/隐藏 view
 */

public class KeyboardChangeView extends ConstraintLayout {
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    private View mRootView;
    protected boolean mIsSoftKeyboardShowing=false;
    public KeyboardChangeView(Context context) {
        this(context,null);
    }

    public KeyboardChangeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public KeyboardChangeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        mRootView=getRootView();
        mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //判断窗口可见区域大小
                Rect r = new Rect();
                mRootView.getWindowVisibleDisplayFrame(r);
                //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
                int screenHeight = PhoneUtil.getDisplayHeight(getContext());
                int heightDifference = screenHeight - (r.bottom - r.top);
                boolean isKeyboardShowing = heightDifference > screenHeight / 3;

                //键盘状态转变再调用监听
                // 如果之前软键盘状态为显示，现在为关闭，或者之前为关闭，现在为显示，则表示软键盘的状态发生了改变
                if (onKeyListener!=null&&isKeyboardShowing!=mIsSoftKeyboardShowing){
                    onKeyListener.onChange(isKeyboardShowing,heightDifference);
                    mIsSoftKeyboardShowing=isKeyboardShowing;
                }
            }
        };
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mRootView!=null){
            mRootView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }

    private KeyBoardChangeListener onKeyListener;

    public void setOnKeyListener(KeyBoardChangeListener onKeyListener) {
        this.onKeyListener = onKeyListener;
    }

    public boolean ismIsSoftKeyboardShowing() {
        return mIsSoftKeyboardShowing;
    }

    public interface KeyBoardChangeListener{
        void onChange(boolean isShow, int keyHeight);
    }
}
