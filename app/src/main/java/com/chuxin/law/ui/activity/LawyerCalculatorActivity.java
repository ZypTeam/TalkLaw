package com.chuxin.law.ui.activity;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.widget.BackTitleView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.view.wheel.dialog.SelectorByArrayDialog;
import cn.com.talklaw.ui.widget.BackTitleView;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1718:20
 * @Email zyp@jusfoun.com
 * @Description ${律师费计算器}
 */
public class LawyerCalculatorActivity extends BaseTalkLawActivity {
    public static final int UNIT = 10000;

    protected BackTitleView titleBar;

    private SelectorByArrayDialog dialog;
    private RelativeLayout rl_case_type;
    private TextView tv_case_type_result,text_result;
    private Button btn_lawyer_reset,btn_lawyer_calculate;
    private EditText ed_property;


    private String caseTypeArray[] = {"民事诉讼","刑事案件","行政案件"};

    private List<String> caseList;

    private String mCaseType = "";
    private int mCaseTypeIndex = -1;

    private boolean isInvolveProperty = true;
    private int inputProperty = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_lawyer_calculator;
    }

    @Override
    public void initDatas() {
        dialog = new SelectorByArrayDialog(mContext, R.style.my_dialog);
        caseList = new ArrayList<>();
        caseList = Arrays.asList(caseTypeArray);
        dialog.setTitle("选择案件类型");
        dialog.setData(caseList);
    }

    @Override
    public void initView() {
        titleBar = (BackTitleView) findViewById(R.id.back_title_view);
        rl_case_type = findViewById(R.id.rl_case_type);

        tv_case_type_result = findViewById(R.id.tv_case_type_result);

        text_result = findViewById(R.id.text_result);
        btn_lawyer_reset = findViewById(R.id.btn_lawyer_reset);
        btn_lawyer_calculate = findViewById(R.id.btn_lawyer_calculate);

        ed_property = findViewById(R.id.ed_property);
    }

    @Override
    public void initAction() {
        titleBar.setTitle("律师费计算器");
        dialog.setOnWheelViewListener(new SelectorByArrayDialog.OnWheelViewListener() {
            @Override
            public void onOkClick(String content) {
                if(!TextUtils.isEmpty(content)){
                    mCaseType = content;
                    tv_case_type_result.setText(content);
                }
            }
        });
        rl_case_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        btn_lawyer_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });

        btn_lawyer_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
    }

    private void resetData(){
        mCaseType = "";
        mCaseTypeIndex = -1;
        ed_property.setText("");
        inputProperty = 0;

        isInvolveProperty = true;
        text_result.setText("");
    }

    private void calculate(){
        if(TextUtils.isEmpty(mCaseType)){
            showToast("请输入案件类型");
            return;
        }
        String getInputProperty = ed_property.getText().toString();
        if(TextUtils.isEmpty(getInputProperty)){
            showToast("请输入诉讼标金额");
            return;
        }
        try {
            inputProperty = Integer.parseInt(getInputProperty);
        }catch (Exception e){
            inputProperty = 0;
        }
        if(caseTypeArray[0].equals(mCaseType)){
            if(isInvolveProperty){
                if(inputProperty <= UNIT){
                    text_result.setText("1000~3000");
                }else if(inputProperty > UNIT && inputProperty <= 10 * UNIT){
                    text_result.setText((inputProperty * 0.08) + "~" + (inputProperty * 0.09));
                }else if(inputProperty > 10 * UNIT && inputProperty <= 100 * UNIT){

                }else if(inputProperty > 100 * UNIT && inputProperty <= 500 * UNIT){

                }else if(inputProperty > 500 * UNIT && inputProperty <= 1000 * UNIT){

                }else if(inputProperty > 1000 * UNIT && inputProperty <= 5000 * UNIT){

                }else if(inputProperty > 5000 * UNIT ){

                }
            }else {
                text_result.setText("1000~2000");
            }
        }else if(caseTypeArray[1].equals(mCaseType)){

        }else {

        }


    }
}
