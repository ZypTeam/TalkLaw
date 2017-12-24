package cn.com.talklaw.ui.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

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

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe 专区
 */

public class ArrondiActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected LoopScrollView top;
    protected ViewPager arrondi;
    protected Button buy;
    protected RecyclerView list;

    private ArrondiTopAdapter topAdapter;
    private ProductListAdapter listAdapter;
    private ArrondiProductAdapter productAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_arrondi;
    }

    @Override
    public void initDatas() {
        topAdapter=new ArrondiTopAdapter(mContext);
        listAdapter=new ProductListAdapter(mContext);
        productAdapter=new ArrondiProductAdapter(mContext);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        top = (LoopScrollView) findViewById(R.id.top);
        arrondi = (ViewPager) findViewById(R.id.arrondi);
        buy = (Button) findViewById(R.id.buy);
        list = (RecyclerView) findViewById(R.id.list);

    }

    @Override
    public void initAction() {

        top.setAdapter(topAdapter)
                .setDelayTime(3000)
                .setOffscreenPageLimit(3);
        List<String> images=new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514130772757&di=a2c522557ff84475f973ec3be533524b&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fb58f8c5494eef01fd65fb3feebfe9925bc317d46.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514130772757&di=3406bedb79ff4c9c5ea4d6b0292df2df&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F2fdda3cc7cd98d10261a710a2a3fb80e7bec903a.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514130772756&di=4a72c3fb61b47a254c5c20404a3011c2&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F9825bc315c6034a8ab2411bac0134954082376c3.jpg");
        topAdapter.refresh(top.getList(images));
        top.setCurrentItem(1,false);

        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setAdapter(listAdapter);

        List<ProductModel> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductModel model=new ProductModel();
            list.add(model);
        }
        listAdapter.refreshList(list);

        initProduct();
    }

    private void initProduct(){
        List<List<ArrondiProductModel>> lists=new ArrayList<>();
        List<ArrondiProductModel> list=new ArrayList<>();
        ArrondiProductModel model1=new ArrondiProductModel();
        model1.setImageResId(R.mipmap.icon_hunyin);
        model1.setName("婚姻");
        list.add(model1);
        ArrondiProductModel model2=new ArrondiProductModel();
        model2.setImageResId(R.mipmap.icon_gongsi);
        model2.setName("公司");
        list.add(model2);
        ArrondiProductModel model3=new ArrondiProductModel();
        model3.setImageResId(R.mipmap.icon_xingshi);
        model3.setName("刑事");
        list.add(model3);
        ArrondiProductModel model4=new ArrondiProductModel();
        model4.setImageResId(R.mipmap.icon_laowu);
        model4.setName("劳务");
        list.add(model4);
        ArrondiProductModel model5=new ArrondiProductModel();
        model5.setImageResId(R.mipmap.icon_susong);
        model5.setName("诉讼");
        list.add(model5);
        ArrondiProductModel model6=new ArrondiProductModel();
        model6.setImageResId(R.mipmap.icon_jiaotong);
        model6.setName("交通");
        list.add(model6);
        ArrondiProductModel model7=new ArrondiProductModel();
        model7.setImageResId(R.mipmap.icon_hetong);
        model7.setName("合同");
        list.add(model7);
        ArrondiProductModel model8=new ArrondiProductModel();
        model8.setImageResId(R.mipmap.icon_gongsi);
        model8.setName("官司");
        list.add(model8);
        lists.add(list);
        arrondi.setAdapter(productAdapter);
        productAdapter.refresh(lists);
    }
}
