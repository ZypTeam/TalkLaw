package com.chuxin.law.ui.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.model.MyMsgModel;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.base.BaseActivity;

/**
 * @author wangcc
 * @date 2018/3/21
 * @describe
 */

public class MsgDefActivity extends BaseTalkLawActivity {
    public static final String MY_MSG_MODEL="my_msg_model";
    protected BackTitleView titleView;
    protected ImageView icon;
    protected TextView title;
    protected TextView content;
    protected TextView time;
    private MyMsgModel model;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_msg_def;
    }

    @Override
    public void initDatas() {
        model= (MyMsgModel) getIntent().getSerializableExtra(MY_MSG_MODEL);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.title_view);
        icon = (ImageView) findViewById(R.id.icon);
        title = (TextView) findViewById(R.id.title_txt);
        content = (TextView) findViewById(R.id.content);
        time = (TextView) findViewById(R.id.time);

    }

    @Override
    public void initAction() {
        time.setText(model.getCreatetime());
        content.setText(model.getContent());
        title.setText("系统消息");
    }
}
