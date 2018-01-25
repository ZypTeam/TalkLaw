package cn.com.talklaw.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseView;
import cn.com.talklaw.model.ProductItemModel;
import cn.com.talklaw.ui.adapter.OpinionAdapter;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2509:26
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class HomeListProductView extends BaseView {
    protected RecyclerView recyclerView;
    protected OpinionAdapter adapter;
    protected TextView textTitle;
    private LinearLayoutManager layoutManager;

    public HomeListProductView(Context context) {
        super(context);
    }

    public HomeListProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeListProductView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {
        adapter = new OpinionAdapter(mContext);
    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_product_home_list, this, true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        textTitle = (TextView) findViewById(R.id.text_title);
    }

    @Override
    protected void initActions() {
         layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                // 直接禁止垂直滑动
                return false;
            }
        };

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    public void setData( List<ProductItemModel> list,int type ){
        // type 1 热门产品  2 限时免费
        adapter.refresh(list,type);
        if(type==1) {
            textTitle.setText("热门产品");
        }else if(type==2){
            textTitle.setText("限时免费");
        }
    }
}
