package com.chuxin.law.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.comment.ApiService;
import com.chuxin.law.model.IntegralModel;
import com.chuxin.law.ui.adapter.IntegralGoodsAdapter;
import com.chuxin.law.ui.util.GlideImageLoader;
import com.chuxin.law.ui.view.IntegralListProductView;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.net.Api;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import cn.com.talklaw.ui.activity.AllGoodsActivity;
import rx.functions.Action1;

import static com.chuxin.law.comment.CommentConstant.NET_SUC_CODE;

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
    protected LinearLayout integralLayout;
    protected TextView textCountIntegral;
    protected RecyclerView viewGoodsRecycleview;
    protected TextView textGetIntegral;
    private IntegralGoodsAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private IntegralModel integralModel;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_homt_integral;
    }

    @Override
    public void initDatas() {
        adapter = new IntegralGoodsAdapter(mContext);
        linearLayoutManager = new LinearLayoutManager(mContext);
    }

    @Override
    public void initView() {
        banner = (Banner) findViewById(R.id.banner);
        viewIntegral = (IntegralListProductView) findViewById(R.id.view_integeral_product);
        titleView = (BackTitleView) findViewById(R.id.titleView);
        layoutRecords = (RelativeLayout) findViewById(R.id.layout_records);
        integralLayout = (LinearLayout) findViewById(R.id.layout_integral);
        textCountIntegral = (TextView) findViewById(R.id.text_count_integral);
        viewGoodsRecycleview = (RecyclerView) findViewById(R.id.view_goods_recycleview);


        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewGoodsRecycleview.setLayoutManager(linearLayoutManager);
        viewGoodsRecycleview.setAdapter(adapter);
        textGetIntegral = (TextView) findViewById(R.id.text_get_integral);
    }

    @Override
    public void initAction() {
        titleView.setTitle("积分商城");
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
        banner.setIndicatorGravity(BannerConfig.LEFT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();


        banner.setFocusable(true);
        banner.setFocusableInTouchMode(true);
        banner.requestFocus();


        layoutRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, ExchangeRecordsActivity.class);
            }
        });
        integralLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, IntegralDetailActivity.class);
            }
        });

        adapter.setCallBack(new IntegralGoodsAdapter.CallBack() {
            @Override
            public void click(int position) {
                if (integralModel != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("integralModel", integralModel);
                    goActivity(bundle, AllGoodsActivity.class);
                }

            }
        });

        textCountIntegral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null,RecommendCourtesyActivity.class);
            }
        });

        delMsg();
    }

    private void delMsg() {
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).getIntergralHome()
                , new Action1<IntegralModel>() {
                    @Override
                    public void call(IntegralModel model) {
                        integralModel = model;
                        hideLoadDialog();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            if (model.data != null) {
                                if (model.data.goods != null) {
                                    viewIntegral.setData(model);
                                }

                                if (model.data.carouse != null) {
                                    banner.setImages(model.data.carouse);
                                    banner.start();
                                }

                                if (model.data.cat != null) {
                                    adapter.refreshList(model.data.cat);
                                }
//                                textCountIntegral.setText();
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
