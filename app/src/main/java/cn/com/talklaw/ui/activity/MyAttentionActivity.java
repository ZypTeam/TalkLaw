package cn.com.talklaw.ui.activity;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.model.MyAttentionModel;
import cn.com.talklaw.ui.adapter.MyAttentionAdapter;
import cn.com.talklaw.ui.widget.BackTitleView;
import cn.com.talklaw.ui.widget.xRecyclerView.XRecyclerView;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe 我的关注
 */

public class MyAttentionActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected XRecyclerView attentionList;
    private MyAttentionAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_attention;
    }

    @Override
    public void initDatas() {

        adapter=new MyAttentionAdapter(mContext);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        attentionList = (XRecyclerView) findViewById(R.id.attention_list);

        attentionList.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void initAction() {

        titleView.setTitle("我的关注");

        attentionList.setAdapter(adapter);

        List<MyAttentionModel> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MyAttentionModel model=new MyAttentionModel();
            list.add(model);
        }
        adapter.refreshList(list);
    }
}
