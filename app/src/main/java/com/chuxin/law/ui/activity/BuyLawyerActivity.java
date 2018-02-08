package com.chuxin.law.ui.activity;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author wangcc
 * @date 2018/2/8
 * @describe
 */

public class BuyLawyerActivity extends BaseTalkLawActivity {
    public static final String TYPE="type";
    public static final String PRICE="price";
    protected BackTitleView titleView;
    protected TextView price;
    protected TextView produte;
    protected TextView zhifubao;
    protected TextView weixin;
    protected CheckBox agree;
    protected Button login;

    private int type;
    private String mPrice;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_buy_lawyer;
    }

    @Override
    public void initDatas() {
        type=getIntent().getExtras().getInt(TYPE,0);
        mPrice=getIntent().getExtras().getString(PRICE);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.title_view);
        price = (TextView) findViewById(R.id.price);
        produte = (TextView) findViewById(R.id.produte);
        zhifubao = (TextView) findViewById(R.id.zhifubao);
        weixin = (TextView) findViewById(R.id.weixin);
        agree = (CheckBox) findViewById(R.id.agree);
        login = (Button) findViewById(R.id.login);

    }

    @Override
    public void initAction() {
        if (type==0){
            titleView.setTitle("律师介绍");
            produte.setText("图文咨询");
        }else if (type==1){
            produte.setText("购买价格");
            titleView.setTitle("购买");
        }
        price.setText("¥"+mPrice);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!agree.isChecked()){
                    showToast("请同意用户协议");
                    return;
                }

            }
        });

        zhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhifubao.setSelected(true);
                weixin.setSelected(false);
            }
        });

        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhifubao.setSelected(false);
                weixin.setSelected(true);
            }
        });

        zhifubao.setSelected(true);
        weixin.setSelected(false);
        setAgreeTxt();
    }

    private void setAgreeTxt(){
        String s="我同意《用户使用协议》";
        SpannableStringBuilder builder=new SpannableStringBuilder(s);
        int len1="我同意".length();
        int len2=s.length()-len1;
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#9b9b9b")),0,len1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#a26e71")),len1,len2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        agree.setText(builder);
    }

    private void pay(){
        if (type==0){
            goActivity(null,PaySucActivity.class);
        }else if (type==1){
            showToast("购买成功");
            setResult(RESULT_OK);
            onBackPressed();
        }
    }
}
