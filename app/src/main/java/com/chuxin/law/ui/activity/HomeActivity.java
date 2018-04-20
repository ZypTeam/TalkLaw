package com.chuxin.law.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.audioplayer.manage.AudioPlayerManager;
import com.chuxin.law.audioplayer.service.AudioPlayerService;
import com.chuxin.law.audioplayer.util.AudioPlayUtils;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.common.CommonLogic;
import com.chuxin.law.model.VersionDataModel;
import com.chuxin.law.model.VersionModel;
import com.chuxin.law.receiver.ScreenBroadcastReceiver;
import com.chuxin.law.ui.adapter.HomeAdapter;
import com.chuxin.law.ui.dialog.GratuityDialog;
import com.chuxin.law.update.ApkDownloadService;
import com.jusfoun.baselibrary.Util.AppUtil;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.permissiongen.PermissionFail;
import com.jusfoun.baselibrary.permissiongen.PermissionGen;
import com.jusfoun.baselibrary.permissiongen.PermissionSuccess;
import com.jusfoun.baselibrary.view.HomeViewPager;

import java.util.HashMap;

import rx.functions.Action1;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2115:44
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class HomeActivity extends BaseTalkLawActivity {

    private HomeViewPager viewPager;
    private HomeAdapter adapter;
    private LinearLayout opinionLayout, statementLayout, myLayout;
    private ImageView opinionImg, statementImg, myImg;
    private TextView opinionText, statementText, myText;

    private ScreenBroadcastReceiver screenReceiver;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    public void initDatas() {
        adapter = new HomeAdapter(getSupportFragmentManager());
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
                dialog.dismiss();
            }
        });

        dialog.setOnCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void initView() {
        viewPager = (HomeViewPager) findViewById(R.id.viewpager);
        opinionLayout = (LinearLayout) findViewById(R.id.layout_opinion);
        statementLayout = (LinearLayout) findViewById(R.id.layout_statement);
        myLayout = (LinearLayout) findViewById(R.id.layout_my);
        opinionImg = (ImageView) findViewById(R.id.img_opinion);
        statementImg = (ImageView) findViewById(R.id.img_stetement);
        myImg = (ImageView) findViewById(R.id.img_my);

        opinionText = (TextView) findViewById(R.id.text_opinion);
        statementText = (TextView) findViewById(R.id.text_statement);
        myText = (TextView) findViewById(R.id.text_my);

    }

    @Override
    public void initAction() {
        viewPager.setNotTouchScoll(true);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        opinionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnState(0);
                viewPager.setCurrentItem(0, false);
            }
        });

        statementLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnState(1);
                viewPager.setCurrentItem(1, false);
            }
        });

        myLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnState(2);
                viewPager.setCurrentItem(2, false);
            }
        });


        viewPager.setOffscreenPageLimit(3);

        initService();

//        registerBroadcastReceiver();
        if (!PermissionGen.checkPermissions(mContext,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            PermissionGen.with(this).addRequestCode(100)
                    .permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
                    .request();
        } else {
            getVersion();
        }
    }

    @PermissionFail(requestCode = 100)
    private void perFail() {

    }

    @PermissionSuccess(requestCode = 100)
    private void perSuc() {
        getVersion();
    }

    private void initService() {
        Intent playerServiceIntent = new Intent(this, AudioPlayerService.class);
        TalkLawApplication.getBaseApplication().startService(playerServiceIntent);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TalkLawApplication.getBaseApplication().startForegroundService(playerServiceIntent);
        } else {
            TalkLawApplication.getBaseApplication().startService(playerServiceIntent);
        }*/

        AudioPlayUtils.getInstance().setPlayStatus(AudioPlayerManager.STOP);
    }

    public void setBtnState(int index) {
        opinionImg.setImageResource(R.mipmap.img_opinion);
        statementImg.setImageResource(R.mipmap.img_statement);
        myImg.setImageResource(R.mipmap.img_my);

        opinionText.setTextColor(getResources().getColor(R.color.text_home_btn_no));
        statementText.setTextColor(getResources().getColor(R.color.text_home_btn_no));
        myText.setTextColor(getResources().getColor(R.color.text_home_btn_no));
        if (index == 0) {
            opinionImg.setImageResource(R.mipmap.img_opinion_select);
            opinionText.setTextColor(getResources().getColor(R.color.text_home_btn_select));
        } else if (index == 1) {
            statementImg.setImageResource(R.mipmap.img_statement_select);
            statementText.setTextColor(getResources().getColor(R.color.text_home_btn_select));
        } else if (index == 2) {
            myImg.setImageResource(R.mipmap.img_my_select);
            myText.setTextColor(getResources().getColor(R.color.text_home_btn_select));
        }
    }

    @Override
    public boolean isNeedSwipe() {
        return false;
    }

    private long mLastTime;

    /**
     * 退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mLastTime > 0 && System.currentTimeMillis() - mLastTime <= 2000) {
                TalkLawApplication.getBaseApplication().removeAll();
            } else {
                Toast.makeText(mContext, R.string.app_exit_string, Toast.LENGTH_SHORT).show();
                mLastTime = System.currentTimeMillis();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unRegisterBroadcastReceiver();
        CommonLogic.getInstance().setLawyerProductData(null);
        AudioPlayUtils.getInstance().setPlayStatus(AudioPlayerManager.STOP);
        Intent playerServiceIntent = new Intent(this, AudioPlayerService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopService(playerServiceIntent);
        } else {
            stopService(playerServiceIntent);
        }
    }

    private GratuityDialog dialog;
    private String url;

    private void getVersion() {
        HashMap<String, String> params = new HashMap<>();
        params.put("versioncode", AppUtil.getVersionCode(mContext) + "");
        addNetwork(Api.getInstance().getService(ApiService.class).getVersion(params), new Action1<VersionModel>() {
            @Override
            public void call(VersionModel model) {
                if (model.getCode() == CommonConstant.NET_SUC_CODE) {
                    if (model.getVersiondata() != null) {
                        if (AppUtil.getVersionCode(mContext) < model.getVersiondata().versioncode)
                            sentMsg(model.getVersiondata());
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    /**
     * 启动screen状态广播接收器
     */
    private void registerBroadcastReceiver() {
        screenReceiver=new ScreenBroadcastReceiver();
        screenReceiver.setScreenStateListener(new ScreenBroadcastReceiver.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                LogUtil.e(TAG,"onScreenOn");
            }

            @Override
            public void onScreenOff() {
                LogUtil.e(TAG,"onScreenOff");
            }

            @Override
            public void onUserPresent() {
                LogUtil.e(TAG,"onUserPresent");
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenReceiver, filter);
    }

    private void unRegisterBroadcastReceiver(){
        if (screenReceiver!=null) {
            unregisterReceiver(screenReceiver);
        }
    }

    private void sentMsg(final VersionDataModel versionDataModel) {
//        url = "http://ojprcxbjz.bkt.clouddn.com/app-%E5%AE%98%E6%96%B9-debug.apk";
        url = versionDataModel.getUrl();
        dialog.setContent(versionDataModel.getDescribe());
        dialog.setTitle(versionDataModel.getTitle());
        dialog.show();
    }
}
