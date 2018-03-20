package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.LawyerIntroModel;
import com.chuxin.law.model.OrderResultModel;
import com.chuxin.law.model.PayValidateModel;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.util.PayUitl;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2018/3/12
 * @describe
 */

public class BuyIntroductionActivity extends BaseTalkLawActivity {
    public static final String TYPE = "type";
    public static final String PRICE = "price";
    public static final String DATA = "data";
    protected BackTitleView titleView;
    protected TextView price,agree_btn;
    protected TextView produte;
    protected TextView zhifubao;
    protected TextView weixin;
    protected CheckBox agree;
    protected Button login;
    private String type;

    private String mPrice;
    private LawyerIntroModel.LawyerIntroData data;
    private String order;

    private boolean isMargin = false;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_buy_lawyer;
    }

    @Override
    public void initDatas() {
        data = (LawyerIntroModel.LawyerIntroData) getIntent().getExtras().getSerializable(DATA);
        type=getIntent().getExtras().getString(TYPE);
        mPrice=data.getLaw().getPrice();
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.title_view);
        price = (TextView) findViewById(R.id.price);
        produte = (TextView) findViewById(R.id.produte);
        zhifubao = (TextView) findViewById(R.id.zhifubao);
        weixin = (TextView) findViewById(R.id.weixin);
        agree = (CheckBox) findViewById(R.id.agree);
        agree_btn = (TextView) findViewById(R.id.agree_btn);
        login = (Button) findViewById(R.id.login);

    }

    @Override
    public void initAction() {
        titleView.setTitle("律师介绍");
        produte.setText("图文咨询");

        price.setText("¥" + mPrice);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!agree.isChecked()) {
                    showToast("请同意用户协议");
                    return;
                }

                buy();

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

        agree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,WebViewActivity.class);
                intent.putExtra("url","http://api.law.wzgeek.com/html/pay.html");
                intent.putExtra("title","用户协议");
                startActivity(intent);
            }
        });

        zhifubao.setSelected(true);
        weixin.setSelected(false);
        setAgreeTxt();

        rxManage.on(PayUitl.WECHATPAY, new Action1<Object>() {
            @Override
            public void call(Object o) {
                payValidate("2");
            }
        });

        rxManage.on(PayUitl.PAY_FIALD, new Action1<Object>() {
            @Override
            public void call(Object o) {
                showToast("支付失败");
            }
        });

        rxManage.on(PayUitl.ALIPAY, new Action1<Object>() {
            @Override
            public void call(Object o) {
//                Map<String,String> map= (Map<String, String>) o;
                payValidate("1");

            }
        });
    }

    private void buy() {
        HashMap<String, String> params = new HashMap<>();
        params.put("touserid", data.getLaw().getUserid());
        params.put("method", zhifubao.isSelected() ? "2" : "1");
        params.put("type",type);
        Log.e("tag", "params" + params);
        addNetwork(Api.getInstance().getService(ApiService.class).consultSet(params)
                , new Action1<OrderResultModel>() {
                    @Override
                    public void call(OrderResultModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode() == CommonConstant.NET_SUC_CODE
                                && noDataModel.getData() != null) {
                            if (zhifubao.isSelected()
                                    && noDataModel.getData().getOrder() != null) {
                                order=noDataModel.getData().getOrder().getOrder();
                                PayUitl.AliPay(BuyIntroductionActivity.this, noDataModel.getData().getAporder());
                                return;
                            }

                            if (weixin.isSelected()
                                    && noDataModel.getData().getWxorder() != null) {
                                order = noDataModel.getData().getWxorder().getPrepay_id();
                                PayUitl.WechatPay(noDataModel.getData().getWxorder());
                                return;
                            }

                        }
                        showToast(noDataModel.getMsg());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void setAgreeTxt() {
        String s = "我同意《用户使用协议》";
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        int len1 = "我同意".length();
        int len2 = s.length();
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#9b9b9b")), 0, len1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#a26e71")), len1, len2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        agree_btn.setText(builder);
    }

    private void payValidate(String type) {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("order", order);
        params.put("payType", type);
        addNetwork(Api.getInstance().getService(ApiService.class).consultOrder(params)
                , new Action1<PayValidateModel>() {
                    @Override
                    public void call(PayValidateModel model) {
                        hideLoadDialog();
                        if (model.getCode() == CommonConstant.NET_SUC_CODE ) {
                            Intent intent=new Intent();
                            intent.setClass(mContext,PaySucActivity.class);
                            intent.putExtra(PaySucActivity.RONGID,model.getData().id);
                            intent.putExtra(PaySucActivity.DATA,data);
                            mContext.startActivity(intent);
                            onBackPressed();
                        } else {
                            showToast(model.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }
}
