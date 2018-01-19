package cn.com.talklaw.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawFragment;
import cn.com.talklaw.model.ProductModel;
import cn.com.talklaw.ui.activity.AtaxCalculatorActivity;
import cn.com.talklaw.ui.activity.DateCalculatorActivity;
import cn.com.talklaw.ui.activity.LawyerCalculatorActivity;
import cn.com.talklaw.ui.activity.LitigationCalculatorActivity;
import cn.com.talklaw.ui.activity.SearchActivity;
import cn.com.talklaw.ui.adapter.ProductListAdapter;
import cn.com.talklaw.ui.util.GlideImageLoader;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2115:49
 * @Email zyp@jusfoun.com
 * @Description ${说法fragment}
 */
public class StatementFragment extends BaseTalkLawFragment implements View.OnClickListener {

    protected Banner banner;
    protected RecyclerView recyclerView;
    protected LinearLayout layoutLitigation;
    protected LinearLayout layoutLawyer;
    protected LinearLayout layoutAtax;
    protected LinearLayout layoutDate;
    protected LinearLayout layoutSearchEdit;
    protected ImageView imgAudio;
    private ProductListAdapter adapter;

    public static StatementFragment getInstance() {
        StatementFragment fragment = new StatementFragment();
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_statement;
    }

    @Override
    public void initDatas() {
        adapter = new ProductListAdapter(mContext);
    }

    @Override
    public void initView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        banner = (Banner) rootView.findViewById(R.id.banner);
        layoutLitigation = (LinearLayout) rootView.findViewById(R.id.layout_litigation);
        layoutLitigation.setOnClickListener(StatementFragment.this);
        layoutLawyer = (LinearLayout) rootView.findViewById(R.id.layout_lawyer);
        layoutLawyer.setOnClickListener(StatementFragment.this);
        layoutAtax = (LinearLayout) rootView.findViewById(R.id.layout_atax);
        layoutAtax.setOnClickListener(StatementFragment.this);
        layoutDate = (LinearLayout) rootView.findViewById(R.id.layout_date);
        layoutDate.setOnClickListener(StatementFragment.this);
        layoutSearchEdit = (LinearLayout) rootView.findViewById(R.id.layout_search_edit);
        imgAudio = (ImageView) rootView.findViewById(R.id.img_audio);
    }

    @Override
    public void initAction() {
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


        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        List<ProductModel> list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductModel model = new ProductModel();
            list1.add(model);
        }
        adapter.refreshList(list1);

        layoutSearchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, SearchActivity.class);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.layout_litigation) {
            goActivity(null, LitigationCalculatorActivity.class);

        } else if (view.getId() == R.id.layout_lawyer) {
            goActivity(null, LawyerCalculatorActivity.class);

        } else if (view.getId() == R.id.layout_atax) {
            goActivity(null, AtaxCalculatorActivity.class);

        } else if (view.getId() == R.id.layout_date) {
            goActivity(null, DateCalculatorActivity.class);
        }
    }
}
