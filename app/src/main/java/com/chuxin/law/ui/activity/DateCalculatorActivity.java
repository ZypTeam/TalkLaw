package com.chuxin.law.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.adapter.CalculatorAdapter;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1718:20
 * @Email zyp@jusfoun.com
 * @Description ${日期计算器}
 */
public class DateCalculatorActivity extends BaseTalkLawActivity {
    protected BackTitleView titleBar;
    protected ViewPager viewpager;
    protected RelativeLayout layoutDay;
    protected RelativeLayout layoutDate;
    protected View viewDay;
    protected View viewDate;
    private CalculatorAdapter calculatorAdapter;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_date_calculator;
    }

    @Override
    public void initDatas() {
        calculatorAdapter = new CalculatorAdapter(getSupportFragmentManager());
    }

    @Override
    public void initView() {
        titleBar = (BackTitleView) findViewById(R.id.back_title_view);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        layoutDay = (RelativeLayout) findViewById(R.id.layout_day);
        layoutDate = (RelativeLayout) findViewById(R.id.layout_date);
        viewDay = (View) findViewById(R.id.view_day);
        viewDate = (View) findViewById(R.id.view_date);

    }

    @Override
    public void initAction() {
        titleBar.setTitle("日期计算器");
        viewpager.setAdapter(calculatorAdapter);

        layoutDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(0);
                viewDay.setVisibility(View.VISIBLE);
                viewDate.setVisibility(View.GONE);
            }
        });
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(1);
                viewDay.setVisibility(View.GONE);
                viewDate.setVisibility(View.VISIBLE);
            }
        });


    }
}
