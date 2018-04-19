package com.chuxin.law.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.model.ShareModel;
import com.google.zxing.utils.WiterQRUtil;
import com.jusfoun.baselibrary.Util.IOUtil;
import com.jusfoun.baselibrary.Util.PhoneUtil;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.permissiongen.PermissionFail;
import com.jusfoun.baselibrary.permissiongen.PermissionGen;
import com.jusfoun.baselibrary.permissiongen.PermissionSuccess;
import com.umeng.socialize.UMShareAPI;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.ui.dialog.RecommendationRulesDialog;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.dialog.ShareDialog;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe 推荐有礼
 */

public class RecommendCourtesyActivity extends BaseTalkLawActivity {
    public static final String URL="url";
    protected BackTitleView titleView;
    protected View bg;
    protected ImageView qrcode;

    private ShareDialog shareDialog;
    private RecommendationRulesDialog rulesDialog;
    private TextView rules;

    private Bitmap qrcodeBitmap;
    private String url;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_recommending_courtesy;
    }

    @Override
    public void initDatas() {

        if (!checkPer()){
            applyPer();
        }

        shareDialog = new ShareDialog(this);
        rulesDialog = new RecommendationRulesDialog(mContext);
        if (getIntent().getExtras()!=null){
            url=getIntent().getExtras().getString(URL);
        }

        if (StringUtil.isEmpty(url)){
            url="http://www.baidu.com";
        }
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        bg = (View) findViewById(R.id.bg);
        qrcode = (ImageView) findViewById(R.id.qrcode);
        rules = (TextView) findViewById(R.id.rules);

        qrcode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                saveQrcode();
                return false;
            }
        });

    }

    @PermissionFail(requestCode = 100)
    private void onPerFail(){
        showToast("无存储权限");
    }

    @PermissionSuccess(requestCode = 100)
    private void onPerSuc(){
        saveQrcode();
    }

    private void saveQrcode() {

        if (checkPer()) {
            while (qrcodeBitmap != null && !qrcodeBitmap.isRecycled()) {
                qrcodeBitmap = WiterQRUtil.witerQRCenterLogo(url, BitmapFactory.decodeResource(getResources(), R.mipmap.logo)
                        , PhoneUtil.dip2px(mContext, 271), Color.BLACK, Color.WHITE);
                break;
            }
            IOUtil.saveImageToGallery(mContext, qrcodeBitmap, "qrcode");
        }else {
            applyPer();
        }
    }

    @Override
    public void initAction() {

        titleView.setTitle("推荐有礼");
        titleView.setTitleColor(Color.WHITE);
        titleView.setRightIcon(R.mipmap.icon_share_white);
        titleView.setBackIcon(R.mipmap.icon_back_white);
        titleView.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareModel shareModel=new ShareModel();
                shareModel.setShareUrl(url);
                shareModel.setShareTitle("快来跟我一块使用看法说法吧");
                shareModel.setShareContent("");
                shareModel.setBitmap(qrcodeBitmap);
                shareDialog.setShareModel(shareModel);
                shareDialog.show();
            }
        });

        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rulesDialog.show();
            }
        });
        qrcodeBitmap = WiterQRUtil.witerQRCenterLogo(url, BitmapFactory.decodeResource(getResources(), R.mipmap.logo)
                , PhoneUtil.dip2px(mContext, 271), Color.BLACK, Color.WHITE);
        qrcode.setImageBitmap(qrcodeBitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (qrcodeBitmap!=null&&!qrcodeBitmap.isRecycled()){
            qrcodeBitmap.recycle();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (shareDialog!=null){
            shareDialog.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean checkPer(){
        return PermissionGen.checkPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    private void applyPer(){
        PermissionGen.with(this).addRequestCode(100)
                .permissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE})
                .request();
    }
}
