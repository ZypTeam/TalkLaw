package cn.com.talklaw.ui.activity;

import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.model.IntegralModel;
import com.chuxin.law.ui.widget.BackTitleView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import cn.com.talklaw.ui.adapter.GoodsViewPagerAdapter;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2911:00
 * @Email zyp@jusfoun.com
 * @Description ${积分 产品详情}
 */
public class AllGoodsActivity extends BaseTalkLawActivity {
    protected BackTitleView backTitleView;
    protected ViewPager viewpager;
    protected SmartTabLayout viewpagertab;
    private GoodsViewPagerAdapter adapter;

    private IntegralModel integralModel;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_all_goods;
    }

    @Override
    public void initDatas() {
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getSerializable("integralModel") != null) {
            integralModel = (IntegralModel) getIntent().getExtras().getSerializable("integralModel");
        }
        adapter = new GoodsViewPagerAdapter(getSupportFragmentManager(),integralModel.data.cat);
    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpagertab = (SmartTabLayout) findViewById(R.id.viewpagertab);

    }

    @Override
    public void initAction() {
        backTitleView.setTitle("所有商品");
        viewpager.setAdapter(adapter);

        viewpagertab.setViewPager(viewpager);
    }
}
