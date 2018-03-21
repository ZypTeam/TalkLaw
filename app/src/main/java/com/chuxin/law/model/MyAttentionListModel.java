package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.util.List;

/**
 * @author wangcc
 * @date 2018/1/30
 * @describe
 */

public class MyAttentionListModel extends BaseModel {

    private List<UserModel> data;

    public List<UserModel> getData() {
        return data;
    }

    public void setData(List<UserModel> data) {
        this.data = data;
    }
}
