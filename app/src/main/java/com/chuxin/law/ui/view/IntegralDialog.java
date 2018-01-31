package com.chuxin.law.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.chuxin.law.R;
import com.jusfoun.baselibrary.Util.PhoneUtil;


/**
 * Created by lsq on 2016/8/10.
 */
public class IntegralDialog extends Dialog {
    private Context mContext;
    private Button agree, hope;
    private TextView message;

    public IntegralDialog(Context context) {
        super(context, R.style.tool_dialog);
        mContext = context;
        initViews();
        initAation();
    }

    private void initViews() {
        setContentView(R.layout.dialog_integral);
        hope = (Button) findViewById(R.id.positiveButton);
        agree = (Button) findViewById(R.id.negativeButton);
        message = (TextView) findViewById(R.id.message);

        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(mContext) * 0.9);
        lp.height = (int) (PhoneUtil.getDisplayHeight(mContext) * 0.8);
        window.setGravity(Gravity.CENTER);
    }

    private void initAation() {

        agree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setOnClick(View.OnClickListener listener){
        hope.setOnClickListener(listener);
    }

    public void setTtile(String count){
        message.setText("兑换"+count+"积分");
    }
}
