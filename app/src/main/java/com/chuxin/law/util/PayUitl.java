package com.chuxin.law.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.model.OrderPayModel;
import com.jusfoun.baselibrary.base.RxManage;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * @author wangcc
 * @date 2018/2/4
 * @describe 支付util
 */

public class PayUitl {

    public static final String ALIPAY="alipay";
    public static final String WECHATPAY="wechatpay";
    public static final String PAY_FIALD="pay_fiald";

    public static final void AliPay(final Activity activity, final String orderInfo){

        Runnable payRunnable=new Runnable() {
            @Override
            public void run() {
                PayTask alipay=new PayTask(activity);
                Map<String,String> result=alipay.payV2(orderInfo,true);
                Log.e("1234",orderInfo);
                String resultStatus=result.get("resultStatus");
                Log.e("zhifubao",resultStatus+"\n");
                for (Map.Entry<String, String> stringStringEntry : result.entrySet()) {
                    Log.e("zhifubao",stringStringEntry.getKey()+"==="+stringStringEntry.getValue()+"\n");
                }
                if (TextUtils.equals(resultStatus,"9000")){
                    //TODO://支付成功
                    RxManage rxManage=new RxManage();
                    rxManage.post(ALIPAY,result);
                }else {
                    //TODO://支付失败
                    RxManage rxManage=new RxManage();
                    rxManage.post(PayUitl.PAY_FIALD,"");
                }
            }
        };
        Thread payThread=new Thread(payRunnable);
        payThread.start();
    }

    public static final void WechatPay(OrderPayModel.WxPayOrderDic item){

        Context context= TalkLawApplication.getInstance();
        IWXAPI iwxapi= WXAPIFactory.createWXAPI(context,context.getString(R.string.key_wechat),false);
        if (!iwxapi.isWXAppInstalled()){
            Toast.makeText(context,"请安装微信",Toast.LENGTH_SHORT).show();
            return;
        }
        iwxapi.registerApp(context.getString(R.string.key_wechat));
        PayReq req=new PayReq();
        req.appId = item.getAppid();
        req.partnerId = item.getPartnerid();
        req.prepayId = item.getPrepayId();
        req.nonceStr = item.getNoncestr();
        req.timeStamp = item.getTimestamp();
        req.packageValue = item.getPackagestr();
        req.sign = item.getSign();
//        req.extData="app data";
        iwxapi.sendReq(req);

    }
}
