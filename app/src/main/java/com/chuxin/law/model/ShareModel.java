package com.chuxin.law.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/1/23
 * @describe 分享 model
 */

public class ShareModel implements Serializable {

    private String shareTitle;
    private String shareContent;
    private String shareImgUrl;
    private String shareUrl;
    private String eName;
    private String time;
    private String id;
    private Bitmap bitmap;
    private boolean isWords;

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareImgUrl() {
        return shareImgUrl;
    }

    public void setShareImgUrl(String shareImgUrl) {
        this.shareImgUrl = shareImgUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isWords() {
        return isWords;
    }

    public void setWords(boolean words) {
        isWords = words;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
