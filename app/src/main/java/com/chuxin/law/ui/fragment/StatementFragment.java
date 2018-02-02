package com.chuxin.law.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chuxin.law.ui.activity.AudioDetailsActivity;
import com.chuxin.law.ui.util.GlideImageLoader;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.jusfoun.baselibrary.Util.PhoneUtil;
import com.jusfoun.baselibrary.net.Api;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.model.ProductListModel;
import com.chuxin.law.ui.activity.AtaxCalculatorActivity;
import com.chuxin.law.ui.activity.DateCalculatorActivity;
import com.chuxin.law.ui.activity.LawyerCalculatorActivity;
import com.chuxin.law.ui.activity.LitigationCalculatorActivity;
import com.chuxin.law.ui.activity.SearchActivity;
import com.chuxin.law.ui.adapter.ProductListAdapter;
import com.chuxin.law.ui.widget.BackTitleView;

import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2115:49
 * @Email zyp@jusfoun.com
 * @Description ${看法}
 */
public class StatementFragment extends BaseTalkLawFragment implements View.OnClickListener {

    protected Banner banner;
    protected XRecyclerView recyclerView;
    protected LinearLayout layoutLitigation;
    protected LinearLayout layoutLawyer;
    protected LinearLayout layoutAtax;
    protected LinearLayout layoutDate;
    protected LinearLayout layoutSearchEdit;
    protected ImageView imgAudio;
    protected BackTitleView backTitleView;
    private ProductListAdapter adapter;
    private int listDy = 0;
    private int disY;


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
        disY = PhoneUtil.dip2px(mContext, 200);
    }

    @Override
    public void initView(View rootView) {
        recyclerView = (XRecyclerView) rootView.findViewById(R.id.list);

        layoutLitigation = (LinearLayout) rootView.findViewById(R.id.layout_litigation);
        layoutLitigation.setOnClickListener(StatementFragment.this);
        layoutLawyer = (LinearLayout) rootView.findViewById(R.id.layout_lawyer);
        layoutLawyer.setOnClickListener(StatementFragment.this);
        layoutAtax = (LinearLayout) rootView.findViewById(R.id.layout_atax);
        layoutAtax.setOnClickListener(StatementFragment.this);
        layoutDate = (LinearLayout) rootView.findViewById(R.id.layout_date);
        layoutDate.setOnClickListener(StatementFragment.this);


        View headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_statemen_header, null);
        recyclerView.addHeaderView(headerView);

        banner = (Banner) headerView.findViewById(R.id.banner);
        layoutSearchEdit = (LinearLayout) headerView.findViewById(R.id.layout_search_edit);
        imgAudio = (ImageView) headerView.findViewById(R.id.img_audio);
        backTitleView = (BackTitleView) rootView.findViewById(R.id.back_title_view);
    }

    @Override
    public void initAction() {


        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合

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


        banner.setFocusable(true);
        banner.setFocusableInTouchMode(true);
        banner.requestFocus();


        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                listDy += dy;
                if (listDy < 0) {
                    listDy = 0;
                }
                float alpha = listDy / (float) disY;
                backTitleView.setAlpha(alpha);
            }
        });
        backTitleView.setTitle("看法");
        layoutSearchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, SearchActivity.class);
            }
        });

        imgAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AudioDetailsActivity.class);
                mContext.startActivity(intent);
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

    @Override
    protected void refreshData() {
        delMsg();
    }

    private void delMsg() {
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).getHomeKanfa()
                , new Action1<ProductListModel>() {
                    @Override
                    public void call(ProductListModel model) {
                        hideLoadDialog();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            if (model.data != null) {
                                if (model.data.article != null) {
                                    adapter.refreshList(model.data.article);
                                }
                                if (model.data.carouse != null) {
                                    banner.setImages(model.data.carouse);
                                    banner.start();
//                                    if(model.data.carouse.size()>0) {
////                                        banner.setVisibility(View.VISIBLE);
//
//                                    }else{
////                                        banner.setVisibility(View.GONE);
//                                    }
                                } else {
//                                    banner.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }
}
