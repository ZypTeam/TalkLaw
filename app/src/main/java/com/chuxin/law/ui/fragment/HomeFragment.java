package com.chuxin.law.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.CarouseModel;
import com.chuxin.law.model.StatementListModel;
import com.chuxin.law.ui.activity.IntegralActivity;
import com.chuxin.law.ui.activity.LawyerIntroductionActivity;
import com.chuxin.law.ui.activity.WebViewActivity;
import com.chuxin.law.util.GlideImageLoader;
import com.chuxin.law.util.UIUtils;
import com.chuxin.law.ui.view.HomeListProductView;
import com.chuxin.law.ui.view.HomeNeedView;
import com.chuxin.law.ui.view.HomeScrollView;
import com.chuxin.law.ui.widget.BackTitleView;
import com.google.gson.Gson;
import com.jusfoun.baselibrary.Util.AppUtil;
import com.jusfoun.baselibrary.Util.PhoneUtil;
import com.jusfoun.baselibrary.net.Api;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2115:49
 * @Email zyp@jusfoun.com
 * @Description ${首页fragment}
 */
public class HomeFragment extends BaseTalkLawFragment {


    protected Banner banner;
    protected HomeListProductView viewHotProduct;
    protected HomeListProductView viewFreeProduct;
    protected HomeNeedView viewNeed;
    protected BackTitleView backTitleView;
    protected HomeScrollView scrollview;
    private int listDy = 0;
    private int disY;
    private StatementListModel statementListModel;

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initDatas() {
        disY = PhoneUtil.dip2px(mContext, 200);
    }

    @Override
    public void initView(View rootView) {
        banner = (Banner) rootView.findViewById(R.id.banner);
        viewHotProduct = (HomeListProductView) rootView.findViewById(R.id.view_hot_product);
        viewFreeProduct = (HomeListProductView) rootView.findViewById(R.id.view_free_product);
        viewNeed = (HomeNeedView) rootView.findViewById(R.id.view_need);
        backTitleView = (BackTitleView) rootView.findViewById(R.id.back_title_view);
        scrollview = (HomeScrollView) rootView.findViewById(R.id.scrollview);

//        ((TextView)    rootView.findViewById(R.id.text_test)).setText("测试用--当前版本号为："+AppUtil.getVersionName(mContext));

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

        rxManage.on(CommonConstant.EVNET_LIKE, new Action1<Object>() {
            @Override
            public void call(Object o) {
                delMsg(false);
            }
        });

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (statementListModel != null && statementListModel.data != null && statementListModel.data.carouse != null) {
                    CarouseModel model = statementListModel.data.carouse.get(position);
                    if (model != null) {
                        if ("0".equals(model.atype)) {
                            UIUtils.goLawyerDef(mContext, model.url);
                        }else if("1".equals(model.atype)) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra("url",model.url);
                            intent.putExtra("title","详情");
                            mContext.startActivity(intent);
                        }else if("2".equals(model.atype)){
                            Intent intent = new Intent();
                            intent.putExtra(LawyerIntroductionActivity.ID, model.url);
                            intent.setClass(mContext, LawyerIntroductionActivity.class);
                            mContext.startActivity(intent);
                        }else if("3".equals(model.atype)){
                            Intent intent = new Intent();
                            intent.setClass(mContext, IntegralActivity.class);
                            mContext.startActivity(intent);
                        }
                    }
                }

            }
        });

        scrollview.setOnScrollChangedListener(new HomeScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int top, int oldTop) {
                if (top < 0) {
                    top = 0;
                }
                float alpha = top / (float) disY;
                backTitleView.setAlpha(alpha);
            }
        });

        backTitleView.setAlpha(0f);
        backTitleView.setTitle("说法");
        backTitleView.setLeftGone();

        viewNeed.setCallBack(new HomeNeedView.CallBack() {
            @Override
            public void callback() {
                changeNet();
            }
        });
    }

    @Override
    protected void refreshData() {
        delMsg(true);
    }

    private void delMsg(boolean isShow) {
        if (isShow){
            showLoadDialog();
        }
        addNetwork(Api.getInstance().getService(ApiService.class).getHomeShuoFa()
                , new Action1<StatementListModel>() {
                    @Override
                    public void call(StatementListModel model) {
                        hideLoadDialog();
                        statementListModel = model;
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            if (model.data != null) {
                                if (model.data.hot != null) {
                                    viewHotProduct.setData(model.data.hot, 1, model.data.freetime);
                                }
                                if (model.data.free != null) {
                                    viewFreeProduct.setData(model.data.free, 2, model.data.freetime);
                                }

                                if (model.data.need != null) {
                                    viewNeed.setData(model.data.need);
                                }

                                if (model.data.carouse != null) {

                                    Log.e("tag","model.data.carouse="+new Gson().toJson(model.data.carouse));
                                    banner.setImages(model.data.carouse);
                                    banner.start();
//                                    if(model.data.carouse.size()>0) {
//                                        banner.setVisibility(View.VISIBLE);
//
//                                    }else{
//                                        banner.setVisibility(View.GONE);
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

    private void changeNet() {
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).getChangeNeedNet()
                , new Action1<StatementListModel.StatementDataModel>() {
                    @Override
                    public void call(StatementListModel.StatementDataModel model) {
                        hideLoadDialog();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            if (model.data != null) {

                                if (model.data!= null) {
                                    viewNeed.setData(model.data);
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
