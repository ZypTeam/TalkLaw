package com.chuxin.law.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author zhaoyapeng
 * @version create time:18/2/718:26
 * @Email zyp@jusfoun.com
 * @Description ${申请提现页面}
 */
public class ApplyForWithdrawalsActivity extends BaseTalkLawActivity {
    protected BackTitleView backTitleView;
    protected ImageView imgTitle;
    protected TextView textNum;
    protected EditText editMoney;
    protected TextView textYue;
    protected TextView textYueNum;
    protected TextView textTixianAll;
    protected TextView textTixian;
    protected LinearLayout layoutAdd;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_apply_for_withdrawals;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        imgTitle = (ImageView) findViewById(R.id.img_title);
        textNum = (TextView) findViewById(R.id.text_num);
        editMoney = (EditText) findViewById(R.id.edit_money);
        textYue = (TextView) findViewById(R.id.text_yue);
        textYueNum = (TextView) findViewById(R.id.text_yue_num);
        textTixianAll = (TextView) findViewById(R.id.text_tixian_all);
        textTixian = (TextView) findViewById(R.id.text_tixian);
        layoutAdd = (LinearLayout) findViewById(R.id.layout_add);

    }

    @Override
    public void initAction() {
        backTitleView.setTitle("申请提现");
        backTitleView.setRightText("账户明细", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, AccountDetailsActivity.class);
            }
        });
        layoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, NewAddYinHangActivity.class);
            }
        });
    }
}
