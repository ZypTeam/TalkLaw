package com.chuxin.law;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.chuxin.law.common.HeaderTalkInterceptor;
import com.chuxin.law.common.UserInfoDelegate;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMCallManager;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.jusfoun.baselibrary.BaseApplication;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.Util.SharePrefenceUtils;
import com.jusfoun.baselibrary.net.Api;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.com.talklaw.xh.DemoHelper;

import com.chuxin.law.common.DaoInstance;
import com.chuxin.law.common.SharePrefenceConstant;
import com.chuxin.law.model.UserModel;

/**
 * @author wangcc
 * @date 2017/11/17
 * @describe
 */

public class TalkLawApplication extends BaseApplication {

    //各个平台的配置
    {
        PlatformConfig.setWeixin("wx6acb4c4141bd83a0", "fe02da59c24dcfd6429996bfca8ea577");
        PlatformConfig.setSinaWeibo("1701976759", "c9f6b6d5015055964780e0c56c3e59a5", "http:www.sharesdk.cn");
        PlatformConfig.setQQZone("1106542171", "iLjGMwSEXLgyWWKG");
    }

    private static TalkLawApplication instance;
    /**
     * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
     */
    public static String currentUserNick = "";

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        instance = this;
        SharePrefenceUtils.getInstance().register(this, getPackageName());
        Api.getInstance().register(this, getString(R.string.url))
                .addInterceptro(new HeaderTalkInterceptor())
                .build();
        DaoInstance.getInstance().regester(this);
        LogUtil.setDebugable(BuildConfig.LOG_MODE);
        UMShareAPI.get(this);

        DemoHelper.getInstance().init(this);
        Config.DEBUG = true;

        FeedbackAPI.init(this, "24769686","13aeb43eb422a0703ab5e7ef8235e9b5");
    }

    public static TalkLawApplication getInstance() {
        return instance;

    }

    public static void exitUser() {
        SharePrefenceUtils.getInstance().setString(SharePrefenceConstant.USER_MODEL, "");
        //此方法为异步方法
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub

            }
        });
    }


    public static UserModel getUserInfo() {
        return UserInfoDelegate.getInstance().getUserInfo();
    }

    public static String getUserId() {
        return UserInfoDelegate.getInstance().getUserId();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
