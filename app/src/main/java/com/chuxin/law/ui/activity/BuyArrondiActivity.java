package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author wangcc
 * @date 2018/3/25
 * @describe
 */

public class BuyArrondiActivity extends BaseTalkLawActivity {

    public static final String PRICE="price";
    public static final String TYPE="type";
    protected BackTitleView titleView;
    protected ImageView iconBuy;
    protected TextView content;
    protected View viewBg;
    protected TextView price;
    protected TextView buy;

    private String priceNum;
    private int type;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_buy_arrondi;
    }

    @Override
    public void initDatas() {
        priceNum=getIntent().getStringExtra(PRICE);
        type=getIntent().getIntExtra(TYPE,0);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.title_view);
        iconBuy = (ImageView) findViewById(R.id.icon_buy);
        content = (TextView) findViewById(R.id.content);
        viewBg = (View) findViewById(R.id.view_bg);
        price = (TextView) findViewById(R.id.price);
        buy = (TextView) findViewById(R.id.buy);

    }

    @Override
    public void initAction() {
        titleView.setTitle("购买");

        if (type==1){
            content.setText("私人顾问");
        }else {
            content.setText("公司顾问");
        }

        price.setText("¥"+priceNum);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt(PayArrondiActivity.TYPE,1);
                bundle.putString(PayArrondiActivity.PRICE,priceNum);
                goActivityForResult(bundle,PayArrondiActivity.class, CommonConstant.REQUEST_PAY_SUCCUSE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=RESULT_OK){
            return;
        }
        if (requestCode==CommonConstant.REQUEST_PAY_SUCCUSE){
            onBackPressed();
        }
    }
}
