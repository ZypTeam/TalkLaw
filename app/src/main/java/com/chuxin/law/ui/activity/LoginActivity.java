package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.HeaderTalkInterceptor;
import com.chuxin.law.common.UserInfoDelegate;
import com.chuxin.law.model.UserInfoModel;
import com.chuxin.law.ry.SealAppContext;
import com.chuxin.law.ry.SealConst;
import com.chuxin.law.ry.SealUserInfoManager;
import com.chuxin.law.sharedpreferences.FriendsSp;
import com.google.gson.Gson;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author wangcc
 * @date 2017/12/12
 * @describe
 */

public class LoginActivity extends BaseTalkLawActivity {
    protected TextView txt;
    protected EditText number;
    protected EditText code;
    protected TextView getCode;
    protected Button login;
    protected TextView loginAgree;
    protected ImageView qq;
    protected ImageView weibo;
    protected ImageView wechat;
    private Subscription timer;
    private UMShareAPI mShareAPI;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            LogUtil.e("auth", "onComplete==" + data.toString());
            if (platform == SHARE_MEDIA.WEIXIN) {
                thridLogin("wx", data.get("uid"), data.get("name"), data.get("iconurl"));
            } else if (platform == SHARE_MEDIA.SINA) {

            } else if (platform == SHARE_MEDIA.QQ) {
                thridLogin("qq", data.get("uid"), data.get("name"), data.get("iconurl"));
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            LogUtil.e("auth", "onError==" + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            LogUtil.e("auth", "onCancel==" + platform);
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initDatas() {
        TAG = getClass().getSimpleName();
        mShareAPI = UMShareAPI.get(mContext);
        TAG = getClass().getSimpleName();

        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
    }

    @Override
    public void initView() {
        txt = (TextView) findViewById(R.id.txt);
        number = (EditText) findViewById(R.id.number);
        code = (EditText) findViewById(R.id.code);
        getCode = (TextView) findViewById(R.id.get_code);
        login = (Button) findViewById(R.id.login);
        loginAgree = (TextView) findViewById(R.id.login_agree);
        qq = (ImageView) findViewById(R.id.qq);
        weibo = (ImageView) findViewById(R.id.weibo);
        wechat = (ImageView) findViewById(R.id.wechat);

    }

    @Override
    public void initAction() {
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCode();
                getCode(number.getText().toString());

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
            }
        });

        weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, umAuthListener);
            }
        });

        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
            }
        });

        loginAgree.setText(Html.fromHtml("登录既代表阅读并同意<font color='#CB1E28'>服务条款</font>"));

        loginAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", "http://api.law.wzgeek.com/service.html");
                intent.putExtra("title", "服务条款");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private void login() {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", number.getText().toString());
        params.put("code", code.getText().toString());
        addNetwork(Api.getInstance().getService(ApiService.class).login(params)
                , new Action1<UserInfoModel>() {
                    @Override
                    public void call(UserInfoModel userInfoModel) {
                        if (userInfoModel != null && userInfoModel.getData() != null && userInfoModel.getCode() == 10000) {
                            //TODO :登录成功直接登录 hide loading 新用户 融云登录注册调试后去掉
                            hideLoadDialog();
//                            UserInfoDelegate.getInstance().saveUserInfo(userInfoModel.getData());

//                            goActivity(null, HomeActivity.class);
//                            finish();

//                            if (userInfoModel.getData() != null) {
//                                RongIM.getInstance().refreshUserInfoCache(new UserInfo("userId", userInfoModel.getData().getUserid(), Uri.parse(userInfoModel.getData().getHeadimg())));
//                            }

//                            UserInfoDelegate.getInstance().saveUserInfo(userInfoModel.getData());
//                            goActivity(null, HomeActivity.class);

                            Api.getInstance().register(LoginActivity.this, getString(R.string.url))
                                    .addInterceptro(new HeaderTalkInterceptor())
                                    .build();
                            loginHx(userInfoModel);
                        } else {
                            hideLoadDialog();
                            Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        LogUtil.e("login", throwable.getMessage());
                        Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void thridLogin(String source, String uid, String name, String headimg) {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("source", TextUtils.isEmpty(source) ? "" : source);
        params.put("uid", TextUtils.isEmpty(uid) ? "" : uid);
        params.put("name", TextUtils.isEmpty(name) ? "" : name);
        params.put("headimg", TextUtils.isEmpty(headimg) ? "" : headimg);
        addNetwork(Api.getInstance().getService(ApiService.class).thridLogin(params)
                , new Action1<UserInfoModel>() {
                    @Override
                    public void call(UserInfoModel userInfoModel) {
                        hideLoadDialog();
                        if (userInfoModel != null && userInfoModel.getCode() == 10000) {
//                            Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
//                            UserInfoDelegate.getInstance().saveUserInfo(userInfoModel.getData());
//                            goActivity(null, HomeActivity.class);


//                            UserInfoDelegate.getInstance().saveUserInfo(userInfoModel.getData());
//                            goActivity(null, HomeActivity.class);

                            loginHx(userInfoModel);
                        } else {
                            Toast.makeText(mContext, userInfoModel.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        LogUtil.e("login", throwable.getMessage());
                        Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getCode(String number) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", number);
        params.put("type", "1");
        addNetwork(Api.getInstance().getService(ApiService.class).getAuthCode(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        Log.e("noDataModel", noDataModel.getMsg());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("throwable", throwable.getMessage());
                    }
                });
    }

    private void startCode() {
        getCode.setEnabled(false);
        timer = Observable.interval(1, TimeUnit.SECONDS)
                .take(60)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return 60 - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onCompleted() {
                        getCode.setEnabled(true);
                        getCode.setText("获取验证码");
                    }

                    @Override
                    public void onError(Throwable e) {
                        getCode.setText("获取验证码");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        getCode.setText(aLong + "");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null && timer.isUnsubscribed()) {
            timer.unsubscribe();
        }
    }


    /**
     * 登录环信
     */

    private void loginHx(final UserInfoModel userInfoModel) {


        RongIM.connect(userInfoModel.getData().rToken, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e("connect", "onTokenIncorrect");
//                reGetToken();
                hideLoadDialog();
                Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String s) {
                hideLoadDialog();
                if (userInfoModel.getData() != null) {

                    Log.e("tag", "hideLoadDialoghideLoadDialoghideLoadDialog=" + userInfoModel.getData().getUserid() + " " + userInfoModel.getData().getName() + " " + userInfoModel.getData().getHeadimg());
                    FriendsSp.saveFriedns(mContext, userInfoModel.getData());
//                    RongIM.getInstance().refreshUserInfoCache();
//                    RongIM.getInstance().setCurrentUserInfo(new UserInfo("userId", userInfoModel.getData().getUserid(), Uri.parse(userInfoModel.getData().getHeadimg())));
                }

                UserInfoDelegate.getInstance().saveUserInfo(userInfoModel.getData());
                Api.getInstance().register(LoginActivity.this, getString(R.string.url))
                        .addInterceptro(new HeaderTalkInterceptor())
                        .build();

                Log.e("tag", "UserInfoDelegate=" + new Gson().toJson(userInfoModel.getData()));
                editor.putString(SealConst.SEALTALK_LOGIN_ID, s);
                editor.commit();
                SealUserInfoManager.getInstance().openDB();

                Log.e("tag", "loginToken=" + userInfoModel.getData().rToken);
                editor.putString("loginToken", userInfoModel.getData().rToken);
                editor.commit();

                Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();


//                SealAppContext.getInstance().setInputProvider();
                goActivity(null, HomeActivity.class);
                finish();

//                connectResultId = s;
//                NLog.e("connect", "onSuccess userid:" + s);
//                editor.putString(SealConst.SEALTALK_LOGIN_ID, s);
//                editor.commit();
//                SealUserInfoManager.getInstance().openDB();
//                request(SYNC_USER_INFO, true);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                hideLoadDialog();
                Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                Log.e("connect", "onError errorcode:" + errorCode.getValue());
            }
        });

//        EMClient.getInstance().login(userInfoModel.getData().getHx_username(), StringUtil.getMD5Str(userInfoModel.getData().getHx_username()), new EMCallBack() {
//
//            @Override
//            public void onSuccess() {
//                Log.d(TAG, "login: onSuccess");
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        hideLoadDialog();
//                        Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
//                        UserInfoDelegate.getInstance().saveUserInfo(userInfoModel.getData());
//
//
//                        // ** manually load all local groups and conversation
//                        EMClient.getInstance().groupManager().loadAllGroups();
//                        EMClient.getInstance().chatManager().loadAllConversations();
//
//                        // update current user's display name for APNs
//                        boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
//                                TalkLawApplication.currentUserNick.trim());
//                        if (!updatenick) {
//                            Log.e("LoginActivity", "update current user nick fail");
//                        }
//                        DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//                        goActivity(null, HomeActivity.class);
//                        finish();
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//                Log.d(TAG, "login: onProgress");
//            }
//
//            @Override
//            public void onError(final int code, final String message) {
//                Log.d(TAG, "login: onError: " + code);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        hideLoadDialog();
//                        //TODO: 环信登录失败暂时存储用户信息 修改聊天SDK后去掉 by wang 2018年03月05日
//                        UserInfoDelegate.getInstance().saveUserInfo(userInfoModel.getData());
////                        Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
//                        goActivity(null,HomeActivity.class);
//                    }
//                });
//            }
//        });


    }

    private long mLastTime;

    /**
     * 退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mLastTime > 0 && System.currentTimeMillis() - mLastTime <= 2000) {
                TalkLawApplication.getBaseApplication().removeAll();
            } else {
                Toast.makeText(mContext, R.string.app_exit_string, Toast.LENGTH_SHORT).show();
                mLastTime = System.currentTimeMillis();
            }
            return true;
        }
        return false;
    }

}
