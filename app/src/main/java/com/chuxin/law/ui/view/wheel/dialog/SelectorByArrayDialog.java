package com.chuxin.law.ui.view.wheel.dialog;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chuxin.law.ui.view.wheel.WheelView;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.Util.PhoneUtil;
import com.chuxin.law.R;
import java.util.List;

/**
 * Created by lee on -2018/1/30.
 */

public class SelectorByArrayDialog extends Dialog {
    private Context mContext;

    private TextView btn_cancel, btn_ok, tv_title;
    private WheelView mWheelView;

    private String mCurrentItem = "";
    private int mCurrentSelectIndex = 0;

    public SelectorByArrayDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SelectorByArrayDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
    }

    private void initView(){
        setContentView(R.layout.wheel_dialog_select_by_array);
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setWindowAnimations(R.style.dialog_enter_anim_up_down);
        lp.width = (int) (PhoneUtil.getDisplayWidth(mContext));
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);


        btn_cancel = findViewById(R.id.btn_cancel);
        btn_ok = findViewById(R.id.btn_ok);
        tv_title = findViewById(R.id.btn_title);
        mWheelView = findViewById(R.id.wheel_week_wheel);
        mWheelView.setOnSelectListener(new WheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                LogUtil.e("select", "当前选中index："+index+",text:"+ text);
                mCurrentItem = text;
                mCurrentSelectIndex = index;
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onOkClick(mCurrentItem);
                }
                dismiss();
            }
        });
    }

    /**
     * 设置title
     * @param title
     */
    public void setTitle(String title){
        tv_title.setText(title);
    }

    /**
     * 设置数据以及默认项
     * @param list
     */
    public void setData(List<String> list){
        if(list != null){
            mWheelView.setWheelItemList(list);
            if(list.size() > 0){
                mCurrentItem = list.get(0);
                mCurrentSelectIndex = 0;
            }
        }
    }

    public interface OnWheelViewListener{
        void onOkClick(String content);
    }
    private OnWheelViewListener listener;
    public void setOnWheelViewListener(OnWheelViewListener listener){
        this.listener = listener;
    }

}
