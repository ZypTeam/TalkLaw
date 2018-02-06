package com.chuxin.law.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.AuthLawyerModel;
import com.chuxin.law.ui.util.base64.Base64Util;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author wangcc
 * @date 2018/2/4
 * @describe 提交审核
 */

public class SubmitAuthActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected EditText price;
    protected TextView submit;
    private AuthLawyerModel model;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_submit_auth;
    }

    @Override
    public void initDatas() {
        if (getIntent().getExtras() != null) {
            model = (AuthLawyerModel) getIntent().getExtras().getSerializable("model");
        }
        if (model == null) {
            onBackPressed();
        }
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

                auth();
            }
        });
    }

    private void auth() {
        if (StringUtil.isEmpty(price.getText().toString())){
            return;
        }
        showLoadDialog();
        final HashMap<String, String> params = new HashMap<>();
        params.put("law_firm", model.getLaw_firm());
        params.put("price",price.getText().toString());
        params.put("starttime",model.getStarttime());
        params.put("endtime","");
        Observable observable = Observable.just(params)
                .map(new Func1() {
                    @Override
                    public Object call(Object o) {
                        String heading=Base64Util.encodeBase64File(mContext,model.getHeadimg());
                        params.put("heading", heading);
                        String cer = Base64Util.encodeBase64File(mContext, model.getCertificate());
                        params.put("certificate", cer);
                        String year = Base64Util.encodeBase64File(mContext, model.getCertificate_year());
                        params.put("certificate_year", year);
                        return "";
                    }
                })
                .flatMap(new Func1() {
                    @Override
                    public Object call(Object o) {
                        return Api.getInstance().getService(ApiService.class).lawyerAuth(params);
                    }
                });
        addNetwork(observable, new Action1<NoDataModel>() {
            @Override
            public void call(NoDataModel noDataModel) {
                hideLoadDialog();
                if (noDataModel.getCode() == CommonConstant.NET_SUC_CODE) {
                    showToast("已经提交");
                    setResult(RESULT_OK);
                    onBackPressed();
                } else {
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
