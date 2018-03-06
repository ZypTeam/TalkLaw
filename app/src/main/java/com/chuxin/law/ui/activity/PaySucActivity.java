package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.task.WeakHandler;


/**
 * @author wangcc
 * @date 2018/2/9
 * @describe 支付成功
 */

public class PaySucActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected TextView suc;
    protected TextView timer;
    protected TextView click;
    private int second=5;

    private WeakHandler handler=new WeakHandler();
    private Runnable task=new Runnable() {
        @Override
        public void run() {
            if (second==0){
                handler.removeCallbacks(task);
                goNext();
                return;
            }
            second--;
            timer.setText(getTimerTxt(second));
            handler.postDelayed(task,1000);
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_pay_suc;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.title_view);
        suc = (TextView) findViewById(R.id.suc);
        timer = (TextView) findViewById(R.id.timer);
        click = (TextView) findViewById(R.id.click);

    }

    @Override
    public void initAction() {
        titleView.setTitle("支付成功");

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(task);
                goNext();
            }
        });

        timer.setText(getTimerTxt(second));
        handler.postDelayed(task,1000);
    }

    private void goNext(){
//        Intent intent  = new Intent(mContext, ChatActivity.class);
//        intent.putExtra("userId", "20");
//        intent.putExtra("userName", "王律师");
//        startActivity(intent);
        onBackPressed();
    }

    private SpannableStringBuilder getTimerTxt(int second){
        SpannableStringBuilder builder=new SpannableStringBuilder(second+"秒后自动进入咨询页面...");
        int len1=(second+"秒").length();
        int len2="后自动进入咨询页面...".length()+len1;

        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#fda263")),0,len1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")),len1,len2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
}
