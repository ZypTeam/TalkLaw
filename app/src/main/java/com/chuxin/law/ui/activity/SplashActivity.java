package com.chuxin.law.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.model.AdModel;
import com.chuxin.law.ry.SealAppContext;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.task.WeakHandler;

import io.rong.imkit.RongIM;
import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NET_SUC_CODE;

/**
 * @author wangcc
 * @date 2018/1/23
 * @describe
 */

public class SplashActivity extends BaseTalkLawActivity {
    //    private int net = 1, daley = 2;
//    private boolean netSuccess = false, handerSuc = false;
    private boolean isTime = false;
    private boolean isGetAd = false;

    private AdModel.AdDataModel adDataModel;
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            isTime = true;
            goNextActivity();
        }
    };
    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            /*if (msg.what == net) {
                netSuccess = true;
                if (handerSuc) {
                    goNextActivity();
                }
            } else if (msg.what == daley) {
                handerSuc = true;
                if (netSuccess) {
                    goNextActivity();
                }
            }*/
            return false;
        }
    });

    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initAction() {

        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        String cachedToken = sp.getString("loginToken", "");
        if (!TextUtils.isEmpty(cachedToken)) {
            RongIM.connect(cachedToken, SealAppContext.getInstance().getConnectCallback());
            handler.postDelayed(task, 2000);
//            handler.sendEmptyMessage(net);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goActivity(null, LoginActivity.class);
                }
            }, 1000);
//            handler.sendEmptyMessage(net);
        }

        changeNet();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(task);
    }

    private void goNextActivity() {

        if (!isTime || !isGetAd) return;
        if (!TextUtils.isEmpty(TalkLawApplication.getUserId())) {
            if(adDataModel!=null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("adDataModel",adDataModel);
                goActivity(bundle, AdActivity.class);
            }else{
                goActivity(null, HomeActivity.class);
            }
        } else {
            goActivity(null, LoginActivity.class);
        }
        onBackPressed();
    }


    private void changeNet() {
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).getAdNet()
                , new Action1<AdModel>() {
                    @Override
                    public void call(AdModel model) {
                        isGetAd = true;
                        hideLoadDialog();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            adDataModel = model.data;
                            goNextActivity();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        isGetAd = true;
                        goNextActivity();
                    }
                });
    }
}
