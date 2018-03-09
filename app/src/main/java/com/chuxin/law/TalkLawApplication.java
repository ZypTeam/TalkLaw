package com.chuxin.law;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.chuxin.law.common.DaoInstance;
import com.chuxin.law.common.HeaderTalkInterceptor;
import com.chuxin.law.common.SharePrefenceConstant;
import com.chuxin.law.common.UserInfoDelegate;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ry.SealAppContext;
import com.chuxin.law.ry.SealUserInfoManager;
import com.chuxin.law.ry.message.TestMessage;
import com.chuxin.law.ry.message.provider.ContactNotificationMessageProvider;
import com.chuxin.law.ry.message.provider.TestMessageProvider;
import com.chuxin.law.ry.my.mymessage.PayMessage;
import com.chuxin.law.ry.my.mymessage.PayMessageProvider;
import com.chuxin.law.ry.server.utils.NLog;
import com.chuxin.law.ry.stetho.RongDatabaseDriver;
import com.chuxin.law.ry.stetho.RongDatabaseFilesProvider;
import com.chuxin.law.ry.stetho.RongDbFilesDumperPlugin;
import com.chuxin.law.ry.utils.SharedPreferencesContext;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.facebook.stetho.inspector.database.DefaultDatabaseConnectionProvider;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.jusfoun.baselibrary.BaseApplication;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.Util.SharePrefenceUtils;
import com.jusfoun.baselibrary.net.Api;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.display.FadeInBitmapDisplayer;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.RealTimeLocationMessageProvider;
import io.rong.imlib.ipc.RongExceptionHandler;
import io.rong.push.RongPushClient;

/**
 * @author wangcc
 * @date 2017/11/17
 * @describe
 */

public class TalkLawApplication extends BaseApplication {

    //各个平台的配置
    {
        PlatformConfig.setWeixin("wx6acb4c4141bd83a0", "5a540845a5a55a0740823ef4fe6616be");
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

        Config.DEBUG = true;

        FeedbackAPI.init(this, "24769686","13aeb43eb422a0703ab5e7ef8235e9b5");

        initRongYun();
    }

    public static TalkLawApplication getInstance() {
        return instance;

    }

