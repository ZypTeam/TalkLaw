package com.chuxin.law.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.AuthLawyerModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ui.util.ImageLoderUtil;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.widget.NumberPickerPopupwinow;
import com.jusfoun.baselibrary.Util.PhoneUtil;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.permissiongen.PermissionFail;
import com.jusfoun.baselibrary.permissiongen.PermissionGen;
import com.jusfoun.baselibrary.permissiongen.PermissionSuccess;

import java.util.HashMap;
import java.util.List;

import cn.com.talklaw.xh.runtimepermissions.PermissionsManager;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NAME_REQUEST_CODE;
import static com.chuxin.law.common.CommonConstant.REQUEST_IMAGE_HEAD;
import static com.chuxin.law.common.CommonConstant.REQUEST_IMAGE_PAGE;
import static com.chuxin.law.common.CommonConstant.REQUEST_IMAGE_PHOTO;
import static com.chuxin.law.common.CommonConstant.REQUEST_LAWYER_AUTH_SUC;

/**
 * @author wangcc
 * @date 2018/2/2
 * @describe
 */

public class LawyerAuthActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected TextView uploadHead;
    protected ImageView iconHead;
    protected TextView name;
    protected TextView time;
    protected TextView lawyer;
    protected ImageView yingye;
    protected ImageView nianjian;
    protected TextView next;
    private NumberPickerPopupwinow numPickPop;
    private AuthLawyerModel model = new AuthLawyerModel();
    private UserModel userModel;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_lawyer_auth;
    }

    @Override
    public void initDatas() {
        userModel = TalkLawApplication.getUserInfo();
        if (userModel == null) {
            onBackPressed();
        }
        PermissionGen.with(this).addRequestCode(100)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @PermissionFail(requestCode = 100)
    private void perFail() {
        showToast("无权限");
    }

    @PermissionSuccess(requestCode = 100)
    private void perSuc() {

    }


    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.title_view);
        uploadHead = (TextView) findViewById(R.id.upload_head);
        iconHead = (ImageView) findViewById(R.id.icon_head);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        lawyer = (TextView) findViewById(R.id.lawyer);
        yingye = (ImageView) findViewById(R.id.yingye);
        nianjian = (ImageView) findViewById(R.id.nianjian);
        next = (TextView) findViewById(R.id.next);

    }

    @Override
    public void initAction() {
        titleView.setTitle("律师认证");
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("R7Plus".equals(Build.MODEL)) {
                    numPickPop.showAtLocation(time.getRootView(), Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, PhoneUtil.dip2px(LawyerAuthActivity.this, 36));
                } else {
                    numPickPop.showAtLocation(time.getRootView(), Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });
        lawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(EditUserInfoActivity.UPDATE_TYPE, 4);
                bundle.putString(EditUserInfoActivity.UPDATE_VALUE, "");
                goActivityForResult(bundle, EditUserInfoActivity.class, NAME_REQUEST_CODE);
            }
        });
        numPickPop = new NumberPickerPopupwinow(LawyerAuthActivity.this,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        numPickPop.dismiss();
                        time.setText(numPickPop.getData2());
                        model.setStarttime(numPickPop.getData2());
                        if (!StringUtil.isEmpty(time.getText().toString())) {

                        } else {
                            showToast("不能为空");
                        }
                    }
                });

        iconHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelector.create(mContext)
                        .showCamera(true)
                        .single()
                        .start(LawyerAuthActivity.this, REQUEST_IMAGE_HEAD);
            }
        });

        yingye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelector.create(mContext)
                        .showCamera(true)
                        .single()
                        .start(LawyerAuthActivity.this, REQUEST_IMAGE_PHOTO);
            }
        });

        nianjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelector.create(mContext)
                        .showCamera(true)
                        .single()
                        .start(LawyerAuthActivity.this, REQUEST_IMAGE_PAGE);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });
        ImageLoderUtil.loadRoundSmailImage(mContext, iconHead, userModel.getHeadimg(),R.mipmap.icon_head_def_round);
        model.setHeadimg(TalkLawApplication.getUserInfo().getHeadimg());
    }

    private void goNext() {
        if (StringUtil.isEmpty(model.getStarttime())){
            showToast("开始时间不能为空");
            return;
        }
        model.setLaw_firm(lawyer.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        goActivityForResult(bundle, SubmitAuthActivity.class,REQUEST_LAWYER_AUTH_SUC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_HEAD) {
            if (resultCode == RESULT_OK) {
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (path != null && path.size() > 0) {
                    model.setHeadimg(path.get(0));
                    ImageLoderUtil.loadRoundSmailImage(mContext, iconHead, path.get(0));
                }
            }
        } else if (requestCode == REQUEST_IMAGE_PAGE) {
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            if (path != null && path.size() > 0) {
                model.setCertificate_year(path.get(0));
                ImageLoderUtil.loadRoundSmailImage(mContext, nianjian, path.get(0));
            }
        } else if (requestCode == REQUEST_IMAGE_PHOTO) {
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            if (path != null && path.size() > 0) {
                model.setCertificate(path.get(0));
                ImageLoderUtil.loadRoundSmailImage(mContext, yingye, path.get(0));
            }
        }else if (requestCode==NAME_REQUEST_CODE){
            if (data!=null){
                if (!StringUtil.isEmpty(data.getStringExtra("name"))){
                    lawyer.setText(data.getStringExtra("name"));
                }
            }
        }else if (requestCode==REQUEST_LAWYER_AUTH_SUC){
            onBackPressed();
        }
    }


}
