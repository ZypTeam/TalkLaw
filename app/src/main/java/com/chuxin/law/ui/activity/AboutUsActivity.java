package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.util.UIUtils;
import com.jusfoun.baselibrary.Util.AppUtil;

/**
 * @author wangcc
 * @date 2018/3/5
 * @describe 关于我们
 */

public class AboutUsActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected TextView version;
    protected TextView aboutUs;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.title_view);
        version = (TextView) findViewById(R.id.version);
        aboutUs = (TextView) findViewById(R.id.about_us);

    }

    @Override
    public void initAction() {
        titleView.setTitle("关于我们");
        version.setText("版本 "+AppUtil.getVersionName(mContext));
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,WebViewActivity.class);
                intent.putExtra("url","http://www.baidu.com");
                intent.putExtra("title","关于说法");
                startActivity(intent);
            }
        });
    }
}
