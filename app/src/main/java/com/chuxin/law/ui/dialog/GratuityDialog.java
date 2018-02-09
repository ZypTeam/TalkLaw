package com.chuxin.law.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jusfoun.baselibrary.Util.PhoneUtil;

import com.chuxin.law.R;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * @author wangcc
 * @date 2018/1/26
 * @describe 赏金dialog
 */

public class GratuityDialog extends Dialog {

    protected TextView content;
    protected TextView cancel;
    protected TextView sure;
    private View line;

    public GratuityDialog(@NonNull Context context) {
        this(context,R.style.my_dialog);
    }

    public GratuityDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
        initAction();
    }

    private void initView() {
        setContentView(R.layout.dialog_gratuity);
        setCancelable(true);
        Window window = getWindow();
        window.addFlags(FLAG_TRANSLUCENT_STATUS);
        WindowManager.LayoutParams lp = window.getAttributes();
//        window.setWindowAnimations(R.style.dialog_enter_anim_up_down);
        lp.width = (int) (PhoneUtil.getDisplayWidth(getContext())*0.8f);
        lp.gravity= Gravity.CENTER;
        window.setAttributes(lp);
        content = (TextView) findViewById(R.id.content);
        cancel = (TextView) findViewById(R.id.cancel);
        sure = (TextView) findViewById(R.id.sure);
        line =  findViewById(R.id.line);

    }

    private void initAction() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setOkListener(View.OnClickListener listener){
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setOneBtn(boolean isOne){
        if (isOne){
            line.setVisibility(View.VISIBLE);
            sure.setVisibility(View.VISIBLE);
        }else {
            line.setVisibility(View.GONE);
            sure.setVisibility(View.GONE);
        }
    }

    public void setContent(String txt){
        content.setText(txt);
    }
}
