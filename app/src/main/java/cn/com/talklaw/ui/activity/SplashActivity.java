package cn.com.talklaw.ui.activity;

import android.text.TextUtils;

import com.jusfoun.baselibrary.task.WeakHandler;

import cn.com.talklaw.R;
import cn.com.talklaw.TalkLawApplication;
import cn.com.talklaw.base.BaseTalkLawActivity;

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
        handler.postDelayed(task,1000);
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
