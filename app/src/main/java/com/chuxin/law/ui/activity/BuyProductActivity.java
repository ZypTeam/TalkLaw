package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author wangcc
 * @date 2018/1/26
 * @describe
 */

public class BuyProductActivity extends BaseTalkLawActivity {
    public static final String DATA = "data";
    protected BackTitleView titleBar;
    protected ImageView iconBuy;
    protected View viewBg;
    protected TextView buy,content,price;
    private LawyerProductModel.LawyerProductData data;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_buy_product;
    }

    @Override
    public void initDatas() {
        data= (LawyerProductModel.LawyerProductData) getIntent().getSerializableExtra(DATA);
    }

    @Override
    public void initView() {
        titleBar = (BackTitleView) findViewById(R.id.title_view);
        iconBuy = (ImageView) findViewById(R.id.icon_buy);
        viewBg = (View) findViewById(R.id.view_bg);
        buy = (TextView) findViewById(R.id.buy);
        content = (TextView) findViewById(R.id.content);
        price = (TextView) findViewById(R.id.price);
    }

    @Override
    public void initAction() {
        titleBar.setTitle("购买");
        if (data!=null&&data.getArticle()!=null){
            content.setText(data.getArticle().getTitle());
            price.setText("¥"+data.getArticle().getPrice());
        }
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data==null||data.getArticle()==null){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putInt(BuyLawyerActivity.TYPE,1);
                bundle.putString(BuyLawyerActivity.PRICE,data.getArticle().getPrice());
                goActivityForResult(bundle,BuyLawyerActivity.class, CommonConstant.REQUEST_PAY_SUCCUSE);
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

    private void buy(){
        showToast("购买失败");
    }
}
