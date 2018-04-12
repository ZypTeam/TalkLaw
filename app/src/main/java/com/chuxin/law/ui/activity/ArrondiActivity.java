package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.chuxin.law.common.AdapterCallback;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.ArrondiModel;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.jusfoun.baselibrary.Util.PhoneUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.model.ArrondiProductModel;
import com.chuxin.law.model.ProductModel;
import com.chuxin.law.ui.adapter.ArrondiProductAdapter;
import com.chuxin.law.ui.adapter.ArrondiTopAdapter;
import com.chuxin.law.ui.adapter.ProductListAdapter;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.widget.LoopScrollView;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import rx.Observable;
import rx.functions.Action1;

import static com.chuxin.law.ui.activity.CommentListActivity.COMMENT_COUNT;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 专区
 */

public class ArrondiActivity extends BaseTalkLawActivity {
    public static final String TYPE = "type";
    protected BackTitleView titleView;
    protected LoopScrollView top;
    protected ViewPager arrondi;
    protected Button buy;
    protected XRecyclerView list;

    private ArrondiTopAdapter topAdapter;
    private ProductListAdapter listAdapter;
    private ArrondiProductAdapter productAdapter;
    private int listDy = 0;
    private int disY;
    private int page = 0;
    private int type;
    private int PAGE_COUNT = 8;

    private String price,oldPrice;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_arrondi;
    }

    @Override
    public void initDatas() {
        topAdapter = new ArrondiTopAdapter(mContext);
        listAdapter = new ProductListAdapter(mContext);
        productAdapter = new ArrondiProductAdapter(mContext);
        disY = PhoneUtil.dip2px(mContext, 200);
        type = getIntent().getExtras().getInt(TYPE, 0);
    }

    @Override
    public void initView() {
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_arrondi_header, null);

        titleView = (BackTitleView) findViewById(R.id.titleView);
        top = (LoopScrollView) headerView.findViewById(R.id.top);
        arrondi = (ViewPager) headerView.findViewById(R.id.arrondi);
        buy = (Button) findViewById(R.id.buy);
        list = (XRecyclerView) findViewById(R.id.list);
        list.addHeaderView(headerView);
    }

    @Override
    public void initAction() {

        list.setPullRefreshEnabled(true);
        list.setLoadingMoreEnabled(false);
        list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData(false, true);
            }

            @Override
            public void onLoadMore() {
                getData(true, true);
            }
        });

        switch (type) {
            case 0:
                titleView.setTitle("免费专区");
                buy.setVisibility(View.GONE);
                break;
            case 1:
                titleView.setTitle("私人顾问");
                buy.setVisibility(View.VISIBLE);
                break;
            case 2:
                titleView.setTitle("公司顾问");
                buy.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        top.setAdapter(topAdapter)
                .setDelayTime(3000)
                .setOffscreenPageLimit(3);

        top.setCurrentItem(1, false);

        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setAdapter(listAdapter);

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                listDy += dy;
                if (listDy < 0) {
                    listDy = 0;
                }
                float alpha = listDy / (float) disY;
                titleView.setAlpha(alpha);
            }
        });

        listAdapter.setCommentCall(new AdapterCallback() {
            @Override
            public void callback(Object model, int position) {
                if (model == null) {
                    return;
                }
                ProductModel productModel = (ProductModel) model;
//                UIUtils.goCommentList(mContext,productModel.getId());
            }
        });

        listAdapter.setThumbsCall(new AdapterCallback() {
            @Override
            public void callback(Object model, int position) {
                ProductModel productModel = (ProductModel) model;
                if (productModel == null) {
                    return;
                }

            }
        });

        arrondi.setAdapter(productAdapter);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,BuyArrondiActivity.class);
                intent.putExtra(BuyArrondiActivity.TYPE,type);
                intent.putExtra(BuyArrondiActivity.PRICE,price);
                intent.putExtra(BuyArrondiActivity.OLD_PRICE,oldPrice);
                mContext.startActivity(intent);

            }
        });

        rxManage.on(CommonConstant.EVENT_BUY_ARRONDI, new Action1<Object>() {
            @Override
            public void call(Object o) {
                getData(false,true);
            }
        });


//        initProduct();
        getData(true, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonConstant.COMMENT_RESULT_CODE) {
            if (data != null) {
                String count = data.getStringExtra(COMMENT_COUNT);
            }
        }
    }

    private void unlike(final ProductModel model, final int position) {
        if (model == null) {
            return;
        }
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", model.getId());
        addNetwork(Api.getInstance().getService(ApiService.class).delLike(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode() == CommonConstant.NET_SUC_CODE) {
                            listAdapter.refreshItem(model, position);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void like(final ProductModel model, final int position) {
        if (model == null) {
            return;
        }
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", model.getId());
        addNetwork(Api.getInstance().getService(ApiService.class).setLike(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode() == CommonConstant.NET_SUC_CODE) {
                            listAdapter.refreshItem(model, position);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void getData(boolean isShow, boolean isRefresh) {
        if (isShow) {
            showLoadDialog();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("size", CommonConstant.LIST_PAGE_SIZE);
        params.put("page", (isRefresh ? 1 : page + 1) + "");
        Observable observable = null;
        switch (type) {
            case 0:
                observable = Api.getInstance().getService(ApiService.class).getFreeList(params);
                break;
            case 1:
                observable = Api.getInstance().getService(ApiService.class).getPrivate(params);
                break;
            case 2:
                observable = Api.getInstance().getService(ApiService.class).getCompany(params);
                break;
            default:
                break;
        }
        addNetwork(observable, new Action1<ArrondiModel>() {
            @Override
            public void call(ArrondiModel arrondiModel) {
                hideLoadDialog();
                list.refreshComplete();
                list.loadMoreComplete();
                if (arrondiModel.getCode() == CommonConstant.NET_SUC_CODE) {
                    ArrondiModel.DataBean dataBean = arrondiModel.getData();
                    if (dataBean != null) {
                        if (!TextUtils.isEmpty(dataBean.getPrice())) {
                            price = dataBean.getPrice();
                        }
                        if (!TextUtils.isEmpty(dataBean.getOldPrice())){
                            oldPrice=dataBean.getOldPrice();
                        }
                        listAdapter.refreshList(dataBean.getArticle());
                        topAdapter.refresh(dataBean.getCarouse());
                        top.start();
                        settProduct(dataBean.getCatList());
                    }
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                hideLoadDialog();
                list.refreshComplete();
                list.loadMoreComplete();
            }
        });
    }

    private void settProduct(List<ArrondiProductModel> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        List<List<ArrondiProductModel>> lists = new ArrayList<>();
        int pageSize;
        int size = list.size();
        if (size % PAGE_COUNT == 0) {
            pageSize = size / PAGE_COUNT;
        } else {
            pageSize = size / PAGE_COUNT + 1;
        }
        for (int i = 0; i < pageSize; i++) {
            List<ArrondiProductModel> models = new ArrayList<>();
            for (int j = 0; j < PAGE_COUNT; j++) {
                if (i * PAGE_COUNT + j >= size) {
                    break;
                } else {
                    models.add(list.get(i * PAGE_COUNT + j));
                }
            }
            lists.add(models);
        }
        productAdapter.refresh(lists);
    }
}
