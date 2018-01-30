package com.chuxin.law.ui.activity;

import com.chuxin.law.base.BaseTalkLawActivity;

import com.chuxin.law.R;

import com.chuxin.law.ui.view.SearchGuideView;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1820:50
 * @Email zyp@jusfoun.com
 * @Description ${搜索页面}
 */
public class SearchActivity extends BaseTalkLawActivity {
    protected SearchGuideView viewSearchGuide;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        viewSearchGuide = (SearchGuideView) findViewById(R.id.view_search_guide);

    }

    @Override
    public void initAction() {
        viewSearchGuide.setHistoryData();
        viewSearchGuide.setHotSearchData();
    }
}
