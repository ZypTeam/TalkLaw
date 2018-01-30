package com.chuxin.law.ui.activity;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.comment.ApiService;
import com.chuxin.law.ui.adapter.CommentListAdapter;
import com.chuxin.law.ui.view.KeyboardChangeView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.widget.TitleStatusBarView;

import java.util.HashMap;

/**
 * @author wangcc
 * @date 2018/1/31
 * @describe 评论列表
 */

public class CommentListActivity extends BaseTalkLawActivity {
    public static final String ID="id";
    protected TextView title;
    protected TitleStatusBarView titleBar;
    protected XRecyclerView list;
    protected ImageView back;
    protected TextView edit;
    protected EditText commentEdit;
    protected TextView cancel;
    protected TextView send;
    protected ConstraintLayout commentLayout;
    private KeyboardChangeView keyboardChangeView;
    private CommentListAdapter adapter;
    private String id;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_comment_list;
    }

    @Override
    public void initDatas() {
        adapter=new CommentListAdapter(mContext);
        id=getIntent().getStringExtra(ID);
    }

    @Override
    public void initView() {
        keyboardChangeView=findViewById(R.id.keyboard_view);
        title = (TextView) findViewById(R.id.title);
        titleBar = (TitleStatusBarView) findViewById(R.id.title_bar);
        list = (XRecyclerView) findViewById(R.id.list);
        back = (ImageView) findViewById(R.id.back);
        edit = (TextView) findViewById(R.id.edit);
        commentEdit = (EditText) findViewById(R.id.comment_edit);
        cancel = (TextView) findViewById(R.id.cancel);
        send = (TextView) findViewById(R.id.send);
        commentLayout = (ConstraintLayout) findViewById(R.id.comment_layout);

    }

    @Override
    public void initAction() {
        keyboardChangeView.setOnKeyListener(new KeyboardChangeView.KeyBoardChangeListener() {
            @Override
            public void onChange(boolean isShow, int keyHeight) {
                if (isShow){
                    ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) commentLayout.getLayoutParams();
                    params.bottomMargin=keyHeight;
                    commentLayout.setLayoutParams(params);
                    commentLayout.setVisibility(View.VISIBLE);
                }else {
                    commentLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getCommentList(){
        HashMap<String,String> params=new HashMap<>();
        params.put("id",id);
//        addNetwork(Api.getInstance().getService(ApiService.class).);
    }
}
