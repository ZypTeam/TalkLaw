package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.util.List;

/**
 * @author wangcc
 * @date 2018/1/31
 * @describe
 */

public class CommentListModel extends BaseModel{
    private List<CommentModel> data;

    public List<CommentModel> getData() {
        return data;
    }

    public void setData(List<CommentModel> data) {
        this.data = data;
    }
}
