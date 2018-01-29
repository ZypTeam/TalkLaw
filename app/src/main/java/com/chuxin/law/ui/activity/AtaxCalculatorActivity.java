package com.chuxin.law.ui.activity;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1718:20
 * @Email zyp@jusfoun.com
 * @Description ${个税计算器}
 */
public class AtaxCalculatorActivity extends BaseTalkLawActivity {
    protected BackTitleView titleBar;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_atax_calculator;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        titleBar = (BackTitleView) findViewById(R.id.back_title_view);

    }

    @Override
    public void initAction() {
        titleBar.setTitle("个税计算器");
    }
}
