package cn.com.talklaw.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jusfoun.baselibrary.Util.PhoneUtil;

import cn.com.talklaw.R;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * @author wangcc
 * @date 2018/1/22
 * @describe 分享 dialog
 */

public class ShareDialog extends Dialog {
    protected TextView cancel;
    protected ImageView pengyou;
    protected ImageView wechat;
    protected ImageView sina;
    protected ImageView qq;

    public ShareDialog(@NonNull Context context) {
        this(context, R.style.my_dialog);
    }

    public ShareDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
        initAction();
    }

    private void initView() {
        setContentView(R.layout.dialog_share);
        setCancelable(true);
        Window window = getWindow();
        window.addFlags(FLAG_TRANSLUCENT_STATUS);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setWindowAnimations(R.style.dialog_enter_anim_up_down);
        lp.width = PhoneUtil.getDisplayWidth(getContext());
        lp.height = PhoneUtil.getDisplayHeight(getContext());
        window.setAttributes(lp);
        cancel = (TextView) findViewById(R.id.cancel);
        pengyou = (ImageView) findViewById(R.id.pengyou);
        wechat = (ImageView) findViewById(R.id.wechat);
        sina = (ImageView) findViewById(R.id.sina);
        qq = (ImageView) findViewById(R.id.qq);
    }

    private void initAction(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
