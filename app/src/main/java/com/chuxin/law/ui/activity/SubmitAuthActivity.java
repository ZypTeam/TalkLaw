package com.chuxin.law.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2018/2/4
 * @describe 提交审核
 */

public class SubmitAuthActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected EditText price;
    protected TextView submit;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_submit_auth;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.title_view);
        price = (EditText) findViewById(R.id.price);
        submit = (TextView) findViewById(R.id.submit);

    }

    @Override
    public void initAction() {
        titleView.setTitle("提交审核");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"认证",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void auth(){
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        addNetwork(Api.getInstance().getService(ApiService.class).lawyerAuth(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        if (noDataModel.getCode()== CommonConstant.NET_SUC_CODE){
                            showToast("认证成功");
                            setResult(RESULT_OK);
                            onBackPressed();
                        }else {
                            showToast(noDataModel.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        showToast("失败");
                    }
                });
    }
}
