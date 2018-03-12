package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.util.List;

/**
 * @author wangcc
 * @date 2018/1/18
 * @describe 我的咨询 list javabean
 */

public class MyConsultListModel extends BaseModel{
    private List<MyConsultModel> data;

    public List<MyConsultModel> getData() {
        return data;
    }

    public void setData(List<MyConsultModel> data) {
        this.data = data;
    }
}
