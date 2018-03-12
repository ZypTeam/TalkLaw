package com.chuxin.law.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jusfoun.baselibrary.Util.AppUtil;

import io.rong.imlib.model.UserInfo;

/**
 * @author zhaoyapeng
 * @version create time:18/3/1216:28
 * @Email zyp@jusfoun.com
 * @Description ${存储好友信息}
 */
public class FriendsSp {

    private static final String NAME = "friends";
    private static final String INFO = "info";

    public static void saveFriedns(Context context, UserInfo userInfo) {
        SharedPreferences preferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(userInfo.getUserId(), new Gson().toJson(userInfo)).commit();
    }

    public static UserInfo getFriendsInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + NAME, Context.MODE_PRIVATE);
        String info =  preferences.getString(INFO, "");
        if(!TextUtils.isEmpty(info)) {
            UserInfo userInfo = new Gson().fromJson(info,UserInfo.class);
            return userInfo;
        }
        return null;
    }

    public static void clear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + NAME, Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

}
