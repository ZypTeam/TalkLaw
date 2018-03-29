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
import com.chuxin.law.model.GuaranteeRequestModel;
import com.chuxin.law.model.LawyerIntroModel;
import com.chuxin.law.model.OrderResultModel;
import com.chuxin.law.model.PayValidateModel;
import com.chuxin.law.ry.my.mymessage.PayMessage;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.util.PayUitl;
import com.google.gson.Gson;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
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
    public static final String MARGIN = "isMargin";
    public static final String MARGIN_PRICE = "MARGIN_PRICE";
    public static final String MARGIN_ORDER = "MARGIN_ORDER";
    public static final String MARGIN_LAWYERID = "MARGIN_LAWYERID";
    public static final String MARGIN_TARGETID = "targetId";
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
    private String marginOrder = "";
    private String marginLawyerId = "";
    private String targetId = "";

    @Override
    public int getLayoutResId() {
        return R.layout.activity_buy_lawyer;
    }

    @Override
    public void initDatas() {
        data = (LawyerIntroModel.LawyerIntroData) getIntent().getExtras().getSerializable(DATA);
        type=getIntent().getExtras().getString(TYPE);
        isMargin = getIntent().getExtras().getBoolean(MARGIN,false);
        marginOrder = getIntent().getExtras().getString(MARGIN_ORDER);
        if(isMargin){
            mPrice = getIntent().getExtras().getString(MARGIN_PRICE);
            marginLawyerId = getIntent().getExtras().getString(MARGIN_LAWYERID);
            targetId = getIntent().getExtras().getString(MARGIN_TARGETID);
        }else{
            mPrice=data.getLaw().getPrice();
        }

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

        if(isMargin){
            titleView.setTitle("支付保证金");
            produte.setText("保证金");
        }

        price.setText("¥" + mPrice);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!agree.isChecked()) {
                    showToast("请同意用户协议");
                    return;
                }
                if(isMargin){
                    payMargin();
                }else {
                    buy();
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
                if(isMargin){
                    payMarginValidate("");
                }else {
                    payValidate("2","");
                }
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
                Log.e("tag","支付宝-===="+new Gson().toJson(o));
                if(isMargin){
                    payMarginValidate(new Gson().toJson(o));
                }else {
                    payValidate("1",new Gson().toJson(o));

                }

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

    private void payMargin() {
        HashMap<String, String> params = new HashMap<>();
        params.put("orderid", marginOrder);
        params.put("touserid", marginLawyerId);
        params.put("method", zhifubao.isSelected() ? "2" : "1");
        Log.e("tag", "params" + params);
        addNetwork(Api.getInstance().getService(ApiService.class).marginPayOrder(params)
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

    private void payValidate(String type,String alipay) {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("order", order);
        params.put("payType", type);
        params.put("alipay", alipay);

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


    /**
     *  保证金回调
     * */
    private void payMarginValidate(String alipay ) {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("order", order);
        params.put("alipay", alipay);

        addNetwork(Api.getInstance().getService(ApiService.class).checkOrder(params)
                , new Action1<GuaranteeRequestModel>() {
                    @Override
                    public void call(GuaranteeRequestModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode() == CommonConstant.NET_SUC_CODE) {
                            Log.e("tag", "checkOrder=" + noDataModel.data.state);
                            if(noDataModel.data!=null&&noDataModel.data.state>0){
                                showToast("支付成功");
                                sendPayMessage();
                                finish();
                            }else{
                                showToast(noDataModel.getMsg());
                            }
                        }else{
                            showToast(noDataModel.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }


    private void sendPayMessage(){
        PayMessage payMessage =  PayMessage.obtain("","",mPrice,"1");
        RongIM.getInstance().sendMessage(Message.obtain(targetId, Conversation.ConversationType.GROUP, payMessage),
                "已支付保证金", "已支付保证金", new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        Log.e("tag","RongIM-onAttached");
                    }

                    @Override
                    public void onSuccess(Message message) {
                        hideLoadDialog();
                        finish();
                        Log.e("tag","RongIM-onSuccess");
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        hideLoadDialog();
                        showToast("申请失败，请重试");
                        Log.e("tag","RongIM-onError="+new Gson().toJson(message)+" "+errorCode);
                    }
                });
    }
}
