package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

/**
 * @author wangcc
 * @date 2018/4/8
 * @describe
 */

public class VersionModel extends BaseModel {

    private VersionDataModel versiondata;

    public VersionDataModel getVersiondata() {
        return versiondata;
    }

    public void setVersiondata(VersionDataModel versiondata) {
        this.versiondata = versiondata;
    }
}
