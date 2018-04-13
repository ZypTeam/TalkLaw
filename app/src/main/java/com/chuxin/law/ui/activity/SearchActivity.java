package com.chuxin.law.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.HotKeyListModel;
import com.chuxin.law.model.ProductsModel;
import com.chuxin.law.ui.adapter.ProductListAdapter;
import com.chuxin.law.ui.view.SearchGuideView;
import com.chuxin.law.ui.widget.xRecyclerView.XRecyclerView;
import com.chuxin.law.util.ShareUtils;
import com.jusfoun.baselibrary.Util.SharePrefenceUtils;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import rx.functions.Action1;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1820:50
 * @Email zyp@jusfoun.com
 * @Description ${搜索页面}
 */
public class SearchActivity extends BaseTalkLawActivity {
    private static final String HISTORY="history_list";
    protected SearchGuideView viewSearchGuide;
    protected ImageView back;
    protected TextView textCancle;
    private ProductListAdapter adapter;
    private XRecyclerView result;
    private int page;
    private String keyword;
    private EditText editSearch;
    private HashSet<String> historySearch;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    public void initDatas() {
        adapter = new ProductListAdapter(mContext);
    }

    @Override
    public void initView() {
        viewSearchGuide = (SearchGuideView) findViewById(R.id.view_search_guide);
        result = findViewById(R.id.result);
        back = (ImageView) findViewById(R.id.back);
        editSearch = (EditText) findViewById(R.id.edit_search);
        textCancle = (TextView) findViewById(R.id.text_cancle);

    }

    @Override
    public void initAction() {
        historySearch= (HashSet<String>) SharePrefenceUtils.getInstance().getSet(HISTORY);
        viewSearchGuide.setHistoryData(historySearch);

        viewSearchGuide.setCallBack(new SearchGuideView.CallBack() {
            @Override
            public void search(String key) {
                keyword=key;
                editSearch.setText(key);
                SearchActivity.this.search(key,true);
            }
        });

        result.setPullRefreshEnabled(true);
        result.setLoadingMoreEnabled(false);
        result.setLayoutManager(new LinearLayoutManager(mContext));
        result.setAdapter(adapter);
        result.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                search(keyword, true);
            }

            @Override
            public void onLoadMore() {
                search(keyword, false);
            }
        });

        textCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_DOWN){
                    keyword=editSearch.getText().toString();
                    search(keyword,true);
                    return true;
                }
                return false;
            }
        });

        viewSearchGuide.setOnDelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historySearch.clear();
                SharePrefenceUtils.getInstance().setSet(HISTORY,historySearch);
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtil.isEmpty(s.toString())){
                    viewSearchGuide.setVisibility(View.VISIBLE);
                }
            }
        });

        getHotKey();

    }

    private void search(String keyword,final boolean isRefrsh) {
        if (StringUtil.isEmpty(keyword)){
            showToast("不能为空");
            return;
        }
        viewSearchGuide.setVisibility(View.GONE);
        HashMap<String, String> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("page", isRefrsh ? "1" : (page + 1) + "");
        params.put("size", CommonConstant.LIST_PAGE_SIZE);
        showLoadDialog();
        if (!historySearch.contains(keyword)) {
            if (historySearch.size() < 20) {
                historySearch.add(keyword);
            } else {
                for (Iterator<String> iter = historySearch.iterator(); iter.hasNext(); ) {
                    //移除第一个
                    iter.remove();
                    break;
                }
                historySearch.add(keyword);
            }
            SharePrefenceUtils.getInstance().setSet(HISTORY,historySearch);
            viewSearchGuide.setHistoryData(historySearch);
        }
        addNetwork(Api.getInstance().getService(ApiService.class).search(params)
                , new Action1<ProductsModel>() {
                    @Override
                    public void call(ProductsModel productsModel) {
                        hideLoadDialog();
                        result.setVisibility(View.VISIBLE);
                        if (productsModel.getCode() == CommonConstant.NET_SUC_CODE) {
                            if (isRefrsh) {
                                page = 1;
                                adapter.refreshList(productsModel.getData());
                            } else {
                                page++;
                                adapter.addList(productsModel.getData());
                            }

                            if (adapter.getItemCount()>productsModel.total){
                                result.setLoadingMoreEnabled(true);
                            }else {
                                result.setLoadingMoreEnabled(false);
                            }
                            return;
                        }
                        showToast(productsModel.getMsg());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void getHotKey(){
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("keyword","看法");
        addNetwork(Api.getInstance().getService(ApiService.class).hotKeyword(params)
                , new Action1<HotKeyListModel>() {
                    @Override
                    public void call(HotKeyListModel hotKeyListModel) {
                        hideLoadDialog();
                        if (hotKeyListModel.getCode()==CommonConstant.NET_SUC_CODE){
                            viewSearchGuide.setHotSearchData(hotKeyListModel.getData());
                            return;
                        }
                        showToast("获取热词失败");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }
}
