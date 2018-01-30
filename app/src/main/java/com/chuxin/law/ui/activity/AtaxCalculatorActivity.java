package com.chuxin.law.ui.activity;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.model.CaseTypeModel;
import com.chuxin.law.ui.adapter.CaseTypeAdapter;
import com.chuxin.law.ui.view.CaseTypeDialog;
import com.chuxin.law.ui.widget.BackTitleView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.baselibrary.Util.AppUtil;
import com.jusfoun.baselibrary.Util.KeyBoardUtil;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1718:20
 * @Email zyp@jusfoun.com
 * @Description ${个税计算器}
 */
public class AtaxCalculatorActivity extends BaseTalkLawActivity {
    protected BackTitleView titleBar;
    private RelativeLayout rl_income_type;

    private TextView tv_income_type_text,text_result;
    private CaseTypeDialog caseTypeDialog;
    private EditText ed_total_income,ed_social_security;
    private Button btn_reset,btn_submit;

    private String mIncomeType = "";

    private int mTotalIncome = 0, mSocialSecurity = 0;
    private int incomeTax = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_atax_calculator;
    }

    @Override
    public void initDatas() {
        String typeJson = AppUtil.getRowJson(mContext, R.raw.income_type);
        caseTypeDialog = new CaseTypeDialog(mContext,typeJson);
    }

    @Override
    public void initView() {
        titleBar = (BackTitleView) findViewById(R.id.back_title_view);
        rl_income_type = findViewById(R.id.rl_income_type);
        tv_income_type_text = findViewById(R.id.tv_income_type_text);

        ed_total_income = findViewById(R.id.ed_total_income);
        ed_social_security = findViewById(R.id.ed_social_security);
        text_result = findViewById(R.id.text_result);

        btn_reset = findViewById(R.id.btn_reset);
        btn_submit = findViewById(R.id.btn_submit);
    }

    @Override
    public void initAction() {
        titleBar.setTitle("个税计算器");

        caseTypeDialog.setTitleText("选择收入类型");

        rl_income_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caseTypeDialog.show();
            }
        });

        caseTypeDialog.setCallBack(new CaseTypeAdapter.CallBack() {
            @Override
            public void getCaseType(CaseTypeModel.CaseTypeItemModel model) {
                mIncomeType = model.title;
                tv_income_type_text.setText(mIncomeType);
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetView();
                KeyBoardUtil.hideSoftKeyboard(AtaxCalculatorActivity.this);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
                KeyBoardUtil.hideSoftKeyboard(AtaxCalculatorActivity.this);
            }
        });

    }

    /**
     * 重置
     */
    private void resetView(){
        mTotalIncome = 0;
        mSocialSecurity = 0;
        ed_total_income.setText("");
        ed_social_security.setText("");
        tv_income_type_text.setText("");
        mIncomeType = "";
    }

    /**
     * 计算
     */
    private void calculate(){
        try {
            mTotalIncome = Integer.parseInt(ed_total_income.getText().toString());
            mSocialSecurity = Integer.parseInt(ed_social_security.getText().toString());
        }catch (Exception e){
            mTotalIncome = 0;
            mSocialSecurity = 0;
        }

        if(mTotalIncome == 0){
            showToast("请输入收入金额");
            return;
        }

        if(mSocialSecurity == 0){
            showToast("请输入社保公积金");
            return;
        }
        int personAmountOfIncome = mTotalIncome - mSocialSecurity - 3500;

        if(personAmountOfIncome <= 0){
            incomeTax = 0;
        }else if(personAmountOfIncome <= 1500 && personAmountOfIncome > 0){
            incomeTax = (int) (personAmountOfIncome * 0.03);
        }else if(personAmountOfIncome <= 4500 && personAmountOfIncome > 1500){
            incomeTax = (int) (personAmountOfIncome * 0.1) - 105;
        }else if(personAmountOfIncome <= 9000 && personAmountOfIncome > 4500){
            incomeTax = (int) (personAmountOfIncome * 0.2) - 555;
        }else if(personAmountOfIncome <= 35000 && personAmountOfIncome > 9000){
            incomeTax = (int) (personAmountOfIncome * 0.25) - 1005;
        }else if(personAmountOfIncome <= 55000 && personAmountOfIncome > 35000){
            incomeTax = (int) (personAmountOfIncome * 0.3) - 2755;
        }else if(personAmountOfIncome <= 80000 && personAmountOfIncome > 55000){
            incomeTax = (int) (personAmountOfIncome * 0.35) - 5505;
        }else if(personAmountOfIncome > 80000){
            incomeTax = (int) (personAmountOfIncome * 0.45) - 13505;
        }

        text_result.setText("个人所得税：" + incomeTax + "（元）");
    }
}
