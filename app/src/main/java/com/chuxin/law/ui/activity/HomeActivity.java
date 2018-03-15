package com.chuxin.law.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.TalkLawApplication;

import com.chuxin.law.R;

import com.chuxin.law.audioplayer.manage.AudioPlayerManager;
import com.chuxin.law.audioplayer.service.AudioPlayerService;
import com.chuxin.law.audioplayer.util.AudioPlayUtils;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.adapter.HomeAdapter;
import com.chuxin.law.util.voice.VoiceHelper;
import com.jusfoun.baselibrary.permissiongen.PermissionFail;
import com.jusfoun.baselibrary.permissiongen.PermissionGen;
import com.jusfoun.baselibrary.permissiongen.PermissionSuccess;
import com.jusfoun.baselibrary.view.HomeViewPager;

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

    @Override
    public int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    public void initDatas() {
        adapter = new HomeAdapter(getSupportFragmentManager());
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

        PermissionGen.with(this).addRequestCode(100)
                .permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
                .request();
    }

    @PermissionFail(requestCode = 100)
    private void perFail() {

    }

    @PermissionSuccess(requestCode = 100)
    private void perSuc() {

    }

    private void initService(){
        Intent playerServiceIntent = new Intent(this, AudioPlayerService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TalkLawApplication.getBaseApplication().startForegroundService(playerServiceIntent);
        } else {
            TalkLawApplication.getBaseApplication().startService(playerServiceIntent);
        }

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
    protected void onDestroy() {
        super.onDestroy();
        Intent playerServiceIntent = new Intent(this, AudioPlayerService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TalkLawApplication.getBaseApplication().stopService(playerServiceIntent);
        } else {
            TalkLawApplication.getBaseApplication().stopService(playerServiceIntent);
        }
    }
}
