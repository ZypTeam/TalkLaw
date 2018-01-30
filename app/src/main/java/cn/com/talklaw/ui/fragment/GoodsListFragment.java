package cn.com.talklaw.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jusfoun.baselibrary.base.BaseViewPagerFragment;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import cn.com.talklaw.R;
import cn.com.talklaw.comment.ApiService;
import cn.com.talklaw.model.GoodsDataModel;
import cn.com.talklaw.model.IntegralModel;
import cn.com.talklaw.ui.adapter.GoodsListAdapter;
import rx.functions.Action1;

import static cn.com.talklaw.comment.CommentConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2914:26
 * @Email zyp@jusfoun.com
 * @Description ${商城产品列表 fragment}
 */
public class GoodsListFragment extends BaseViewPagerFragment {

    protected RecyclerView recyclerview;
    private GoodsListAdapter adapter;
    private IntegralModel.CatItemModel model;


    public static  GoodsListFragment getInstance(IntegralModel.CatItemModel model){
        GoodsListFragment goodsListFragment = new GoodsListFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("model",model);
        goodsListFragment.setArguments(bundle);
        return goodsListFragment;
    }

    @Override
    protected void refreshData() {
        delMsg();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_goods_list;
    }

    @Override
    public void initDatas() {
        if(getArguments()!=null){
            model = (IntegralModel.CatItemModel)getArguments().getSerializable("model");
        }
        adapter = new GoodsListAdapter(mContext);
    }

    @Override
    public void initView(View rootView) {
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview);

    }

    @Override
    public void initAction() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(adapter);
    }

    private void delMsg() {
        Log.e("tag","delMsgdelMsgdelMsg1");
        HashMap<String,String> params = new HashMap<>();
        params.put("cat",model.id);
        addNetwork(Api.getInstance().getService(ApiService.class).getAllGoods(params)
                , new Action1<GoodsDataModel>() {
                    @Override
                    public void call(GoodsDataModel model) {
                        Log.e("tag","delMsgdelMsgdelMsg2");
                        hideLoadDialog();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            adapter.refreshList(model.data);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("tag","delMsgdelMsgdelMsg3="+throwable);
                        hideLoadDialog();
                    }
                });
    }
}
