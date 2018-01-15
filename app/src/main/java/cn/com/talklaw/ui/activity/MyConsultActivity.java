package cn.com.talklaw.ui.activity;

import android.support.v4.view.ViewPager;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.widget.BackTitleView;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe 我的咨询
 */

public class MyConsultActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected ViewPager viewpager;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_consult;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

    }

    @Override
    public void initAction() {

        titleView.setTitle("我的咨询");
    }
}
