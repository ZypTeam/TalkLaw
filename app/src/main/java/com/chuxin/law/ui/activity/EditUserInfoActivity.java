package com.chuxin.law.ui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

/**
 * Created by lee on -2018/1/31.
 */

public class EditUserInfoActivity extends BaseTalkLawActivity{

    public static final String UPDATE_TYPE = "update_info_type";
    public static final String UPDATE_VALUE = "update_info_value";
    public static final int TYPE_NAME = 0;

    private BackTitleView titleView;
    private EditText ed_info;
    private LinearLayout lin_num;


    private int updateInfoType = 0;
    private String updateInfoValue = "";

    @Override
    public int getLayoutResId() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    public void initDatas() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            updateInfoType = bundle.getInt(UPDATE_TYPE, 0);
            updateInfoValue = bundle.getString(UPDATE_VALUE, "");
        }
    }

    @Override
    public void initView() {
        titleView = findViewById(R.id.titleView);
        ed_info = findViewById(R.id.edit_info);
        lin_num = findViewById(R.id.lin_num);
    }

    @Override
    public void initAction() {
        if(updateInfoType == 0){
            titleView.setTitle("修改姓名");
            ed_info.setHint("请输入姓名");

        }else if(updateInfoType == 1){
            titleView.setTitle("修改昵称");
            ed_info.setHint("请输入昵称");
        }else if(updateInfoType == 2){
            titleView.setTitle("修改手机号");
            ed_info.setHint("请输入手机号");
            ed_info.setInputType(InputType.TYPE_CLASS_PHONE);
        }else if(updateInfoType == 3){
            titleView.setTitle("修改邮箱");
            ed_info.setHint("请输入邮箱账号");
            ed_info.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }

        titleView.setRightText("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitInfo();
            }
        });
    }

    private void submitInfo(){
        final String info = ed_info.getText().toString();
        String toastText = "";
        HashMap<String, String> map = new HashMap<>();
        switch (updateInfoType){
            case 0:
                toastText = "请输入姓名";
                map.put("name", info);
                break;
            case 1:
                toastText ="请输入昵称";
                map.put("hx_username", info);
                break;
            case 2:
                toastText = "请输入手机号";
                map.put("phone", info);
                break;
            case 3:
                toastText = "请输入邮箱";
                map.put("email", info);
                break;
        }
        if(TextUtils.isEmpty(info)){
            map.clear();
            showToast(toastText);
            return;
        }

        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).changeUserInfo(map), new Action1<NoDataModel>() {
            @Override
            public void call(NoDataModel noDataModel) {
                hideLoadDialog();
                if(noDataModel.getCode() == CommonConstant.NET_SUC_CODE){
                    UserModel userModel = TalkLawApplication.getUserInfo();
                    switch (updateInfoType){
                        case 0:
                            userModel.setName(info);
                            showToast("修改姓名成功");
                            break;
                        case 1:
                            userModel.setHx_username(info);
                            showToast("修改昵称成功");
                            break;
                        case 2:
                            userModel.setPhone(info);
                            showToast("修改手机号成功");
                            break;
                        case 3:
                            userModel.setEmail(info);
                            showToast("修改邮箱成功");
                            break;
                    }

                    TalkLawApplication.saveUserInfo(userModel);

                    setResult(RESULT_OK);
                    finish();
                }else{
                    showToast(noDataModel.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                hideLoadDialog();
                showToast(throwable.getMessage());
            }
        });
    }
}
