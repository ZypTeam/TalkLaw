package com.chuxin.law.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jusfoun.baselibrary.Util.PhoneUtil;

import com.chuxin.law.R;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * @author wangcc
 * @date 2018/1/22
 * @describe 推荐规则 dialog
 */

public class RecommendationRulesDialog extends Dialog {
    private ImageView close;
    public RecommendationRulesDialog(@NonNull Context context) {
        this(context, R.style.my_dialog);
    }

    public RecommendationRulesDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
        initAction();
    }

    private void initView() {
        setContentView(R.layout.dialog_recommendation_rules);
        setCancelable(true);
        Window window = getWindow();
        window.addFlags(FLAG_TRANSLUCENT_STATUS);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setWindowAnimations(R.style.dialog_enter_anim_up_down);
        lp.width = PhoneUtil.getDisplayWidth(getContext());
        lp.height = PhoneUtil.getDisplayHeight(getContext());
        window.setAttributes(lp);

        close=findViewById(R.id.close);
    }

    private void initAction(){
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
