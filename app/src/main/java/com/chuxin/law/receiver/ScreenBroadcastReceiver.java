package com.chuxin.law.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author wangcc
 * @date 2018/4/20
 * @describe
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
            if (mScreenStateListener!=null) {
                mScreenStateListener.onScreenOn();
            }
        } else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            if (mScreenStateListener!=null) {
                mScreenStateListener.onScreenOff();
            }
        } else if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
            if (mScreenStateListener!=null) {
                mScreenStateListener.onUserPresent();
            }
        }
    }

    private ScreenStateListener mScreenStateListener;

    public void setScreenStateListener(ScreenStateListener mScreenStateListener) {
        this.mScreenStateListener = mScreenStateListener;
    }

    public interface  ScreenStateListener{
        void onScreenOn();
        void onScreenOff();
        void onUserPresent();
    }
}
