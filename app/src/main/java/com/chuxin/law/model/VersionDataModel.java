package com.chuxin.law.model;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/4/8
 * @describe
 */

public class VersionDataModel implements Serializable {

    private String describe,url,versionname,titletext;

    private int update,filter,versioncode;

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

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getTitletext() {
        return titletext;
    }

    public void setTitletext(String titletext) {
        this.titletext = titletext;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    @Override
    public String toString() {
        return "VersionDataModel{" +
                "describe='" + describe + '\'' +
                ", url='" + url + '\'' +
                ", versionname='" + versionname + '\'' +
                ", titletext='" + titletext + '\'' +
                ", update=" + update +
                ", filter=" + filter +
                ", versioncode=" + versioncode +
                '}';
    }
}
