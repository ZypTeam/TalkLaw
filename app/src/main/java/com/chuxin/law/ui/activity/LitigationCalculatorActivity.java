package com.chuxin.law.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.model.CaseTypeModel;
import com.chuxin.law.ui.adapter.CaseTypeAdapter;
import com.chuxin.law.util.calculator.LitigationUtil;
import com.chuxin.law.ui.view.CaseTypeDialog;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.Util.AppUtil;
import com.jusfoun.baselibrary.Util.KeyBoardUtil;

import com.chuxin.law.R;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1718:20
 * @Email zyp@jusfoun.com
 * @Description ${诉讼计算器}
 */
public class LitigationCalculatorActivity extends BaseTalkLawActivity {
    protected BackTitleView titleBar;
    protected TextView textCaseType;
    protected ImageView imgRightArrow;
    protected TextView textNo;
    protected TextView textYes;
    protected TextView textQuan;
    protected TextView textBan;
    protected TextView textResult;
    protected LinearLayout layouProperty;
    protected LinearLayout layoutCalculation;
    protected Button btnReset;
    protected Button btnAfrim;
    protected EditText editMoney;
    protected TextView textResultTitle;
    protected RelativeLayout layouPropertyRelationship;
    protected View linePropertyRelationship;
    protected RelativeLayout layoutEditMoney;
    private CaseTypeDialog caseTypeDialog;
    private CaseTypeModel.CaseTypeItemModel caseTypeItemModel;

    private int propertyType = 1;// 是否涉及财产关系标志位
    private int TYPE_YES = 1; // 是
    private int TYPE_NO = 0; // 否


    private int calculationType = 1; // 计算方式
    private int TYPE_ALL = 0; // 全额
    private int TYPE_HALVED = 1; // 减半

    private LitigationUtil litigationUtil;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_litigating_calculator;
    }

    @Override
    public void initDatas() {
        String typeJson = AppUtil.getRowJson(mContext, R.raw.casetype);
        caseTypeDialog = new CaseTypeDialog(mContext,typeJson);
        litigationUtil = new LitigationUtil();
    }

    @Override
    public void initView() {
        titleBar = (BackTitleView) findViewById(R.id.back_title_view);
        textCaseType = (TextView) findViewById(R.id.text_case_type);
        imgRightArrow = (ImageView) findViewById(R.id.img_right_arrow);
        textNo = (TextView) findViewById(R.id.text_no);
        textYes = (TextView) findViewById(R.id.text_yes);
        textQuan = (TextView) findViewById(R.id.text_quan);
        textBan = (TextView) findViewById(R.id.text_ban);
        textResult = (TextView) findViewById(R.id.text_result);
        layouProperty = (LinearLayout) findViewById(R.id.layou_property);
        layoutCalculation = (LinearLayout) findViewById(R.id.layout_calculation);
        btnReset = (Button) findViewById(R.id.btn_reset);
        btnAfrim = (Button) findViewById(R.id.btn_afrim);
        editMoney = (EditText) findViewById(R.id.edit_money);
        textResultTitle = (TextView) findViewById(R.id.text_result_title);
        layouPropertyRelationship = (RelativeLayout) findViewById(R.id.layou_property_relationship);
        linePropertyRelationship = (View) findViewById(R.id.line_property_relationship);
        layoutEditMoney = (RelativeLayout) findViewById(R.id.layout_edit_money);

    }

    @Override
    public void initAction() {
        titleBar.setTitle("诉讼计算器");
        textCaseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caseTypeDialog.show();
            }
        });
        caseTypeDialog.setCallBack(new CaseTypeAdapter.CallBack() {
            @Override
            public void getCaseType(CaseTypeModel.CaseTypeItemModel model) {
                caseTypeItemModel = model;
                if (model.type != 0) {
                    caseTypeDialog.dismiss();
                    if (model.id == 1) {
                        layouPropertyRelationship.setVisibility(View.GONE);
                        linePropertyRelationship.setVisibility(View.GONE);
                    } else {
                        layouPropertyRelationship.setVisibility(View.VISIBLE);
                        linePropertyRelationship.setVisibility(View.VISIBLE);
                    }
                    textCaseType.setText(model.title);
                }
            }
        });

        layouProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (propertyType == TYPE_YES) {
                    propertyType = TYPE_NO;
                    layouProperty.setBackgroundResource(R.drawable.bg_yes_no);
                    textNo.setTextColor(mContext.getResources().getColor(R.color.write));
                    textYes.setTextColor(0xffcb1e28);

                    layoutEditMoney.setVisibility(View.GONE);
                    linePropertyRelationship.setVisibility(View.GONE);
                } else {
                    propertyType = TYPE_YES;
                    layouProperty.setBackgroundResource(R.drawable.bg_no_yes);
                    textYes.setTextColor(mContext.getResources().getColor(R.color.write));
                    textNo.setTextColor(0xffcb1e28);

                    layoutEditMoney.setVisibility(View.VISIBLE);
                    linePropertyRelationship.setVisibility(View.VISIBLE);
                }
            }
        });

        layoutCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationType == TYPE_HALVED) {
                    calculationType = TYPE_ALL;
                    layoutCalculation.setBackgroundResource(R.drawable.bg_yes_no);

                    textQuan.setTextColor(mContext.getResources().getColor(R.color.write));
                    textBan.setTextColor(0xffcb1e28);
                } else {
                    calculationType = TYPE_HALVED;
                    layoutCalculation.setBackgroundResource(R.drawable.bg_no_yes);

                    textBan.setTextColor(mContext.getResources().getColor(R.color.write));
                    textQuan.setTextColor(0xffcb1e28);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textCaseType.setText("请选择案件类型");
                editMoney.setText("");

                propertyType = TYPE_YES;
                layouProperty.setBackgroundResource(R.drawable.bg_no_yes);
                textYes.setTextColor(mContext.getResources().getColor(R.color.write));
                textNo.setTextColor(0xffcb1e28);


                calculationType = TYPE_HALVED;
                layoutCalculation.setBackgroundResource(R.drawable.bg_no_yes);

                textBan.setTextColor(mContext.getResources().getColor(R.color.write));
                textQuan.setTextColor(0xffcb1e28);

                textResultTitle.setVisibility(View.GONE);
                textResult.setVisibility(View.GONE);

                layouPropertyRelationship.setVisibility(View.VISIBLE);
                linePropertyRelationship.setVisibility(View.VISIBLE);
            }
        });

        btnAfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textCaseType.getText().equals("请选择案件类型")) {
                    Toast.makeText(mContext, "请选择案件类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (propertyType==TYPE_YES&&TextUtils.isEmpty(editMoney.getText())) {
                    Toast.makeText(mContext, "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (caseTypeItemModel != null) {
                    textResultTitle.setVisibility(View.VISIBLE);
                    textResult.setVisibility(View.VISIBLE);

                    boolean isHalved ;
                    if (calculationType == TYPE_ALL) {
                        isHalved = false;
                    } else {
                        isHalved = true;
                    }
                    int price;
                    if(propertyType==TYPE_NO){
                        price = 0;
                    }else{
                        price =  Integer.parseInt(editMoney.getText().toString());
                    }
                    textResult.setText(litigationUtil.getCost(caseTypeItemModel.id, price, isHalved));
                    KeyBoardUtil.hideSoftKeyboard(LitigationCalculatorActivity.this);
                }

            }
        });
    }


}
