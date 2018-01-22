package cn.com.talklaw.ui.activity;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.adapter.MyConsultAdapter;
import cn.com.talklaw.ui.widget.BackTitleView;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe 我的咨询
 */

public class MyConsultActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected ViewPager viewpager;
    protected View bgView;
    protected TextView consulting;
    protected TextView consulted;
    private MyConsultAdapter myConsultAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_consult;
    }

    @Override
    public void initDatas() {
        myConsultAdapter = new MyConsultAdapter(getSupportFragmentManager());
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        bgView = (View) findViewById(R.id.bg_view);
        consulting = (TextView) findViewById(R.id.consulting);
        consulted = (TextView) findViewById(R.id.consulted);

    }

    @Override
    public void initAction() {

        titleView.setTitle("我的咨询");

        viewpager.setAdapter(myConsultAdapter);
        selectPosition(0);

        consulted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition(1);
            }
        });

        consulting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition(0);
            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void selectPosition(int position) {
        viewpager.setCurrentItem(position);
        switch (position) {
            case 0:
                consulting.setTextColor(Color.parseColor("#cb1f28"));
                consulting.setBackgroundResource(R.drawable.bg_red_line);
                consulted.setTextColor(Color.parseColor("#999999"));
                consulted.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 1:
                consulted.setTextColor(Color.parseColor("#cb1f28"));
                consulted.setBackgroundResource(R.drawable.bg_red_line);
                consulting.setBackgroundColor(Color.parseColor("#ffffff"));
                consulting.setTextColor(Color.parseColor("#999999"));
                break;
            default:
                break;
        }
    }
}
