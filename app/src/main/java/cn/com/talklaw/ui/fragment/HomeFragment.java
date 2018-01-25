package cn.com.talklaw.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.jusfoun.baselibrary.Util.PhoneUtil;
import com.jusfoun.baselibrary.net.Api;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawFragment;
import cn.com.talklaw.comment.ApiService;
import cn.com.talklaw.model.StatementListModel;
import cn.com.talklaw.ui.util.GlideImageLoader;
import cn.com.talklaw.ui.view.HomeListProductView;
import cn.com.talklaw.ui.view.HomeNeedView;
import cn.com.talklaw.ui.view.HomeScrollView;
import cn.com.talklaw.ui.widget.BackTitleView;
import rx.functions.Action1;

import static cn.com.talklaw.comment.CommentConstant.NET_SUC_CODE;

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
    }

    @Override
    protected void refreshData() {
        delMsg();
    }

    private void delMsg() {
        addNetwork(Api.getInstance().getService(ApiService.class).getHomeShuoFa()
                , new Action1<StatementListModel>() {
                    @Override
                    public void call(StatementListModel model) {
                        hideLoadDialog();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            if (model.data != null) {
                                if (model.data.hot != null) {
                                    viewHotProduct.setData(model.data.hot, 1,model.data.freetime);
                                }
                                if (model.data.free != null) {
                                    viewFreeProduct.setData(model.data.free, 2,model.data.freetime);
                                }

                                if (model.data.need != null) {
                                    viewNeed.setData(model.data.need);
                                }

                                if (model.data.carouse != null) {
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
}
