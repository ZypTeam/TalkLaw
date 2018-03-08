package com.chuxin.law.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.util.PayUitl;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Author  wangchenchen
 * CreateDate 2016/12/28.
 * Email wcc@jusfoun.com
 * Description
 */
public class WXPayEntryActivity extends BaseTalkLawActivity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("aaaaa","create");
        super.onCreate(savedInstanceState);
        iwxapi= WXAPIFactory.createWXAPI(this, null);
        iwxapi.registerApp(getString(R.string.key_wechat));
        iwxapi.handleIntent(getIntent(),this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.weixin_pay;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initAction() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e("aaaaa","baseResp=="+baseResp.errCode);
        switch (baseResp.errCode){
            case 0:
                rxManage.post(PayUitl.WECHATPAY,"");
                break;
            case -1:
                rxManage.post(PayUitl.PAY_FIALD,"");
                break;
            case -2:
                rxManage.post(PayUitl.PAY_FIALD,"");
                break;
        }
//        finish();
    }
}
