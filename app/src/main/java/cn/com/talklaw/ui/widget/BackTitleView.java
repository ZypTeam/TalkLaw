package cn.com.talklaw.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jusfoun.baselibrary.widget.TitleStatusBarView;

import cn.com.talklaw.R;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe
 */

public class BackTitleView extends FrameLayout {
    protected ImageView back,right;
    protected TextView title;
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
        right.setVisibility(GONE);
    }

    private void initAction(){
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
}
