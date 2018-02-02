package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.UserInfoModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ui.view.wheel.dialog.SelectorDateDialog;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;

import java.util.HashMap;

import rx.functions.Action1;

/**
 * @author wangcc
 * @date 2018/1/3
 * @describe
 */

public class MyInfoActivity extends BaseTalkLawActivity implements View.OnKeyListener {
    private final int NAME_REQUEST_CODE = 101;
    private final int NICKNAME_REQUEST_CODE = 102;
    private final int PHONE_REQUEST_CODE = 103;
    private final int EMAIL_REQUEST_CODE = 104;

    protected BackTitleView titleView;
    protected TextView head;
    protected ImageView iconHead;
    protected TextView userName;
    protected TextView userNickname;
    protected TextView userSex;
    protected TextView address;
    protected TextView userBirthday;
    protected TextView userNumber;
    protected TextView userMail;
    protected TextView birthday;

    private HashMap<String,String> editUserMap=new HashMap<>();
    private UserModel userModel;

    private SelectorDateDialog dialog;

    private int mYear = 2018,mMonth = 1,mDay = 29;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_info;
    }

    @Override
    public void initDatas() {
        dialog = new SelectorDateDialog(mContext, R.style.my_dialog);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        head = (TextView) findViewById(R.id.head);
        iconHead = (ImageView) findViewById(R.id.icon_head);
        userName = (TextView) findViewById(R.id.user_name);
        userNickname = (TextView) findViewById(R.id.user_nickname);
        userSex = (TextView) findViewById(R.id.user_sex);
        address = (TextView) findViewById(R.id.address);
        userBirthday = (TextView) findViewById(R.id.user_birthday);
        userNumber = (TextView) findViewById(R.id.user_number);
        userMail = (TextView) findViewById(R.id.user_mail);
        birthday =  findViewById(R.id.birthday);

    }

    @Override
    public void initAction() {
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null,SelectAreaActivity.class);
            }
        });
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(EditUserInfoActivity.UPDATE_TYPE, 0);
                bundle.putString(EditUserInfoActivity.UPDATE_VALUE, userModel.getName());
                goActivityForResult(bundle, EditUserInfoActivity.class, NAME_REQUEST_CODE);
            }
        });

        userNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(EditUserInfoActivity.UPDATE_TYPE, 1);
                bundle.putString(EditUserInfoActivity.UPDATE_VALUE, userModel.getHx_username());
                goActivityForResult(bundle, EditUserInfoActivity.class, NICKNAME_REQUEST_CODE);
            }
        });

        userNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(EditUserInfoActivity.UPDATE_TYPE, 2);
                bundle.putString(EditUserInfoActivity.UPDATE_VALUE, userModel.getPhone());
                goActivityForResult(bundle, EditUserInfoActivity.class, PHONE_REQUEST_CODE);
            }
        });

        userMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(EditUserInfoActivity.UPDATE_TYPE, 3);
                bundle.putString(EditUserInfoActivity.UPDATE_VALUE, userModel.getEmail());
                goActivityForResult(bundle, EditUserInfoActivity.class, EMAIL_REQUEST_CODE);
            }
        });

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(mYear, mMonth - 1, mDay);
            }
        });

        dialog.setOnClickListener(new SelectorDateDialog.OnClickListener() {
            @Override
            public boolean onSure(int year, int month, int day, long time) {
                mYear = year;
                mMonth = month ;
                mDay = day;

                userBirthday.setText(mYear + "-" + (mMonth + 1)+ "-" +mDay);
                return false;
            }

            @Override
            public boolean onCancel() {
                return false;
            }
        });

        userName.setOnKeyListener(this);
        userNickname.setOnKeyListener(this);
        userSex.setOnKeyListener(this);
        userNumber.setOnKeyListener(this);
        userMail.setOnKeyListener(this);
        userBirthday.setOnKeyListener(this);

        getUserInfo();
    }



    private void getUserInfo(){
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).getUserInfo()
                , new Action1<UserInfoModel>() {
                    @Override
                    public void call(UserInfoModel model) {
                        hideLoadDialog();
                        if (model!=null&&model.getCode()== CommonConstant.NET_SUC_CODE&&model.getData()!=null){
                            TalkLawApplication.saveUserInfo(model.getData());
                        }
                        updateUserInfo();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        updateUserInfo();
                    }
                });
    }

    private void updateUserInfo(){
        userModel= TalkLawApplication.getUserInfo();
        if (userModel==null){
            goActivity(null,LoginActivity.class);
            return;
        }
        Glide.with(mContext)
                .load("http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg")
                .placeholder(R.mipmap.icon_head_def_cir)
                .error(R.mipmap.icon_head_def_cir)
                .transform(new CenterCrop(mContext),new GlideCircleTransform(mContext))
                .crossFade()
                .into(iconHead);

        userName.setText(userModel.getName());
        userNickname.setText(userModel.getHx_username());
        if (userModel.getSex()==1){
            userSex.setText("男");
        }else {
            userSex.setText("女");
        }
        userBirthday.setText(userModel.getBirthday());
        userMail.setText(userModel.getEmail());
        userNumber.setText(userModel.getPhone());
    }

    private void editUserInfo(){
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).editUserInfo(editUserMap)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel model) {
                        hideLoadDialog();
                        if (model.getCode()== CommonConstant.NET_SUC_CODE){
                            TalkLawApplication.saveUserInfo(userModel);
                        }
                        showToast(model.getMsg());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showToast("修改失败");
                    }
                });
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN&&keyCode==KeyEvent.KEYCODE_ENTER){
            if (userModel==null){
                goActivity(null,LoginActivity.class);
                return false;
            }
            switch (v.getId()){
                case R.id.user_name:
                    if (!StringUtil.isEmpty(userName.getText().toString())) {
                        editUserMap.clear();
                        editUserMap.put("name",userName.getText().toString());
                        userModel.setName(userName.getText().toString());
                        editUserInfo();
                    }else {
                        showToast("不能为空");
                    }
                    break;
                case R.id.user_nickname:
                    if (!StringUtil.isEmpty(userNickname.getText().toString())) {
                        editUserMap.clear();
                        editUserMap.put("intro",userNickname.getText().toString());
                        userModel.setHx_username(userNickname.getText().toString());
                        editUserInfo();
                    }else {
                        showToast("不能为空");
                    }
                    break;
                case R.id.user_number:
                    if (!StringUtil.isEmpty(userNumber.getText().toString())) {
                        editUserMap.clear();
                        editUserMap.put("phone",userNumber.getText().toString());
                        userModel.setName(userNumber.getText().toString());
                        editUserInfo();
                    }else {
                        showToast("不能为空");
                    }
                    break;
                case R.id.user_birthday:
                    if (!StringUtil.isEmpty(userBirthday.getText().toString())) {
                        editUserMap.clear();
                        editUserMap.put("birthday",userBirthday.getText().toString());
                        userModel.setName(userBirthday.getText().toString());
                        editUserInfo();
                    }else {
                        showToast("不能为空");
                    }
                    break;
                case R.id.user_mail:
                    if (!StringUtil.isEmpty(userMail.getText().toString())) {
                        editUserMap.clear();
                        editUserMap.put("email",userMail.getText().toString());
                        userModel.setName(userMail.getText().toString());
                        editUserInfo();
                    }else {
                        showToast("不能为空");
                    }
                    break;
                case R.id.user_sex:
                    if (!StringUtil.isEmpty(userName.getText().toString())
                            &&(!StringUtil.equals(userName.getText().toString(),"男")
                    ||!StringUtil.equals(userName.getText().toString(),"女"))) {
                        editUserMap.clear();
                        editUserMap.put("sex",userSex.getText().toString());
                        if(StringUtil.equals(userName.getText().toString(),"女")){
                            userModel.setSex(2);
                            editUserMap.put("sex","2");
                        }else {
                            userModel.setSex(1);
                            editUserMap.put("sex","1");
                        }
                        editUserInfo();
                    }else {
                        showToast("请输入男或女");
                    }
                    break;
            }
            return true;
        }else if (event.getAction()==KeyEvent.ACTION_UP&&keyCode==KeyEvent.KEYCODE_ENTER){
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            return;
        }

        if(requestCode == NAME_REQUEST_CODE || requestCode == NICKNAME_REQUEST_CODE
                || requestCode == PHONE_REQUEST_CODE || requestCode == EMAIL_REQUEST_CODE){
            userModel = TalkLawApplication.getUserInfo();
            updateUserInfo();
        }
    }
}
