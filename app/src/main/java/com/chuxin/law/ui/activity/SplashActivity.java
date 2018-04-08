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
    private int net = 1, daley = 2;
    private boolean netSuccess = false, handerSuc = false;
    private GratuityDialog dialog;
    private String url;
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            goNextActivity();
        }
    };
    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == net) {
                netSuccess = true;
                if (handerSuc) {
                    goNextActivity();
                }
            } else if (msg.what == daley) {
                handerSuc = true;
                if (netSuccess) {
                    goNextActivity();
                }
            }
            return false;
        }
    });

    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initDatas() {
        dialog = new GratuityDialog(mContext);
        dialog.setContent("有新版本更新了");
        dialog.setOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ApkDownloadService.getState(mContext)) {
                    Intent intent = new Intent(mContext, ApkDownloadService.class);
                    intent.putExtra(ApkDownloadService.DOWNLOAD_URL, url);
                    intent.putExtra(ApkDownloadService.NOTIFICATION_ID, 20);
                    startService(intent);
                }
                handler.sendEmptyMessage(net);
                dialog.dismiss();
            }
        });

        dialog.setOnCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(net);
                dialog.dismiss();
            }
        });
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
            handler.postDelayed(task, 3000);
            handler.sendEmptyMessage(net);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goActivity(null, LoginActivity.class);
                }
            }, 3000);
            handler.sendEmptyMessage(net);
        }

        getVersion();
    }

    private void getVersion() {
        HashMap<String, String> params = new HashMap<>();
        params.put("versioncode", AppUtil.getVersionCode(mContext) + "");
        addNetwork(Api.getInstance().getService(ApiService.class).getVersion(params), new Action1<VersionModel>() {
            @Override
            public void call(VersionModel model) {
                if (model.getCode() == CommonConstant.NET_SUC_CODE) {
                    if (model.getVersiondata() != null) {
                        sentMsg(model.getVersiondata());
                    } else {
                        if (!TextUtils.isEmpty(model.getMsg())) {
                            showToast(model.getMsg());
                        }
                        handler.sendEmptyMessage(net);
                    }
                } else {
                    if (TextUtils.isEmpty(model.getMsg())) {
                        showToast(model.getMsg());
                        handler.sendEmptyMessage(net);
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                handler.sendEmptyMessage(net);
            }
        });
    }

    private void sentMsg(final VersionDataModel versionDataModel) {

        url = versionDataModel.getUrl();
        dialog.show();
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
