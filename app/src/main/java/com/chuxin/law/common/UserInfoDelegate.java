package com.chuxin.law.common;

import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.model.UserModel;
import com.google.gson.Gson;
import com.jusfoun.baselibrary.Util.SharePrefenceUtils;

/**
 * @author wangcc
 * @date 2018/2/7
 * @describe 用户信息
 */

public class UserInfoDelegate {
    private UserModel userModel;

    private static final class SingletonHolder{
        private static UserInfoDelegate delegate=new UserInfoDelegate();
    }

    private UserInfoDelegate(){
        String string = SharePrefenceUtils.getInstance().getString(SharePrefenceConstant.USER_MODEL);
        userModel = new Gson().fromJson(string, UserModel.class);
    }

    public static UserInfoDelegate getInstance(){
        return SingletonHolder.delegate;
    }

    public UserModel getUserInfo(){
        return userModel;
    }

    public void saveUserInfo(UserModel userModel){
        if (userModel == null) {
            return;
        }
        this.userModel=userModel;
        String string = new Gson().toJson(userModel);
        SharePrefenceUtils.getInstance().setString(SharePrefenceConstant.USER_MODEL, string);
    }

    public String getUserId() {
        String userId = null;
        if (userModel != null) {
            userId = userModel.getId();
        }
        return userId;
    }

    public String getToken() {
        String token = null;
        if (userModel != null) {
            token = userModel.getAppToken();
        }
        return token;
    }


}
