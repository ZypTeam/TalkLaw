package cn.com.talklaw;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.jusfoun.baselibrary.BaseApplication;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.Util.SharePrefenceUtils;
import com.jusfoun.baselibrary.net.Api;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.com.talklaw.comment.ApiService;
import cn.com.talklaw.comment.DaoInstance;
import cn.com.talklaw.comment.SharePrefenceConstant;
import cn.com.talklaw.model.UserInfoModel;
import cn.com.talklaw.model.UserModel;

/**
 * @author wangcc
 * @date 2017/11/17
 * @describe
 */

public class TalkLawApplication extends BaseApplication{

    //各个平台的配置
    {
        PlatformConfig.setWeixin("wx6acb4c4141bd83a0", "fe02da59c24dcfd6429996bfca8ea577");
        PlatformConfig.setSinaWeibo("1701976759", "c9f6b6d5015055964780e0c56c3e59a5","http:www.sharesdk.cn");
        PlatformConfig.setQQZone("1106542171", "iLjGMwSEXLgyWWKG");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Api.getInstance().register(this,getString(R.string.url));
        DaoInstance.getInstance().regester(this);
        SharePrefenceUtils.getInstance().register(this,getPackageName());
        LogUtil.setDebugable(BuildConfig.LOG_MODE);
        UMShareAPI.get(this);
    }

    public static void saveUserInfo(UserModel model){
        if (model==null){
            return;
        }
        String string=new Gson().toJson(model);
        SharePrefenceUtils.getInstance().setString(SharePrefenceConstant.USER_MODEL,string);
    }

    public static UserModel getUserInfo(){
        UserModel model;
        String string=SharePrefenceUtils.getInstance().getString(SharePrefenceConstant.USER_MODEL);
        model=new Gson().fromJson(string,UserModel.class);
        return model;
    }

    public static String getUserId(){
        String userId=null;
        UserModel model=getUserInfo();
        if (model!=null){
            userId=model.getId();
        }
        return userId;
    }
}
