package cn.com.talklaw.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jusfoun.baselibrary.widget.TitleStatusBarView;

import java.util.ArrayList;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.comment.OnMsgDelCallback;
import cn.com.talklaw.model.MyMsgModel;
import cn.com.talklaw.ui.adapter.MyMsgListAdapter;
import cn.com.talklaw.ui.widget.xRecyclerView.XRecyclerView;

/**
 * @author wangcc
 * @date 2018/1/3
 * @describe 我的信息 列表activity
 */

public class MyMsgListActivity extends BaseTalkLawActivity {
    protected ImageView back;
    protected TextView title;
    protected TextView right;
    protected TitleStatusBarView titleBar;
    protected XRecyclerView list;

    private MyMsgListAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_msg_list;
    }

    @Override
    public void initDatas() {
        adapter=new MyMsgListAdapter(mContext);
    }

    @Override
    public void initView() {
        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        right = (TextView) findViewById(R.id.right);
        titleBar = (TitleStatusBarView) findViewById(R.id.title_bar);
        list = (XRecyclerView) findViewById(R.id.list);

    }

    @Override
    public void initAction() {

        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setAdapter(adapter);
        ArrayList<MyMsgModel> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new MyMsgModel());
        }
        adapter.refreshList(list);

        adapter.setCallback(new OnMsgDelCallback() {
            @Override
            public void del(MyMsgModel model, int position) {
                adapter.remove(position);
            }
        });
    }
}
