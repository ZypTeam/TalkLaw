package com.chuxin.law.ui.activity;

import android.widget.EditText;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.widget.BackTitleView;

/**
 * @author zhaoyapeng
 * @version create time:18/2/719:58
 * @Email zyp@jusfoun.com
 * @Description ${新增银行卡信息}
 */
public class NewAddYinHangActivity extends BaseTalkLawActivity {
    protected BackTitleView backTitleView;
    protected EditText editName;
    protected EditText editNum;
    protected EditText editPhone;
    protected TextView textAfrim;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_new_add_yinhang;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        editName = (EditText) findViewById(R.id.edit_name);
        editNum = (EditText) findViewById(R.id.edit_num);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        textAfrim = (TextView) findViewById(R.id.text_afrim);

    }

    @Override
    public void initAction() {
        backTitleView.setTitle("新增银行卡信息");
    }
}
