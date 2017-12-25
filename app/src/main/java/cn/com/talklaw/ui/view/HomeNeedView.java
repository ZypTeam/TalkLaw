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
import cn.com.talklaw.model.ProductModel;
import cn.com.talklaw.ui.adapter.HomeNeedAdapter;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2509:26
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class HomeNeedView extends BaseView {
    protected RecyclerView recyclerView;
    protected HomeNeedAdapter adapter;
    protected TextView textTitle;
    private LinearLayoutManager layoutManager;

    public HomeNeedView(Context context) {
        super(context);
    }

    public HomeNeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeNeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {
        adapter = new HomeNeedAdapter(mContext);
    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_need_home_list, this, true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        textTitle = (TextView) findViewById(R.id.text_title);
    }

    @Override
    protected void initActions() {
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        List list = new ArrayList<ProductModel>();
        list.add(new ProductModel());
        list.add(new ProductModel());
        list.add(new ProductModel());
        list.add(new ProductModel());
        adapter.refreshList(list);

    }

    public void setData() {
        textTitle.setText("猜你需要");
    }
}
