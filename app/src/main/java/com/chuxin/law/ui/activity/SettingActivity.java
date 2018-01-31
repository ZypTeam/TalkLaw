package com.chuxin.law.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.chuxin.law.TalkLawApplication;

import com.chuxin.law.R;

import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe 设置
 */

public class SettingActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected TextView clearCache;
    protected TextView cacheCount;
    protected TextView help;
    protected TextView feedback;
    protected TextView pingfen;
    protected TextView aboutUs;
    protected TextView exit;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        clearCache = (TextView) findViewById(R.id.clear_cache);
        cacheCount = (TextView) findViewById(R.id.cache_count);
        help = (TextView) findViewById(R.id.help);
        feedback = (TextView) findViewById(R.id.feedback);
        pingfen = (TextView) findViewById(R.id.pingfen);
        aboutUs = (TextView) findViewById(R.id.about_us);
        exit = (TextView) findViewById(R.id.exit);

    }

    @Override
    public void initAction() {

        titleView.setTitle("设置");
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(null,LoginActivity.class);
                finish();
                TalkLawApplication.exitUser();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackAPI.openFeedbackActivity();
            }
        });
    }
}
