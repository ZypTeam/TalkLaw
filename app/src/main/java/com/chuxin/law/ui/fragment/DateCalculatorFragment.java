package com.chuxin.law.ui.fragment;

import android.os.Build;
import android.util.TimeUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.ui.widget.NumberPickerPopupwinow;
import com.chuxin.law.util.DateUtil;
import com.jusfoun.baselibrary.Util.PhoneUtil;

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
    protected LinearLayout layoutRoot;
    protected TextView textResultDay;
    protected TextView textResultMonth;
    protected TextView textResultYear;
    protected TextView textResultWorkDay;
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
        layoutRoot = (LinearLayout) rootView.findViewById(R.id.layout_root);
        textResultDay = (TextView) rootView.findViewById(R.id.text_result_day);
        textResultMonth = (TextView) rootView.findViewById(R.id.text_result_month);
        textResultYear = (TextView) rootView.findViewById(R.id.text_result_year);
        textResultWorkDay = (TextView) rootView.findViewById(R.id.text_result_work_day);

    }

    @Override
    public void initAction() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textStart.setText("请选择日期");
                textEnd.setText("请选择日期");
                textResultDay.setText("");
                textResultMonth.setText("");
                textResultYear.setText("");
                textResultWorkDay.setText("");
            }
        });

        btnJisuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textStart.getText().equals("请选择日期")) {
                    showToast("请选择开始日期");
                    return;
                }
                if (textEnd.getText().equals("请选择日期")) {
                    showToast("请选择开始日期");
                    return;
                }
//                if(isQian) {
//                    textResult.setText(DateUtil.getDateBefore(numPickPop.getData2(), Integer.parseInt(editInputDay.getText().toString())));
//                }else {
//                    textResult.setText(DateUtil.getDateBefore(numPickPop.getData2(),-Integer.parseInt(editInputDay.getText().toString())));
//                }
//

                textResultDay.setText(DateUtil.compareDate(startNumPickPop.getData2(), endNumPickPop.getData2(), 0) + "");
                textResultMonth.setText(DateUtil.compareDate(startNumPickPop.getData2(), endNumPickPop.getData2(), 1) + "");
                textResultYear.setText(DateUtil.compareDate(startNumPickPop.getData2(), endNumPickPop.getData2(), 2) + "");
                textResultWorkDay.setText(DateUtil.getDutyDays(startNumPickPop.getData2(),endNumPickPop.getData2())+"");
            }
        });
        textStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("R7Plus".equals(Build.MODEL)) {
                    startNumPickPop.showAtLocation(layoutRoot, Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, PhoneUtil.dip2px(mContext, 36));
                } else {
                    startNumPickPop.showAtLocation(layoutRoot, Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });

        textEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("R7Plus".equals(Build.MODEL)) {
                    endNumPickPop.showAtLocation(layoutRoot, Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, PhoneUtil.dip2px(mContext, 36));
                } else {
                    endNumPickPop.showAtLocation(layoutRoot, Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });
    }

    @Override
    protected void refreshData() {

    }
}
