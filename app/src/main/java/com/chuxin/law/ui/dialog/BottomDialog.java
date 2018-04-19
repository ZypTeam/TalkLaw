package com.chuxin.law.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.chuxin.law.R;
import com.jusfoun.baselibrary.Util.PhoneUtil;

/**
 * @author wangcc
 * @date 2018/4/18
 * @describe
 */
public class BottomDialog extends Dialog {
    private TextView top,bottom,cencel;

    public BottomDialog(Context context) {
        super(context);
        initView(context);
    }

    public BottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    private void initView(Context context) {
        setContentView(R.layout.dialog_bottom);
        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.setWindowAnimations(R.style.bottom_anim);
        lp.width = (int) (PhoneUtil.getDisplayWidth(context));
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);

      /*  Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.bottom_anim);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        dialogWindow.setAttributes(lp);*/

        top= (TextView) findViewById(R.id.top_txt);
        bottom= (TextView) findViewById(R.id.bottom_txt);
        cencel= (TextView) findViewById(R.id.cencel);

        cencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomListener!=null){
                    bottomListener.onClick(view);
                }
            }
        });

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topListener!=null){
                    topListener.onClick(view);
                }
            }
        });
    }

    private View.OnClickListener topListener,bottomListener;

    public void setTopListener(View.OnClickListener topListener) {
        this.topListener = topListener;
    }

    public void setBottomListener(View.OnClickListener bottomListener) {
        this.bottomListener = bottomListener;
    }

    public void setTxt(String topTxt,String bottomTxt){
        top.setText(topTxt);
        bottom.setText(bottomTxt);
    }
}
