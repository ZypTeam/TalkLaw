package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.VersionDataModel;
import com.chuxin.law.model.VersionModel;
import com.chuxin.law.ry.SealAppContext;
import com.chuxin.law.ui.dialog.GratuityDialog;
import com.chuxin.law.update.ApkDownloadService;
import com.jusfoun.baselibrary.Util.AppUtil;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.task.WeakHandler;

import com.chuxin.law.R;

import com.chuxin.law.base.BaseTalkLawActivity;

import java.util.HashMap;

import io.rong.imkit.RongIM;
import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2018/1/23
 * @describe
 */

public class SplashActivity extends BaseTalkLawActivity {
//    private int net = 1, daley = 2;
//    private boolean netSuccess = false, handerSuc = false;

    private Runnable task = new Runnable() {
        @Override
        public void run() {
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
            handler.postDelayed(task, 1000);
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(task);
    }

    private void goNextActivity() {
        if (!TextUtils.isEmpty(TalkLawApplication.getUserId())) {
            goActivity(null, HomeActivity.class);
        } else {
            goActivity(null, LoginActivity.class);
        }
        onBackPressed();
    }
}
