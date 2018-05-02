package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.base.BaseTakeActivity;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.common.UserInfoDelegate;
import com.chuxin.law.model.UserInfoModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ui.dialog.BottomDialog;
import com.chuxin.law.util.TakeHelper;
import com.chuxin.law.util.base64.Base64Util;
import com.chuxin.law.util.base64.EncodeCallBack;
import com.chuxin.law.ui.view.wheel.dialog.SelectorDateDialog;
import com.chuxin.law.ui.widget.BackTitleView;
import com.chuxin.law.ui.widget.NumberPickerPopupwinow;
import com.chuxin.law.ui.widget.SexNumberPickerPopup;
import com.jph.takephoto.model.TResult;
import com.jusfoun.baselibrary.Util.PhoneUtil;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;

import java.util.HashMap;

import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.EDIT_HEADER;

/**
 * @author wangcc
 * @date 2018/1/3
 * @describe
 */

public class MyInfoActivity extends BaseTakeActivity {
    //    implements View.OnKeyListener
    private final int NAME_REQUEST_CODE = 101;
    private final int NICKNAME_REQUEST_CODE = 102;
    private final int PHONE_REQUEST_CODE = 103;
    private final int EMAIL_REQUEST_CODE = 104;
    private final int ADDRESS_REQUEST_CODE = 105;
    private final int REQUEST_IMAGE = 105;

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
    protected TextView name;
    protected ImageView ivNameArrow;
    protected View lineName;
    protected TextView nickname;
    protected ImageView ivNicknameArrow;
    protected View lineNickname;
    protected TextView sex;
    protected ImageView ivSexArrow;
    protected View lineSex;
    protected TextView tvAddressValue;
    protected View lineAddress;
    protected TextView number;
    protected View lineNumber;
    protected TextView mail, introText, userIntroText;

    private HashMap<String, String> editUserMap = new HashMap<>();
    private UserModel userModel;

    private BottomDialog bottomDialog;

    private SelectorDateDialog dialog;

