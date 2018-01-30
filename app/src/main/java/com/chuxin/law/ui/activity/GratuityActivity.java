package com.chuxin.law.ui.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author wangcc
 * @date 2018/1/24
 * @describe 打赏
 */

public class GratuityActivity extends BaseTalkLawActivity {
    protected BackTitleView titleBar;
    protected ImageView iconGratuity;
    protected TextView content;
    protected TextView one;
    protected TextView two;
    protected TextView three;
    protected TextView four;
    protected TextView five;
    protected TextView six;
    protected TextView help;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gratuity;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        titleBar = (BackTitleView) findViewById(R.id.title_bar);
        iconGratuity = (ImageView) findViewById(R.id.icon_gratuity);
        content = (TextView) findViewById(R.id.content);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);
        five = (TextView) findViewById(R.id.five);
        six = (TextView) findViewById(R.id.six);
        help = (TextView) findViewById(R.id.help);

    }

    @Override
    public void initAction() {
        titleBar.setTitle("打赏");
    }
}
