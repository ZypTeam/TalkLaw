package com.chuxin.law.ui.activity;

import android.support.v7.widget.RecyclerView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author zhaoyapeng
 * @version create time:18/2/720:14
 * @Email zyp@jusfoun.com
 * @Description ${账户明细}
 */
public class AccountDetailsActivity extends BaseTalkLawActivity {
    protected BackTitleView backTitleView;
    protected RecyclerView recyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_account_details;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public void initAction() {
        backTitleView.setTitle("账户明细");
    }
}