    private int mYear = 2018, mMonth = 1, mDay = 29;
    private SexNumberPickerPopup sexNumPicPop;
    private ConstraintLayout rootLayout;
    private NumberPickerPopupwinow numPickPop;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_info;
    }

    @Override
    public void initDatas() {
        bottomDialog = new BottomDialog(mContext, R.style.my_dialog);
        bottomDialog.setTxt("拍照", "从相册选取");
        bottomDialog.setTopListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakeHelper.pickByTake(getTakePhoto());
                bottomDialog.dismiss();
            }
        });

        bottomDialog.setBottomListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakeHelper.pickBySelect(takePhoto);
                bottomDialog.dismiss();
            }
        });

        dialog = new SelectorDateDialog(mContext, R.style.my_dialog);
        sexNumPicPop = new SexNumberPickerPopup(MyInfoActivity.this,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editUserMap.clear();
                        editUserMap.put("sex", userSex.getText().toString());
                        if (StringUtil.equals(userName.getText().toString(), "女")) {
                            userModel.setSex(2);
                            editUserMap.put("sex", "2");
                        } else {
                            userModel.setSex(1);
                            editUserMap.put("sex", "1");
                        }

                        userSex.setText(sexNumPicPop.getData());
                        editUserInfo(false);
                        sexNumPicPop.dismiss();
                    }
                });

        numPickPop = new NumberPickerPopupwinow(MyInfoActivity.this,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        numPickPop.dismiss();
                        userBirthday.setText(numPickPop.getData2());
                        if (!StringUtil.isEmpty(userBirthday.getText().toString())) {
                            editUserMap.clear();
                            editUserMap.put("birthday", userBirthday.getText().toString());
                            userModel.setBirthday(userBirthday.getText().toString());
                            editUserInfo(false);
                        } else {
                            showToast("不能为空");
                        }
                    }
                });
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
        birthday = findViewById(R.id.birthday);
        rootLayout = (ConstraintLayout) findViewById(R.id.layout_root1);
        name = (TextView) findViewById(R.id.name);
        ivNameArrow = (ImageView) findViewById(R.id.iv_name_arrow);
        lineName = (View) findViewById(R.id.line_name);
        nickname = (TextView) findViewById(R.id.nickname);
        ivNicknameArrow = (ImageView) findViewById(R.id.iv_nickname_arrow);
        lineNickname = (View) findViewById(R.id.line_nickname);
        sex = (TextView) findViewById(R.id.sex);
        ivSexArrow = (ImageView) findViewById(R.id.iv_sex_arrow);
        lineSex = (View) findViewById(R.id.line_sex);
        tvAddressValue = (TextView) findViewById(R.id.tv_address_value);
        lineAddress = (View) findViewById(R.id.line_address);
        number = (TextView) findViewById(R.id.number);
        lineNumber = (View) findViewById(R.id.line_number);
        mail = (TextView) findViewById(R.id.mail);
        introText = (TextView) findViewById(R.id.intro);
        userIntroText = (TextView) findViewById(R.id.user_intro);


    }

    @Override
    public void initAction() {
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivityForResult(null, AreaListActivity.class, ADDRESS_REQUEST_CODE);
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(EditUserInfoActivity.UPDATE_TYPE, 0);
                bundle.putString(EditUserInfoActivity.UPDATE_VALUE, userModel.getName());
                goActivityForResult(bundle, EditUserInfoActivity.class, NAME_REQUEST_CODE);
            }
        });

        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(EditUserInfoActivity.UPDATE_TYPE, 1);
                bundle.putString(EditUserInfoActivity.UPDATE_VALUE, userModel.getHx_username());
                goActivityForResult(bundle, EditUserInfoActivity.class, NICKNAME_REQUEST_CODE);
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(EditUserInfoActivity.UPDATE_TYPE, 2);
                bundle.putString(EditUserInfoActivity.UPDATE_VALUE, userModel.getPhone());
                goActivityForResult(bundle, EditUserInfoActivity.class, PHONE_REQUEST_CODE);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(EditUserInfoActivity.UPDATE_TYPE, 3);
                bundle.putString(EditUserInfoActivity.UPDATE_VALUE, userModel.getEmail());
                goActivityForResult(bundle, EditUserInfoActivity.class, EMAIL_REQUEST_CODE);
            }
        });
        introText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(EditUserInfoActivity.UPDATE_TYPE, 7);
                bundle.putString(EditUserInfoActivity.UPDATE_VALUE, userModel.getEmail());
                goActivityForResult(bundle, EditUserInfoActivity.class, EMAIL_REQUEST_CODE);
            }
        });

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.show(mYear, mMonth - 1, mDay);
                // 显示窗口
                if ("R7Plus".equals(Build.MODEL)) {
                    numPickPop.showAtLocation(rootLayout, Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, PhoneUtil.dip2px(MyInfoActivity.this, 36));
                } else {
                    numPickPop.showAtLocation(rootLayout, Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });

        dialog.setOnClickListener(new SelectorDateDialog.OnClickListener() {
            @Override
            public boolean onSure(int year, int month, int day, long time) {
                mYear = year;
                mMonth = month;
                mDay = day;

                userBirthday.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
                return false;
            }

            @Override
            public boolean onCancel() {
                return false;
            }
        });


        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 显示窗口
                if ("R7Plus".equals(Build.MODEL)) {
                    sexNumPicPop.showAtLocation(rootLayout, Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, PhoneUtil.dip2px(mContext, 36));
                } else {
                    sexNumPicPop.showAtLocation(rootLayout, Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });

        iconHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.show();
                // Multi image selector form an Activity
               /* MultiImageSelector.create(MyInfoActivity.this)
                        .showCamera(true) // 是否显示相机. 默认为显示
                        .count(1) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                        .single() // 单选模式
                        .multi() // 多选模式, 默认模式;
                        .start(MyInfoActivity.this, REQUEST_IMAGE);*/
            }
        });

