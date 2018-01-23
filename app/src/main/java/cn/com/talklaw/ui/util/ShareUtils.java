package cn.com.talklaw.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import cn.com.talklaw.R;
import cn.com.talklaw.model.ShareModel;

/**
 * @author wangcc
 * @date 2018/1/23
 * @describe
 */

public class ShareUtils {

    private ShareAction mShareAction;
    private Activity activity;
    private UMShareListener listener;
    public ShareUtils(Activity activity, UMShareListener listener){
        mShareAction=new ShareAction(activity);
        mShareAction.setCallback(listener);
        this.listener=listener;
        this.activity=activity;
    }

    public void shareWeb(SHARE_MEDIA media, String url, int thumbResId){
        if (!checkPlatformInstall(media)){
            Toast.makeText(activity,"分享失败",Toast.LENGTH_SHORT).show();
            return;
        }
        UMImage thumb=new UMImage(activity,thumbResId);
        UMWeb web = new UMWeb(url);
        web.setTitle("This is music title");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("my description");//描述

        mShareAction
                .setPlatform(media)
                .withMedia(web)
                .share();
    }

    public void shareWeb(SHARE_MEDIA media, ShareModel model){
        if (!checkPlatformInstall(media)||model==null){
            if (listener!=null){
                listener.onStart(media);
                listener.onError(media,new Exception("app not install"));
            }
            return;
        }
        UMImage thumb=null;
        if (TextUtils.isEmpty(model.getShareImgUrl())){
            thumb=new UMImage(activity, R.mipmap.logo);
        }else {
            thumb=new UMImage(activity, model.getShareImgUrl());
        }
        UMWeb web = new UMWeb(model.getShareUrl());
        web.setThumb(thumb);  //缩略图
        web.setDescription(model.getShareContent());//描述
        if (media== SHARE_MEDIA.TWITTER){
            mShareAction.setPlatform(media)
                    .withText(model.getShareTitle()+"\n"+model.getShareContent()
                            +"\n"+model.getShareUrl())
                    .withMedia(thumb)
                    .share();
        }else {
            web.setTitle(model.getShareTitle());//标题
            mShareAction
                    .setPlatform(media)
                    .withMedia(web)
                    .share();
        }
    }

    public boolean checkPlatformInstall(SHARE_MEDIA media){
        boolean install=true;
        switch (media){
            case WEIXIN:
            case WEIXIN_CIRCLE:
            case WEIXIN_FAVORITE:
                install= UMShareAPI.get(activity.getApplicationContext()).isInstall(activity,media);
                break;
            case TWITTER:
//                install=UMShareAPI.get(activity.getApplicationContext()).isInstall(activity,media);
                break;
            default:
                break;

        }
        return install;
    }
}