    public static void exitUser() {
        SharePrefenceUtils.getInstance().setString(SharePrefenceConstant.USER_MODEL, "");
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


    /**
     * 初始化融云
     * */
    private static DisplayImageOptions options;
    private void initRongYun(){
        Stetho.initialize(new Stetho.Initializer(this) {
            @Override
            protected Iterable<DumperPlugin> getDumperPlugins() {
                return new Stetho.DefaultDumperPluginsBuilder(TalkLawApplication.this)
                        .provide(new RongDbFilesDumperPlugin(TalkLawApplication.this, new RongDatabaseFilesProvider(TalkLawApplication.this)))
                        .finish();
            }

            @Override
            protected Iterable<ChromeDevtoolsDomain> getInspectorModules() {
                Stetho.DefaultInspectorModulesBuilder defaultInspectorModulesBuilder = new Stetho.DefaultInspectorModulesBuilder(TalkLawApplication.this);
                defaultInspectorModulesBuilder.provideDatabaseDriver(new RongDatabaseDriver(TalkLawApplication.this, new RongDatabaseFilesProvider(TalkLawApplication.this), new DefaultDatabaseConnectionProvider()));
                return defaultInspectorModulesBuilder.finish();
            }
        });

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

//            LeakCanary.install(this);//内存泄露检测
            RongPushClient.registerHWPush(this);
            RongPushClient.registerMiPush(this, "2882303761517473625", "5451747338625");
//            try {
//                RongPushClient.registerFCM(this);
//            } catch (RongException e) {
//                e.printStackTrace();
//            }

            /**
             * 注意：
             *
             * IMKit SDK调用第一步 初始化
             *
             * context上下文
             *
             * 只有两个进程需要初始化，主进程和 push 进程
             */
            RongIM.setServerInfo("nav.cn.ronghub.com", "up.qbox.me");
            RongIM.init(this);
            NLog.setDebug(true);//Seal Module Log 开关
            SealAppContext.init(this);
            SharedPreferencesContext.init(this);
            Thread.setDefaultUncaughtExceptionHandler(new RongExceptionHandler(this));

            try {
                RongIM.registerMessageTemplate(new ContactNotificationMessageProvider());
                RongIM.registerMessageTemplate(new RealTimeLocationMessageProvider());
                RongIM.registerMessageType(TestMessage.class);

                RongIM.registerMessageTemplate(new TestMessageProvider());
                RongIM.registerMessageType(PayMessage.class);
                RongIM.registerMessageTemplate(new PayMessageProvider());


            } catch (Exception e) {
                e.printStackTrace();
            }

            openSealDBIfHasCachedToken();

            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.de_default_portrait)
                    .showImageOnFail(R.drawable.de_default_portrait)
                    .showImageOnLoading(R.drawable.de_default_portrait)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();

////            RongExtensionManager.getInstance().registerExtensionModule(new PTTExtensionModule(this, true, 1000 * 60));
//            RongExtensionManager.getInstance().registerExtensionModule(new ContactCardExtensionModule(new IContactCardInfoProvider() {
//                @Override
//                public void getContactAllInfoProvider(final IContactCardInfoCallback contactInfoCallback) {
//                    SealUserInfoManager.getInstance().getFriends(new SealUserInfoManager.ResultCallback<List<Friend>>() {
//                        @Override
//                        public void onSuccess(List<Friend> friendList) {
//                            contactInfoCallback.getContactCardInfoCallback(friendList);
//                        }
//
//                        @Override
//                        public void onError(String errString) {
//                            contactInfoCallback.getContactCardInfoCallback(null);
//                        }
//                    });
//                }
//
//                @Override
//                public void getContactAppointedInfoProvider(String userId, String name, String portrait, final IContactCardInfoCallback contactInfoCallback) {
//                    SealUserInfoManager.getInstance().getFriendByID(userId, new SealUserInfoManager.ResultCallback<Friend>() {
//                        @Override
//                        public void onSuccess(Friend friend) {
//                            List<UserInfo> list = new ArrayList<>();
//                            list.add(friend);
//                            contactInfoCallback.getContactCardInfoCallback(list);
//                        }
//
//                        @Override
//                        public void onError(String errString) {
//                            contactInfoCallback.getContactCardInfoCallback(null);
//                        }
//                    });
//                }
//
//            }, new IContactCardClickListener() {
//                @Override
//                public void onContactCardClick(View view, ContactMessage content) {
//                    Intent intent = new Intent(view.getContext(), UserDetailActivity.class);
//                    Friend friend = SealUserInfoManager.getInstance().getFriendByID(content.getId());
//                    if (friend == null) {
//                        UserInfo userInfo = new UserInfo(content.getId(), content.getName(),
//                                Uri.parse(TextUtils.isEmpty(content.getImgUrl()) ? RongGenerate.generateDefaultAvatar(content.getName(), content.getId()) : content.getImgUrl()));
//                        friend = CharacterParser.getInstance().generateFriendFromUserInfo(userInfo);
//                    }
//                    intent.putExtra("friend", friend);
//                    view.getContext().startActivity(intent);
//                }
//            }));
//            RongExtensionManager.getInstance().registerExtensionModule(new RecognizeExtensionModule());

        }
    }

    public static DisplayImageOptions getOptions() {
        return options;
    }

    private void openSealDBIfHasCachedToken() {
        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        String cachedToken = sp.getString("loginToken", "");
        if (!TextUtils.isEmpty(cachedToken)) {
            String current = getCurProcessName(this);
            String mainProcessName = getPackageName();
            if (mainProcessName.equals(current)) {
                SealUserInfoManager.getInstance().openDB();
            }
        }
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
