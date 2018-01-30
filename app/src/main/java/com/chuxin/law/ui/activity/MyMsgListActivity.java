package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.widget.TitleStatusBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.comment.ApiService;
import com.chuxin.law.comment.CommentConstant;
import com.chuxin.law.comment.AdapterDelCallback;
import com.chuxin.law.model.MyMsgListModel;
import com.chuxin.law.model.MyMsgModel;
import com.chuxin.law.ui.adapter.MyMsgListAdapter;

import rx.functions.Action1;

import static com.chuxin.law.comment.CommentConstant.NET_SUC_CODE;

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

    private int page=0;
    private String size= CommentConstant.LIST_PAGE_SIZE;

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
        list.setPullRefreshEnabled(true);
        list.setLoadingMoreEnabled(true);
        list.setAdapter(adapter);
        list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData(true,false);
            }

            @Override
            public void onLoadMore() {
                getData(false,false);
            }
        });

        getData(true,true);

        adapter.setCallback(new AdapterDelCallback() {
            @Override
            public void del(Object model, int position) {
                delMsg((MyMsgModel) model);
            }
        });
    }

    private void delMsg(MyMsgModel model){
        if (model==null){
            return;
        }
        List<MyMsgModel> list=new ArrayList<>();
        list.add(model);
        delMsg(list);
    }

    private void delMsg(final List<MyMsgModel> models){
        if (models==null||models.size()==0){
            return;
        }
        showLoadDialog();
        String ids="";
        for (MyMsgModel model : models) {
            ids+=model.getId()+",";
        }
        HashMap<String,String> params=new HashMap<>();
        params.put("id",ids);
        addNetwork(Api.getInstance().getService(ApiService.class).allMsgRead(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel!=null&&noDataModel.getCode()==NET_SUC_CODE){
                            adapter.remove(models);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void getData(final boolean isRefresh, boolean isShowLoading){

        if (isShowLoading){
            showLoadDialog();
        }
        Map<String,String> params=new HashMap<>();
        params.put("size",size);
        params.put("page",(isRefresh?1:page+1)+"");
        addNetwork(Api.getInstance().getService(ApiService.class).getSystemMsg(params)
                , new Action1<MyMsgListModel>() {
                    @Override
                    public void call(MyMsgListModel myMsgListModel) {
                        loadingComplete();
                        if (myMsgListModel!=null&&myMsgListModel.getCode()==10000){
                            adapter.refreshList(myMsgListModel.getData());

                            if (isRefresh){
                                page=1;
                            }else {
                                page+=1;
                            }
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingComplete();
                    }
                });
    }

    private void loadingComplete(){
        hideLoadDialog();
        list.loadMoreComplete();
        list.refreshComplete();
    }
}
