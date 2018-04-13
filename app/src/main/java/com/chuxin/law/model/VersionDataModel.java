package com.chuxin.law.model;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/4/8
 * @describe
 */

public class VersionDataModel implements Serializable {

    private int type;
    private String title;
    private String describe;
    private String url;
    public int versioncode;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VersionDataModel{" +
                "describe='" + describe + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title  +
                '}';
    }
}
