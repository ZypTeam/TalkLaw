package com.chuxin.law.ui.view.wheel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.ui.view.wheel.WheelStyle;
import com.chuxin.law.ui.view.wheel.WheelView;
import com.jusfoun.baselibrary.Util.PhoneUtil;

import java.util.Calendar;

/**
 * Created by lee on -2018/1/31.
 */

public class SelectorDateDialog extends Dialog {
    private Context mContext;

    private WheelView yearWheel;
    private WheelView monthWheel;
    private WheelView dayWheel;

    private TextView tv_cancel, tv_ok, tv_title;

    private int selectYear;
    private int selectMonth;

    private OnClickListener onClickListener;

    public SelectorDateDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SelectorDateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
    }

    private void initView(){
        setContentView(R.layout.dialog_select_date);
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setWindowAnimations(R.style.dialog_enter_anim_up_down);
        lp.width = (int) (PhoneUtil.getDisplayWidth(mContext));
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);

        tv_cancel = findViewById(R.id.btn_cancel);
        tv_title = findViewById(R.id.btn_title);
        tv_ok = findViewById(R.id.btn_ok);
        yearWheel =findViewById(R.id.select_date_wheel_year_wheel);
        monthWheel = findViewById(R.id.select_date_month_wheel);
        dayWheel = findViewById(R.id.select_date_day_wheel);


        yearWheel.setWheelStyle(WheelStyle.STYLE_YEAR);
        yearWheel.setOnSelectListener(new WheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                selectYear = index + WheelStyle.minYear;
                dayWheel.setWheelItemList(WheelStyle.createDayString(selectYear, selectMonth));
            }
        });

        monthWheel.setWheelStyle(WheelStyle.STYLE_MONTH);
        monthWheel.setOnSelectListener(new WheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                selectMonth = index + 1;
                dayWheel.setWheelItemList(WheelStyle.createDayString(selectYear, selectMonth));
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = yearWheel.getCurrentItem() + WheelStyle.minYear;
                int month = monthWheel.getCurrentItem();
                int day = dayWheel.getCurrentItem() + 1;
                int daySize = dayWheel.getItemCount();
                if (day > daySize) {
                    day = day - daySize;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, day);
                long setTime = calendar.getTimeInMillis();

                if (onClickListener != null) {
                    if (!onClickListener.onSure(year, month, day, setTime)) {
                        dismiss();
                    }
                } else {
                    dismiss();
                }
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    if (!onClickListener.onCancel()) {
                       dismiss();
                    }
                } else {
                    dismiss();
                }
            }
        });
    }
    /**
     * 显示选择日期对话框
     *
     * @param year  默认显示的年
     * @param month 默认月
     * @param day   默认日
     */
    public void show(int year, int month, int day) {
        dayWheel.setWheelItemList(WheelStyle.createDayString(year - WheelStyle.minYear, month + 1));
        yearWheel.setCurrentItem(year - WheelStyle.minYear);
        monthWheel.setCurrentItem(month);
        dayWheel.setCurrentItem(day - 1);
        this.show();
    }

    /**
     * 选择日期对话框回调
     *
     * @param listener
     */
    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    /**
     * 选择日期对话框回调接口，调用者实现
     *
     * @author huangzj
     */
    public interface OnClickListener {
        boolean onSure(int year, int month, int day, long time);

        boolean onCancel();
    }
}