//        userName.setOnKeyListener(this);
//        userNickname.setOnKeyListener(this);
//        userNumber.setOnKeyListener(this);
//        userMail.setOnKeyListener(this);
//        userBirthday.setOnKeyListener(this);

        getUserInfo();
    }


    private void getUserInfo() {
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).getUserInfo()
                , new Action1<UserInfoModel>() {
                    @Override
                    public void call(UserInfoModel model) {
                        hideLoadDialog();
                        if (model != null && model.getCode() == CommonConstant.NET_SUC_CODE && model.getData() != null) {
                            UserInfoDelegate.getInstance().saveUserInfo(model.getData());
                            userName.setText(model.getData().getName());
                            if (1 == model.getData().getSex()) {
                                userSex.setText("男");
                            } else {
                                userSex.setText("女");
                            }
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

    private void updateUserInfo() {
        userModel = TalkLawApplication.getUserInfo();
        if (userModel == null) {
            goActivity(null, LoginActivity.class);
            return;
        }
        Glide.with(mContext)
                .load(userModel.getHeadimg())
                .placeholder(R.mipmap.icon_head_def_cir)
                .error(R.mipmap.icon_head_def_cir)
                .transform(new CenterCrop(mContext), new GlideCircleTransform(mContext))
                .crossFade()
                .into(iconHead);

        userName.setText(userModel.getName());
        userNickname.setText(userModel.getHx_username());
        if (userModel.getSex() == 1) {
            userSex.setText("男");
        } else {
            userSex.setText("女");
        }
        tvAddressValue.setText(userModel.getProvince() + "-" + userModel.getCity());
        userBirthday.setText(userModel.getBirthday());
        userMail.setText(userModel.getEmail());
        userNumber.setText(userModel.getPhone());
        userIntroText.setText(userModel.getIntro());
    }

    private void editUserInfo(boolean isEditHeadImg) {
        showLoadDialog();
        Log.e("tag", "editUserMap=" + editUserMap);
        addNetwork(Api.getInstance().getService(ApiService.class).editUserInfo(editUserMap)
                , new Action1<UserInfoModel>() {
                    @Override
                    public void call(UserInfoModel model) {
                        hideLoadDialog();
                        if (model.getCode() == CommonConstant.NET_SUC_CODE) {
                            Glide.with(mContext)
                                    .load(model.getData().getHeadimg())
                                    .placeholder(R.mipmap.icon_head_def_cir)
                                    .error(R.mipmap.icon_head_def_cir)
                                    .transform(new CenterCrop(mContext), new GlideCircleTransform(mContext))
                                    .crossFade()
                                    .into(iconHead);
                            UserInfoDelegate.getInstance().saveUserInfo(model.getData());
                            rxManage.post(EDIT_HEADER,"");
                        }
                        showToast(model.getMsg());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        Log.e("tag", "throwable=" + throwable);
                        showToast("修改失败");
                    }
                });
    }

//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
//            if (userModel == null) {
//                goActivity(null, LoginActivity.class);
//                return false;
//            }
//            switch (v.getId()) {
//                case R.id.user_name:
//                    if (!StringUtil.isEmpty(userName.getText().toString())) {
//                        editUserMap.clear();
//                        editUserMap.put("name", userName.getText().toString());
//                        userModel.setName(userName.getText().toString());
//                        editUserInfo(false);
//                    } else {
//                        showToast("不能为空");
//                    }
//                    break;
//                case R.id.user_nickname:
//                    if (!StringUtil.isEmpty(userNickname.getText().toString())) {
//                        editUserMap.clear();
//                        editUserMap.put("intro", userNickname.getText().toString());
//                        userModel.setHx_username(userNickname.getText().toString());
//                        editUserInfo();
//                    } else {
//                        showToast("不能为空");
//                    }
//                    break;
//                case R.id.user_number:
//                    if (!StringUtil.isEmpty(userNumber.getText().toString())) {
//                        editUserMap.clear();
//                        editUserMap.put("phone", userNumber.getText().toString());
//                        userModel.setName(userNumber.getText().toString());
//                        editUserInfo();
//                    } else {
//                        showToast("不能为空");
//                    }
//                    break;
//                case R.id.user_birthday:
////                    if (!StringUtil.isEmpty(userBirthday.getText().toString())) {
////                        editUserMap.clear();
////                        editUserMap.put("birthday",userBirthday.getText().toString());
////                        userModel.setName(userBirthday.getText().toString());
////                        editUserInfo();
////                    }else {
////                        showToast("不能为空");
////                    }
//                    break;
//                case R.id.user_mail:
//                    if (!StringUtil.isEmpty(userMail.getText().toString())) {
//                        editUserMap.clear();
//                        editUserMap.put("email", userMail.getText().toString());
//                        userModel.setName(userMail.getText().toString());
//                        editUserInfo();
//                    } else {
//                        showToast("不能为空");
//                    }
//                    break;
//                case R.id.user_sex:
//                    Log.e("tag", "user_sex==1");
////                    if (!StringUtil.isEmpty(userName.getText().toString())
////                            &&(!StringUtil.equals(userName.getText().toString(),"男")
////                    ||!StringUtil.equals(userName.getText().toString(),"女"))) {
////                        editUserMap.clear();
////                        editUserMap.put("sex",userSex.getText().toString());
////                        if(StringUtil.equals(userName.getText().toString(),"女")){
////                            userModel.setSex(2);
////                            editUserMap.put("sex","2");
////                        }else {
////                            userModel.setSex(1);
////                            editUserMap.put("sex","1");
////                        }
////                        editUserInfo();
////                    }else {
////                        showToast("请输入男或女");
////                    }
//
//
//                    break;
//
//            }
//            return true;
//        } else if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
//            return true;
//        }
//        return false;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == NAME_REQUEST_CODE || requestCode == NICKNAME_REQUEST_CODE
                || requestCode == PHONE_REQUEST_CODE || requestCode == EMAIL_REQUEST_CODE || requestCode == ADDRESS_REQUEST_CODE) {
//            userModel = TalkLawApplication.getUserInfo();
            updateUserInfo();
        }

        if (requestCode == REQUEST_IMAGE && data != null) {
            /*final List<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            if (pathList != null && pathList.size() > 0) {

                    }
                });

            }*/
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (result == null) {
            return;
        }
        if (result.getImages() == null || result.getImages().size() == 0
                || result.getImages().get(0) == null) {
            return;
        }
        final String url = result.getImages().get(0).getOriginalPath();
        userModel.setHeadimg(url);
        Base64Util.encodeBase64File(this, url, new EncodeCallBack() {
            @Override
            public void callBack(final String str) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        editUserMap.clear();
                        editUserMap.put("headimg", "data:image/jpeg;base64," + str);
                        editUserInfo(true);
                    }
                });
            }
        });
    }
}
