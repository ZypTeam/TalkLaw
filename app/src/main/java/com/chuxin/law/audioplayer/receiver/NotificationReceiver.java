package com.chuxin.law.audioplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

import java.util.Date;

/**
 * @author wangcc
 * @date 2018/3/11
 * @describe 通知栏广播
 */

public class NotificationReceiver {

    /**
     * 是否注册成功
     */
    private boolean isRegisterSuccess = false;
    private Context mContext;
    /**
     * 注册成功广播
     */
    private String ACTION_SUCCESS = "com.zlm.hp.notification.success_" + new Date().getTime();

    /**
     * 通知栏app播放歌曲
     */
    public static String NOTIFIATION_APP_PLAYMUSIC = "com.notification.app.playmusic";
    /**
     * 通知栏app暂停歌曲
     */
    public static String NOTIFIATION_APP_PAUSEMUSIC = "com.notification.app.pausemusic";
    /**
     * 通知栏app上一首歌曲
     */
    public static String NOTIFIATION_APP_PREMUSIC = "com.notification.app.premusic";
    /**
     * 通知栏app下一首歌曲
     */
    public static String NOTIFIATION_APP_NEXTMUSIC = "com.notification.app.nextmusic";

    /**
     * 通知栏显示桌面歌词
     */
    public static String NOTIFIATION_DESLRC_SHOW = "com.notification.des.lrc.show";
    /**
     * 通知栏隐藏桌面歌词
     */
    public static String NOTIFIATION_DESLRC_HIDE = "com.notification.des.lrc.hide";
    /**
     * 通知栏桌面歌词解锁
     */
    public static String NOTIFIATION_DESLRC_UNLOCK = "com.notification.des.lrc.unlock";

    private BroadcastReceiver mNotificationBroadcastReceiver;
    private IntentFilter mNotificationIntentFilter;
    private NotificationReceiverListener mNotificationReceiverListener;

    public NotificationReceiver(Context context) {
        this.mContext = context;
        mNotificationIntentFilter = new IntentFilter();

        //
        mNotificationIntentFilter.addAction(ACTION_SUCCESS);
        mNotificationIntentFilter.addAction(NOTIFIATION_APP_PLAYMUSIC);
        mNotificationIntentFilter.addAction(NOTIFIATION_APP_PAUSEMUSIC);
        mNotificationIntentFilter.addAction(NOTIFIATION_APP_PREMUSIC);
        mNotificationIntentFilter.addAction(NOTIFIATION_APP_NEXTMUSIC);
        mNotificationIntentFilter.addAction(NOTIFIATION_DESLRC_SHOW);
        mNotificationIntentFilter.addAction(NOTIFIATION_DESLRC_HIDE);
        mNotificationIntentFilter.addAction(NOTIFIATION_DESLRC_UNLOCK);


    }

    /**
     *
     */
    private Handler mNotificationHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mNotificationReceiverListener != null) {
                Intent intent = (Intent) msg.obj;
                if (intent.getAction().equals(ACTION_SUCCESS)) {
                    isRegisterSuccess = true;

                } else {
                    mNotificationReceiverListener.onReceive(mContext, intent);
                }

            }
        }
    };

    /**
     * 注册广播
     *
     * @param context
     */
    public void registerReceiver(Context context) {
        if (mNotificationBroadcastReceiver == null) {
            //
            mNotificationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    Message msg = new Message();
                    msg.obj = intent;
                    mNotificationHandler.sendMessage(msg);


                }
            };

            mContext.registerReceiver(mNotificationBroadcastReceiver, mNotificationIntentFilter);
            //发送注册成功广播
            Intent successIntent = new Intent(ACTION_SUCCESS);
            successIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            mContext.sendBroadcast(successIntent);

        }
    }

    /**
     * 取消注册广播
     */
    public void unregisterReceiver(Context context) {
        if (mNotificationReceiverListener != null && isRegisterSuccess) {

            mContext.unregisterReceiver(mNotificationBroadcastReceiver);

        }

    }

    ///////////////////////////////////
    public interface NotificationReceiverListener {
        void onReceive(Context context, Intent intent);
    }

    public void setNotificationReceiverListener(NotificationReceiverListener mNotificationReceiverListener) {
        this.mNotificationReceiverListener = mNotificationReceiverListener;
    }
}
