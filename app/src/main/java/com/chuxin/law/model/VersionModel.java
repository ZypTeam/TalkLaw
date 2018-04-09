package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

/**
 * @author wangcc
 * @date 2018/4/8
 * @describe
 */

public class VersionModel extends BaseModel {

    private VersionDataModel data;

    public VersionDataModel getVersiondata() {
        return data;
    }

    public void setVersiondata(VersionDataModel data) {
        this.data = data;
    }
}
