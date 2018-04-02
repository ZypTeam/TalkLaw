package com.chuxin.law.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.AuthLawyerModel;
import com.chuxin.law.util.base64.Base64Util;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
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
    private Subscription observable;

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
        try {
            Float p = Float.parseFloat(price.getText().toString());
        } catch (Exception e) {
            showToast("价格输入错误,请输入数字");
            return;
        }
        showLoadDialog();
        final HashMap<String, String> params = new HashMap<>();
        params.put("law_firm", model.getLaw_firm());
        params.put("price", price.getText().toString());
        params.put("starttime", model.getStarttime());
        params.put("endtime", ""+System.currentTimeMillis());
        observable = Observable.just(params)
                .map(new Func1() {
                    @Override
                    public Object call(Object o) {
                        String heading = Base64Util.encodeBase64File(mContext, model.getHeadimg());
                        params.put("heading", "data:image/jpeg;base64," + heading);
                        String cer = Base64Util.encodeBase64File(mContext, model.getCertificate());
                        params.put("certificate", "data:image/jpeg;base64," + cer);
                        String year = Base64Util.encodeBase64File(mContext, model.getCertificate_year());
                        params.put("certificate_year", "data:image/jpeg;base64," + year);
                        return "";
                    }
                })
                .flatMap(new Func1() {
                    @Override
                    public Object call(Object o) {

                        return Api.getInstance().getService(ApiService.class).lawyerAuth(params);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        NoDataModel noDataModel = (NoDataModel) o;
                        hideLoadDialog();
                        if (noDataModel.getCode() == CommonConstant.NET_SUC_CODE) {
                            showToast("提交成功，请等待审核");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (observable!=null&&!observable.isUnsubscribed()){
            observable.unsubscribe();
        }
    }
}
