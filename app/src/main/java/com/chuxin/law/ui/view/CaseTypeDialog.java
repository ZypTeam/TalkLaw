package com.chuxin.law.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chuxin.law.model.CaseTypeModel;
import com.chuxin.law.ui.adapter.CaseTypeAdapter;
import com.google.gson.Gson;
import com.jusfoun.baselibrary.Util.AppUtil;

import com.chuxin.law.R;


/**
 * @author zhaoyapeng
 * @version create time:18/1/2611:09
 * @Email zyp@jusfoun.com
 * @Description ${案件类型dialog}
 */
public class CaseTypeDialog extends Dialog {
    protected RecyclerView recyclerview;
    private Context mContext;
    private CaseTypeAdapter caseTypeAdapter;

    private CaseTypeModel caseTypeModel;

    public CaseTypeDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public CaseTypeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    protected CaseTypeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    private void initData() {
        caseTypeModel = new Gson().fromJson(AppUtil.getRowJson(mContext, R.raw.casetype), CaseTypeModel.class);
    }

    private void initViews() {
        setContentView(R.layout.dialog_case_type);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(caseTypeAdapter);
        caseTypeAdapter = new CaseTypeAdapter(mContext);
    }

    private void initActions() {
        recyclerview.setAdapter(caseTypeAdapter);
        if (caseTypeModel != null && caseTypeModel.list != null) {
            caseTypeAdapter.refresh(caseTypeModel.list);
        }


    }

    public void setCallBack(CaseTypeAdapter.CallBack callBack){
        caseTypeAdapter.setCallBack(callBack);
    }


}
