package com.chuxin.law.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chuxin.law.R;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
