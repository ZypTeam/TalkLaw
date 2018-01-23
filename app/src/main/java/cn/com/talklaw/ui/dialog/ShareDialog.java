package cn.com.talklaw.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.Util.PhoneUtil;
import com.jusfoun.baselibrary.Util.TouchUtil;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import cn.com.talklaw.R;
import cn.com.talklaw.ui.util.ShareUtils;

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

    private ShareUtils shareUtils;

    private Activity activity;

    public ShareDialog(@NonNull Activity context) {
        this(context, R.style.my_dialog);
    }

    public ShareDialog(@NonNull Activity context, int themeResId) {
        super(context, themeResId);
        activity=context;
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
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareUtils.shareWeb(SHARE_MEDIA.QQ,"http://www.baidu.com",R.mipmap.logo);
            }
        });

        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareUtils.shareWeb(SHARE_MEDIA.WEIXIN,"http://www.baidu.com",R.mipmap.logo);
            }
        });

        pengyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareUtils.shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE,"http://www.baidu.com",R.mipmap.logo);
            }
        });

        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareUtils.shareWeb(SHARE_MEDIA.SINA,"http://www.baidu.com",R.mipmap.logo);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        shareUtils=new ShareUtils(activity, new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(getContext(),"分享成功",Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                LogUtil.e("share",throwable.getMessage());
                Toast.makeText(getContext(),"分享失败",Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(getContext(),"分享取消",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

}
