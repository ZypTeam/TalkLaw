package com.chuxin.law.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.chuxin.law.model.UserModel;
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

    public static void saveFriedns(Context context, UserModel userInfo) {
        SharedPreferences preferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(userInfo.getUserid() ,new Gson().toJson(userInfo)).commit();
    }

    public static UserInfo getFriendsInfo(Context context,String userId) {
        SharedPreferences preferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + NAME, Context.MODE_PRIVATE);
        String info =  preferences.getString(userId,"");
        if(!TextUtils.isEmpty(info)) {
            UserModel userInfo = new Gson().fromJson(info,UserModel.class);
            if(TextUtils.isEmpty(userInfo.getHeadimg())) {
                Log.e("tag","userInfo="+userInfo.getHeadimg());
                userInfo.setHeadimg("http://");

            }

            if(TextUtils.isEmpty(userInfo.getName())) {
                userInfo.setName("说法");

            }
            UserInfo model = new UserInfo(userInfo.getUserid(), userInfo.getName(), Uri.parse(userInfo.getHeadimg()));
            return model;
        }
        return null;
    }

    public static void clear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + NAME, Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

}
