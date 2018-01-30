package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.util.List;

/**
 * @author wangcc
 * @date 2018/1/30
 * @describe
 */

public class MyAttentionListModel extends BaseModel {

    private List<MyAttentionModel> data;

    public List<MyAttentionModel> getData() {
        return data;
    }

    public void setData(List<MyAttentionModel> data) {
        this.data = data;
    }
}
