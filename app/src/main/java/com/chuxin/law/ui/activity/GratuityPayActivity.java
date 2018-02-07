package com.chuxin.law.ui.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.ui.widget.BackTitleView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;

/**
 * @author wangcc
 * @date 2018/1/24
 * @describe 打赏支付
 */

public class GratuityPayActivity extends BaseTalkLawActivity {
    public static final String ID="id";
    public static final String PRICE="price";
    public static final String ORDER="order";
    protected BackTitleView titleBar;
    protected ImageView iconGratuity;
    protected TextView price;
    protected TextView wechat;
    protected TextView zhifubao;
    protected TextView help;
    private String id;
    private String mPrice;
    private String order;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gratuity_pay;
    }

    @Override
    public void initDatas() {

        id=getIntent().getStringExtra(ID);
        mPrice=getIntent().getStringExtra(PRICE);
        order=getIntent().getStringExtra(ORDER);
    }

    @Override
    public void initView() {
        titleBar = (BackTitleView) findViewById(R.id.title_view);
        iconGratuity = (ImageView) findViewById(R.id.icon_gratuity);
        price = (TextView) findViewById(R.id.price);
        wechat = (TextView) findViewById(R.id.wechat);
        zhifubao = (TextView) findViewById(R.id.zhifubao);
        help = (TextView) findViewById(R.id.help);

    }

    @Override
    public void initAction() {
        titleBar.setTitle("打赏");
    }

    private void pay(){

    }
}
