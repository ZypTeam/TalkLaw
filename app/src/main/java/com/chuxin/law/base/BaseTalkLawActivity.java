package com.chuxin.law.base;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chuxin.law.R;
import com.chuxin.law.audioplayer.receiver.AudioBroadcastReceiver;
import com.chuxin.law.ui.dialog.LoadingDialog;
import com.jusfoun.baselibrary.base.BaseActivity;
import com.jusfoun.baselibrary.permissiongen.PermissionGen;
import com.umeng.analytics.MobclickAgent;

/**
 * @author wangcc
 * @date 2017/11/17
 * @describe 说法 activity基类
 */

public abstract class BaseTalkLawActivity extends BaseActivity {

    private LoadingDialog loadingDialog;
    private KeyguardManager mKeyguardManager;
    private PowerManager pm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        initDialog();
        initDatas();
        initView();
        initAction();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private void initDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this, R.style.my_dialog);
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    }

    protected void showLoadDialog() {
        if (mContext == null) {
            Log.e("TAG", "该Activity已经销毁，但仍欲显示dialog");
            return;
        }
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    protected void hideLoadDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(mContext);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(mContext);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (pm != null) {
            boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
            if (!isScreenOn) {
                Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_PAUSEMUSIC);
                resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                mContext.sendBroadcast(resumeIntent);
            }
        }

        if(mKeyguardManager.inKeyguardRestrictedInputMode()){
            Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_PAUSEMUSIC);
            resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            mContext.sendBroadcast(resumeIntent);
        }
    }
}
