package com.chuxin.law.ui.fragment;

import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.ui.activity.MyInfoActivity;
import com.chuxin.law.ui.widget.NumberPickerPopupwinow;
import com.chuxin.law.util.DateUtil;
import com.jusfoun.baselibrary.Util.PhoneUtil;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1718:20
 * @Email zyp@jusfoun.com
 * @Description ${日期计算器}
 */
public class DayCalculatorFragment extends BaseTalkLawFragment {

    protected LinearLayout layoutSelectDate;
    protected EditText editInputDay;
    protected TextView textQian;
    protected TextView textHou;
    protected TextView textFou;
    protected TextView textShi;
    protected Button btnReset;
    protected Button btnJisuan;
    protected TextView textResult;
    protected TextView textSelectDate;
    protected LinearLayout layoutRoot;
    private NumberPickerPopupwinow numPickPop;
    private LinearLayout houQianLayout;

    private boolean isQian = false;

    public static DayCalculatorFragment getInstance() {
        return new DayCalculatorFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_day_calcuator;
    }

    @Override
    public void initDatas() {
        numPickPop = new NumberPickerPopupwinow(mContext,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        numPickPop.dismiss();
                        textSelectDate.setText(numPickPop.getData2());
                    }
                });
    }

    @Override
    public void initView(View rootView) {
        layoutSelectDate = (LinearLayout) rootView.findViewById(R.id.layout_select_date);
        editInputDay = (EditText) rootView.findViewById(R.id.edit_input_day);
        textQian = (TextView) rootView.findViewById(R.id.text_qian);
        textHou = (TextView) rootView.findViewById(R.id.text_hou);
        textFou = (TextView) rootView.findViewById(R.id.text_fou);
        textShi = (TextView) rootView.findViewById(R.id.text_shi);
        btnReset = (Button) rootView.findViewById(R.id.btn_reset);
        btnJisuan = (Button) rootView.findViewById(R.id.btn_jisuan);
        textResult = (TextView) rootView.findViewById(R.id.text_result);
        textSelectDate = (TextView) rootView.findViewById(R.id.text_select_date);
        layoutRoot = (LinearLayout) rootView.findViewById(R.id.layout_root);
        houQianLayout = (LinearLayout)rootView.findViewById(R.id.layout_qian_hou);

    }

    @Override
    public void initAction() {
        layoutSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("R7Plus".equals(Build.MODEL)) {
                    numPickPop.showAtLocation(layoutRoot, Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, PhoneUtil.dip2px(mContext, 36));
                } else {
                    numPickPop.showAtLocation(layoutRoot, Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });

        btnJisuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textSelectDate.getText().equals("请选择日期")){
                    showToast("请选择开始日期");
                    return;
                }
                if(TextUtils.isEmpty(editInputDay.getText())){
                    showToast("请输入间隔天数");
                    return;
                }
                if(isQian) {
                    textResult.setText(DateUtil.getDateBefore(numPickPop.getData2(), Integer.parseInt(editInputDay.getText().toString())));
                }else {
                    textResult.setText(DateUtil.getDateBefore(numPickPop.getData2(),-Integer.parseInt(editInputDay.getText().toString())));
                }
            }
        });

        textQian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isQian = true;
                houQianLayout.setBackgroundResource(R.drawable.bg_yes_no);
                textQian.setTextColor(mContext.getResources().getColor(R.color.write));
                textHou.setTextColor(0xffcb1e28);

            }
        });
        textHou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isQian = false;
                houQianLayout.setBackgroundResource(R.drawable.bg_no_yes);
                textHou.setTextColor(mContext.getResources().getColor(R.color.write));
                textQian.setTextColor(0xffcb1e28);
            }
        });
    }

    @Override
    protected void refreshData() {

    }
}
