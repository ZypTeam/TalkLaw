package com.chuxin.law.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.ui.dialog.GratuityDialog;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

import static com.chuxin.law.ui.activity.LawyerDefautActivity.ID;

/**
 * @author wangcc
 * @date 2018/1/24
 * @describe 打赏
 */

public class GratuityActivity extends BaseTalkLawActivity implements View.OnClickListener {
    public static final String DATA="data";
    protected BackTitleView titleBar;
    protected ImageView iconGratuity;
    protected TextView content;
    protected TextView one;
    protected TextView two;
    protected TextView three;
    protected TextView four;
    protected TextView five;
    protected TextView six;
    protected TextView help;
    private String id;
    private String price="5";
    private TextView submit;
    private LawyerProductModel.LawyerProductData data;
    private GratuityDialog dialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gratuity;
    }

    @Override
    public void initDatas() {
        data= (LawyerProductModel.LawyerProductData) getIntent().getSerializableExtra(DATA);
        if (data!=null&&data.getLawyer()!=null) {
            id = data.getLawyer().getUserid();
        }
        dialog=new GratuityDialog(mContext);
        dialog.setContent("使用帮助");
    }

    @Override
    public void initView() {
        titleBar = (BackTitleView) findViewById(R.id.title_view);
        iconGratuity = (ImageView) findViewById(R.id.icon_gratuity);
        content = (TextView) findViewById(R.id.content);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);
        five = (TextView) findViewById(R.id.five);
        six = (TextView) findViewById(R.id.six);
        help = (TextView) findViewById(R.id.help);
        submit = (TextView) findViewById(R.id.submit);
    }

    @Override
    public void initAction() {
        titleBar.setTitle("打赏");
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        if (data!=null&&data.getArticle()!=null){
            content.setText(data.getArticle().getTitle());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gratuity();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog!=null){
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= RESULT_OK){
            return;
        }
        if (requestCode==CommonConstant.REQUEST_LAWYER_GRATUITY){
            onBackPressed();
        }else if (requestCode==CommonConstant.REQUEST_GRATUITY_PRICE){
            if (data!=null){
                price=data.getStringExtra("price");
            }
        }

    }

    public void gratuity(){
        if (StringUtil.isEmpty(price)){
            showToast("打赏金额不能为空");
            return;
        }
        showLoadDialog();
        Map<String,String> params=new HashMap<>();
        params.put("artid",id);
        params.put("money",price);
        addNetwork(Api.getInstance().getService(ApiService.class).gratuityOrder(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode()==CommonConstant.NET_SUC_CODE){
                            Intent intent=new Intent();
                            intent.putExtra(GratuityPayActivity.ID,id);
                            intent.putExtra(GratuityPayActivity.PRICE,price);
                            goActivityForResult(null,GratuityPayActivity.class, CommonConstant.REQUEST_LAWYER_GRATUITY);
                        }else {
                            showToast(noDataModel.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        showToast("连接服务器失败，请重新确认");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        init();
        switch (v.getId()){
            case R.id.one:
                one.setTextColor(Color.WHITE);
                one.setBackgroundResource(R.drawable.bg_gratuity_price_select);
                price="1";
                break;
            case R.id.two:
                two.setTextColor(Color.WHITE);
                two.setBackgroundResource(R.drawable.bg_gratuity_price_select);
                price="5";
                break;
            case R.id.three:
                three.setTextColor(Color.WHITE);
                three.setBackgroundResource(R.drawable.bg_gratuity_price_select);
                price="10";
                break;
            case R.id.four:
                four.setTextColor(Color.WHITE);
                four.setBackgroundResource(R.drawable.bg_gratuity_price_select);
                price="50";
                break;
            case R.id.five:
                five.setTextColor(Color.WHITE);
                five.setBackgroundResource(R.drawable.bg_gratuity_price_select);
                price="100";
                break;
            case R.id.six:
                six.setTextColor(Color.WHITE);
                six.setBackgroundResource(R.drawable.bg_gratuity_price_select);
                Bundle bundle = new Bundle();
                bundle.putInt(EditUserInfoActivity.UPDATE_TYPE, 5);
                bundle.putString(EditUserInfoActivity.UPDATE_VALUE, "");
                goActivityForResult(bundle, EditUserInfoActivity.class, CommonConstant.REQUEST_GRATUITY_PRICE);
                break;
        }
    }

    private void init(){
        one.setTextColor(Color.parseColor("#666666"));
        one.setBackgroundResource(R.drawable.bg_gratuity_price);
        two.setTextColor(Color.parseColor("#666666"));
        two.setBackgroundResource(R.drawable.bg_gratuity_price);
        three.setTextColor(Color.parseColor("#666666"));
        three.setBackgroundResource(R.drawable.bg_gratuity_price);
        four.setTextColor(Color.parseColor("#666666"));
        four.setBackgroundResource(R.drawable.bg_gratuity_price);
        five.setTextColor(Color.parseColor("#666666"));
        five.setBackgroundResource(R.drawable.bg_gratuity_price);
        six.setTextColor(Color.parseColor("#666666"));
        six.setBackgroundResource(R.drawable.bg_gratuity_price);
    }
}
