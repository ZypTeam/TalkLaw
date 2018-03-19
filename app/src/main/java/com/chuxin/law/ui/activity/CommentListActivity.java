package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.CommentListModel;
import com.chuxin.law.ui.adapter.CommentListAdapter;
import com.chuxin.law.ui.view.KeyboardChangeView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.jusfoun.baselibrary.Util.KeyBoardUtil;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.Util.PhoneUtil;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.widget.TitleStatusBarView;

import java.util.HashMap;

import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2018/1/31
 * @describe 评论列表
 */

public class CommentListActivity extends BaseTalkLawActivity {
    public static final String COMMENT_COUNT="comment_count";
    public static final String COMMENT_TITLE="comment_title";
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
    private String commentTitle;

    private int page=1;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_comment_list;
    }

    @Override
    public void initDatas() {
        adapter=new CommentListAdapter(mContext);
        id=getIntent().getStringExtra(ID);
        commentTitle=getIntent().getStringExtra(COMMENT_TITLE);
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
        title.setText(commentTitle);
        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setAdapter(adapter);
        list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getCommentList(false,true);
            }

            @Override
            public void onLoadMore() {
                getCommentList(false,false);
            }
        });
        keyboardChangeView.setOnKeyListener(new KeyboardChangeView.KeyBoardChangeListener() {
            @Override
            public void onChange(boolean isShow, int keyHeight) {
                if (isShow){
                    ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) commentLayout.getLayoutParams();
                    params.bottomMargin=keyHeight- PhoneUtil.dip2px(mContext,21);
                    commentLayout.setLayoutParams(params);
                    commentLayout.setVisibility(View.VISIBLE);
                    commentEdit.setCursorVisible(true);
//                    commentEdit.setFocusableInTouchMode(true);
                    commentEdit.requestFocus();
                }else {
                    commentLayout.setVisibility(View.GONE);
                    commentEdit.setCursorVisible(false);
//                    commentEdit.setFocusableInTouchMode(false);
                    commentEdit.clearFocus();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra(COMMENT_COUNT,adapter.getItemCount()+"");
                setResult(RESULT_OK,intent);
                onBackPressed();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.hideSoftKeyboard(mContext,commentEdit);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.showSoftKeyboard(commentEdit,mContext);
            }
        });

        getCommentList(true,true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_UP){
            Intent intent=new Intent();
            intent.putExtra(COMMENT_COUNT,adapter.getItemCount()+"");
            setResult(RESULT_OK,intent);
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void sendComment(){
        if (StringUtil.isEmpty(commentEdit.getText().toString())){
            showToast("评论内容不能为空");
            return;
        }
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("artid",id);
        params.put("content",commentEdit.getText().toString());
        addNetwork(Api.getInstance().getService(ApiService.class).sendComment(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        KeyBoardUtil.hideSoftKeyboard(mContext,commentEdit);
                        if (noDataModel.getCode()== CommonConstant.NET_SUC_CODE){
                            getCommentList(true,true);
                        }else {
                            hideLoadDialog();
                            showToast(noDataModel.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        KeyBoardUtil.hideSoftKeyboard(mContext,commentEdit);
                        hideLoadDialog();
                    }
                });
    }

    private void getCommentList(boolean isShow, final boolean isRefresh){
        if (isShow){
            showLoadDialog();
        }
        HashMap<String,String> params=new HashMap<>();
        params.put("id",id);
        params.put("size", CommonConstant.LIST_PAGE_SIZE);
        params.put("page",(isRefresh?1:page)+"");
        addNetwork(Api.getInstance().getService(ApiService.class).getCommentList(params)
                , new Action1<CommentListModel>() {
                    @Override
                    public void call(CommentListModel commentListModel) {
                        hideLoadDialog();
                        list.refreshComplete();
                        list.loadMoreComplete();
                        if (commentListModel.getCode()== CommonConstant.NET_SUC_CODE){
                            if (isRefresh){
                                adapter.refreshList(commentListModel.getData());
                            }else {
                                adapter.addList(commentListModel.getData());
                            }

                            if(adapter.getItemCount()<commentListModel.total){
                                list.setLoadingMoreEnabled(true);
                            }else{
                                list.setLoadingMoreEnabled(false);
                            }
                            page++;
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.e("aaaaa",throwable.toString());
                        hideLoadDialog();
                        list.refreshComplete();
                        list.loadMoreComplete();
                    }
                });
    }
}
