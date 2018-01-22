package cn.com.talklaw.ui.activity;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.view.SearchGuideView;

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
