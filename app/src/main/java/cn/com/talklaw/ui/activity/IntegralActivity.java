package cn.com.talklaw.ui.activity;

import android.view.View;
import android.widget.RelativeLayout;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.util.GlideImageLoader;
import cn.com.talklaw.ui.view.IntegralListProductView;
import cn.com.talklaw.ui.widget.BackTitleView;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2515:31
 * @Email zyp@jusfoun.com
 * @Description ${积分首页}
 */
public class IntegralActivity extends BaseTalkLawActivity {
    protected Banner banner;
    protected IntegralListProductView viewIntegral;
    protected BackTitleView titleView;
    protected RelativeLayout layoutRecords;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_homt_integral;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        banner = (Banner) findViewById(R.id.banner);
        viewIntegral = (IntegralListProductView) findViewById(R.id.view_integeral_product);
        titleView = (BackTitleView) findViewById(R.id.titleView);
        layoutRecords = (RelativeLayout) findViewById(R.id.layout_records);

    }

    @Override
    public void initAction() {
        titleView.setTitle("积分商城");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        banner.setImages(list);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();


        banner.setFocusable(true);
        banner.setFocusableInTouchMode(true);
        banner.requestFocus();

        viewIntegral.setData();

        layoutRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null,ExchangeRecordsActivity.class);
            }
        });
    }
}
