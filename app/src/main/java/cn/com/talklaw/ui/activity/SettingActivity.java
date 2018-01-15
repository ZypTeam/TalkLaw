package cn.com.talklaw.ui.activity;

import android.widget.TextView;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.widget.BackTitleView;

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

    }

    @Override
    public void initAction() {

        titleView.setTitle("设置");
    }
}
