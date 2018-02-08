package com.chuxin.law.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.chuxin.law.R;
import com.jusfoun.baselibrary.Util.PhoneUtil;

/**
 * @author zhaoyapeng
 * @version create time:18/2/900:09
 * @Email zyp@jusfoun.com
 * @Description ${提现说明 dialog}
 */
public class PresentInstructionsDialog extends Dialog {
    protected View rootView;
    protected TextView textTitle;
    protected TextView textDes;
    protected TextView textAfirm;
    private Context mContext;

    public PresentInstructionsDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initData();
        initViews();
        initAction();
    }

    public PresentInstructionsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        initData();
        initViews();
        initAction();
    }

    protected PresentInstructionsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initData();
        initViews();
        initAction();
    }

    private void initData() {
        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(mContext) * 0.9);
        lp.height = (int) (PhoneUtil.getDisplayHeight(mContext) * 0.8);
        window.setGravity(Gravity.CENTER);
    }

    private void initViews() {
        setContentView(R.layout.dialog_present_instructions);
        textTitle = (TextView) findViewById(R.id.text_title);
        textDes = (TextView) findViewById(R.id.text_des);
        textAfirm = (TextView) findViewById(R.id.text_afirm);
    }

    private void initAction() {
        textAfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setListener(View.OnClickListener listener){
        textAfirm.setOnClickListener(listener);
    }

    public void setTextTitle(String title){
        textTitle.setText(title);
    }

    public void setContent(String content){
        textDes.setText(content);
    }
}
