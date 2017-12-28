package cn.com.talklaw.ui.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jusfoun.baselibrary.Util.PhoneUtil;

import java.util.ArrayList;
import java.util.List;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.model.ArrondiProductModel;
import cn.com.talklaw.model.ProductModel;
import cn.com.talklaw.ui.adapter.ArrondiProductAdapter;
import cn.com.talklaw.ui.adapter.ArrondiTopAdapter;
import cn.com.talklaw.ui.adapter.ProductListAdapter;
import cn.com.talklaw.ui.widget.BackTitleView;
import cn.com.talklaw.ui.widget.LoopScrollView;
import cn.com.talklaw.ui.widget.xRecyclerView.XRecyclerView;

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

    private int type;

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

        list.setPullRefreshEnabled(false);

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
        top.start();
        List<String> images = new ArrayList<>();
        images.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2825366391,814429300&fm=27&gp=0.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514130772757&di=3406bedb79ff4c9c5ea4d6b0292df2df&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F2fdda3cc7cd98d10261a710a2a3fb80e7bec903a.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514130772756&di=4a72c3fb61b47a254c5c20404a3011c2&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F9825bc315c6034a8ab2411bac0134954082376c3.jpg");
        topAdapter.refresh(top.getList(images));
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

        List<ProductModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductModel model = new ProductModel();
            list.add(model);
        }
        listAdapter.refreshList(list);

        initProduct();
    }

    private void initProduct() {
        List<List<ArrondiProductModel>> lists = new ArrayList<>();
        List<ArrondiProductModel> list = new ArrayList<>();
        ArrondiProductModel model1 = new ArrondiProductModel();
        model1.setImageResId(R.mipmap.icon_hunyin);
        model1.setName("婚姻");
        list.add(model1);
        ArrondiProductModel model2 = new ArrondiProductModel();
        model2.setImageResId(R.mipmap.icon_gongsi);
        model2.setName("公司");
        list.add(model2);
        ArrondiProductModel model3 = new ArrondiProductModel();
        model3.setImageResId(R.mipmap.icon_xingshi);
        model3.setName("刑事");
        list.add(model3);
        ArrondiProductModel model4 = new ArrondiProductModel();
        model4.setImageResId(R.mipmap.icon_laowu);
        model4.setName("劳务");
        list.add(model4);
        ArrondiProductModel model5 = new ArrondiProductModel();
        model5.setImageResId(R.mipmap.icon_susong);
        model5.setName("诉讼");
        list.add(model5);
        ArrondiProductModel model6 = new ArrondiProductModel();
        model6.setImageResId(R.mipmap.icon_jiaotong);
        model6.setName("交通");
        list.add(model6);
        ArrondiProductModel model7 = new ArrondiProductModel();
        model7.setImageResId(R.mipmap.icon_hetong);
        model7.setName("合同");
        list.add(model7);
        ArrondiProductModel model8 = new ArrondiProductModel();
        model8.setImageResId(R.mipmap.icon_gongsi);
        model8.setName("官司");
        list.add(model8);
        lists.add(list);
        arrondi.setAdapter(productAdapter);
        productAdapter.refresh(lists);
    }
}
