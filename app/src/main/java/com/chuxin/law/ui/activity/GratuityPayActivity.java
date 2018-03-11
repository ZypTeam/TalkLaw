package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.model.OrderResultModel;
import com.chuxin.law.model.PayValidateModel;
import com.chuxin.law.ui.widget.BackTitleView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.util.PayUitl;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2018/1/24
 * @describe 打赏支付
 */

public class GratuityPayActivity extends BaseTalkLawActivity {
    public static final String DATA="data";
    public static final String PRICE="price";
    protected BackTitleView titleBar;
    protected ImageView iconGratuity;
    protected TextView price;
    protected TextView wechat;
    protected TextView zhifubao;
    protected TextView help;
    private LawyerProductModel.LawyerProductData data;
    private String order;
    private String gratuityPrice;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gratuity_pay;
    }

    @Override
    public void initDatas() {
        data= (LawyerProductModel.LawyerProductData) getIntent().getSerializableExtra(DATA);
        gratuityPrice=getIntent().getStringExtra(PRICE);
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

        zhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gratuity("1");
            }
        });

        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gratuity("2");
            }
        });

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

    private void payValidate(String type) {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("order", order);
//        params.put("payType", type);
        addNetwork(Api.getInstance().getService(ApiService.class).rewardOrder(params)
                , new Action1<PayValidateModel>() {
                    @Override
                    public void call(PayValidateModel model) {
                        hideLoadDialog();
                        if (model.getCode() == CommonConstant.NET_SUC_CODE&&model.getData()!=null) {
                            if (model.getData().getState()==1) {
//                                pay();
                                showToast("打赏成功");
                                setResult(RESULT_OK);
                                onBackPressed();
                            }else {
                                showToast(model.getMsg());
                            }
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

    public void gratuity(final String method){
        if (StringUtil.isEmpty(gratuityPrice)){
            showToast("打赏金额不能为空");
            return;
        }
        if (data==null||data.getArticle()==null){
            showToast("连接服务器失败");
            return;
        }
        showLoadDialog();
        Map<String,String> params=new HashMap<>();
        params.put("artid",data.getArticle().getId());
        params.put("money",gratuityPrice);
        params.put("method",method);
        addNetwork(Api.getInstance().getService(ApiService.class).gratuityOrder(params)
                , new Action1<OrderResultModel>() {
                    @Override
                    public void call(OrderResultModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode()== CommonConstant.NET_SUC_CODE
                                &&noDataModel.getData()!=null){
                            if (StringUtil.equals("1",method)
                                    &&noDataModel.getData().getOrder()!=null){
                                order=noDataModel.getData().getOrder().getPartnerid();
                                PayUitl.AliPay(GratuityPayActivity.this,noDataModel.getData().getOrder().getPartnerid());
                                return;
                            }

                            if (StringUtil.equals("2",method)
                                    &&noDataModel.getData().getWxorder()!=null){
                                order=noDataModel.getData().getWxorder().getPrepay_id();
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
                        showToast("连接服务器失败，请重新确认");
                    }
                });
    }
}
