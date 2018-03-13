package com.chuxin.law.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.ui.widget.NumberPickerPopupwinow;
import com.chuxin.law.util.DateUtil;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1718:20
 * @Email zyp@jusfoun.com
 * @Description ${日期计算器}
 */
public class DateCalculatorFragment extends BaseTalkLawFragment {

    protected TextView textStart;
    protected TextView textEnd;
    protected Button btnReset;
    protected Button btnJisuan;
    protected TextView textResult;
    private NumberPickerPopupwinow startNumPickPop, endNumPickPop;

    public static DateCalculatorFragment getInstance() {
        return new DateCalculatorFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_date_calcuator;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView(View rootView) {

        textStart = (TextView) rootView.findViewById(R.id.text_start);
        textEnd = (TextView) rootView.findViewById(R.id.text_end);
        btnReset = (Button) rootView.findViewById(R.id.btn_reset);
        btnJisuan = (Button) rootView.findViewById(R.id.btn_jisuan);
        textResult = (TextView) rootView.findViewById(R.id.text_result);


        startNumPickPop = new NumberPickerPopupwinow(mContext,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startNumPickPop.dismiss();
                        textStart.setText(startNumPickPop.getData2());
                    }
                });

        endNumPickPop = new NumberPickerPopupwinow(mContext,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        endNumPickPop.dismiss();
                        textEnd.setText(endNumPickPop.getData2());
                    }
                });

    }

    @Override
    public void initAction() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textStart.setText("请选择日期");
                textEnd.setText("请选择日期");
                textResult.setText("");
            }
        });

        btnJisuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textStart.getText().equals("请选择日期")){
                    showToast("请选择开始日期");
                    return;
                }
                if(textEnd.getText().equals("请选择日期")){
                    showToast("请选择开始日期");
                    return;
                }
//                if(isQian) {
//                    textResult.setText(DateUtil.getDateBefore(numPickPop.getData2(), Integer.parseInt(editInputDay.getText().toString())));
//                }else {
//                    textResult.setText(DateUtil.getDateBefore(numPickPop.getData2(),-Integer.parseInt(editInputDay.getText().toString())));
//                }
//

                textResult.setText(DateUtil.compareDate(startNumPickPop.getData2(),endNumPickPop.getData2(),0));
            }
        });
    }

    @Override
    protected void refreshData() {

    }
}
