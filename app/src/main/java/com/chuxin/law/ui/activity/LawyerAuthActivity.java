package com.chuxin.law.ui.activity;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2018/2/2
 * @describe
 */

public class LawyerAuthActivity extends BaseTalkLawActivity {
    @Override
    public int getLayoutResId() {
        return R.layout.activity_lawyer_auth;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initAction() {

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
