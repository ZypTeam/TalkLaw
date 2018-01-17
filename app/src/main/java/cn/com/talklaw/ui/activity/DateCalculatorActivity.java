package cn.com.talklaw.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.adapter.CalculatorAdapter;
import cn.com.talklaw.ui.widget.BackTitleView;

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

    }

    @Override
    public void initAction() {
        titleBar.setTitle("日期计算器");
        viewpager.setAdapter(calculatorAdapter);

        layoutDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(0);
            }
        });
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(1);
            }
        });
    }
}
