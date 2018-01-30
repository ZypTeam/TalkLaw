package com.chuxin.law.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.view.wheel.dialog.SelectorByArrayDialog;
import com.chuxin.law.ui.widget.BackTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private RelativeLayout rl_case_type,rl_is_InvolveProperty,rl_suit_money;
    private LinearLayout ll_yes_or_no;
    private TextView tv_case_type_result,text_result, tv_no, tv_yes;
    private Button btn_lawyer_reset,btn_lawyer_calculate;
    private EditText ed_property;
    private View line_check,line_edit;


    private String caseTypeArray[] = {"民事诉讼","刑事案件","行政案件"};

    private List<String> caseList;

    private String mCaseType = "";
    private int mCaseTypeIndex = -1;

    private boolean isInvolveProperty = true;// 是否涉及财产关系标志位
    private int inputProperty = 0;

    private int propertyType = 1;// 是否涉及财产关系标志位
    private int TYPE_YES = 1; // 是
    private int TYPE_NO = 0; // 否

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
        ll_yes_or_no = findViewById(R.id.ll_yes_or_no);

        rl_is_InvolveProperty = findViewById(R.id.rl_is_InvolveProperty);
        rl_suit_money = findViewById(R.id.rl_suit_money);
        line_check = findViewById(R.id.line_check);
        line_edit = findViewById(R.id.line_edit);
        tv_no = findViewById(R.id.tv_no);
        tv_yes = findViewById(R.id.tv_yes);

        ed_property = findViewById(R.id.ed_property);
    }

    @Override
    public void initAction() {
        titleBar.setTitle("律师费计算器");
        dialog.setOnWheelViewListener(new SelectorByArrayDialog.OnWheelViewListener() {
            @Override
            public void onOkClick(String content) {
                if(!TextUtils.isEmpty(content)){
                    text_result.setText("");
                    mCaseType = content;
                    if(caseTypeArray[1].equals(mCaseType)){
                        rl_is_InvolveProperty.setVisibility(View.GONE);
                        rl_suit_money.setVisibility(View.GONE);
                        line_check.setVisibility(View.GONE);
                        line_edit.setVisibility(View.GONE);
                    }else {
                        rl_is_InvolveProperty.setVisibility(View.VISIBLE);
                        rl_suit_money.setVisibility(View.VISIBLE);
                        line_check.setVisibility(View.VISIBLE);
                        line_edit.setVisibility(View.VISIBLE);
                    }
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
        ll_yes_or_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInvolveProperty){
                    isInvolveProperty = false;
                    ll_yes_or_no.setBackgroundResource(R.drawable.bg_yes_no);
                    tv_no.setTextColor(mContext.getResources().getColor(R.color.write));
                    tv_yes.setTextColor(0xffcb1e28);

                    rl_suit_money.setVisibility(View.GONE);
                    line_edit.setVisibility(View.GONE);
                }else {
                    isInvolveProperty = true;
                    ll_yes_or_no.setBackgroundResource(R.drawable.bg_no_yes);
                    tv_yes.setTextColor(mContext.getResources().getColor(R.color.write));
                    tv_no.setTextColor(0xffcb1e28);

                    rl_suit_money.setVisibility(View.VISIBLE);
                    line_edit.setVisibility(View.VISIBLE);
                }
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

        if(caseTypeArray[0].equals(mCaseType)){
            if(isInvolveProperty){
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
                if(inputProperty <= UNIT){
                    text_result.setText("律师费：1000~3000（元）");
                }else if(inputProperty > UNIT && inputProperty <= 10 * UNIT ){
                    text_result.setText("律师费："+ (inputProperty * 0.08) + "~" + (inputProperty * 0.09)+ "（元）");
                }else if(inputProperty > 10 * UNIT && inputProperty <= 100 * UNIT){
                    text_result.setText("律师费："+ (inputProperty * 0.06) + "~" + (inputProperty * 0.08)+ "（元）");
                }else if(inputProperty > 100 * UNIT && inputProperty <= 500 * UNIT){
                    text_result.setText("律师费："+ (inputProperty * 0.04) + "~" + (inputProperty * 0.06)+ "（元）");
                }else if(inputProperty > 500 * UNIT && inputProperty <= 1000 * UNIT){
                    text_result.setText("律师费："+ (inputProperty * 0.02) + "~" + (inputProperty * 0.04)+ "（元）");
                }else if(inputProperty > 1000 * UNIT && inputProperty <= 5000 * UNIT){
                    text_result.setText("律师费："+ (inputProperty * 0.01) + "~" + (inputProperty * 0.02)+ "（元）");
                }else if(inputProperty > 5000 * UNIT ){
                    text_result.setText("律师费："+ (inputProperty * 0.005) + "~" + (inputProperty * 0.01)+ "（元）");
                }
            }else {
                text_result.setText("律师费：1000~2000（元）");
            }
        }else if(caseTypeArray[1].equals(mCaseType)){
            text_result.setText("侦查阶段：                 3000元—30000（元/件）" +
                              "\n审查起诉阶段：         5000元—50000（元/件）" +
                              "\n审判阶段：                 5000元—50000（元/件）" +
                              "\n办理死刑复核案件： 20000—30000（元/件）" +
                              "\n担任刑事自诉案件的原告或公诉案件被害人的代理人参加诉讼的，不涉及财产关系的:5000—30000（元/件）" );

        }else {
            if(isInvolveProperty){
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
                if(inputProperty <= UNIT){
                    text_result.setText("律师费：1000~3000（元/件）");
                }else if(inputProperty > UNIT && inputProperty <= 10 * UNIT ){
                    text_result.setText("律师费："+ (inputProperty * 0.08) + "~" + (inputProperty * 0.09)+ "（元/件）");
                }else if(inputProperty > 10 * UNIT && inputProperty <= 100 * UNIT){
                    text_result.setText("律师费："+ (inputProperty * 0.06) + "~" + (inputProperty * 0.08)+ "（元/件）");
                }else if(inputProperty > 100 * UNIT && inputProperty <= 500 * UNIT){
                    text_result.setText("律师费："+ (inputProperty * 0.04) + "~" + (inputProperty * 0.06)+ "（元/件）");
                }else if(inputProperty > 500 * UNIT && inputProperty <= 1000 * UNIT){
                    text_result.setText("律师费："+ (inputProperty * 0.02) + "~" + (inputProperty * 0.04)+ "（元/件）");
                }else if(inputProperty > 1000 * UNIT && inputProperty <= 5000 * UNIT){
                    text_result.setText("律师费："+ (inputProperty * 0.01) + "~" + (inputProperty * 0.02)+ "（元/件）");
                }else if(inputProperty > 5000 * UNIT ){
                    text_result.setText("律师费："+ (inputProperty * 0.005) + "~" + (inputProperty * 0.01)+ "（元/件）");
                }
            }else{
                text_result.setText("律师费：1000—20000（元/件）");
            }
        }


    }
}
