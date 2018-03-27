package com.chuxin.law.ui.activity;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.ry.SealAppContext;
import com.jusfoun.baselibrary.task.WeakHandler;

import com.chuxin.law.R;

import com.chuxin.law.base.BaseTalkLawActivity;

import io.rong.imkit.RongIM;

/**
 * @author wangcc
 * @date 2018/1/23
 * @describe
 */

public class SplashActivity extends BaseTalkLawActivity {
    private Runnable task=new Runnable() {
        @Override
        public void run() {
            goNextActivity();
        }
    };
    private WeakHandler handler=new WeakHandler();
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
            handler.postDelayed(task,3000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goActivity(null,LoginActivity.class);
                }
            }, 3000);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(task);
    }



    private void goNextActivity(){
        if (!TextUtils.isEmpty(TalkLawApplication.getUserId())){
            goActivity(null,HomeActivity.class);
        }else {
            goActivity(null,LoginActivity.class);
        }
        onBackPressed();
    }
}
