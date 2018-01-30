package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.util.List;

/**
 * @author wangcc
 * @date 2018/1/30
 * @describe 产品列表
 */

public class ProductsModel extends BaseModel {
    private List<ProductModel> data;

    public List<ProductModel> getData() {
        return data;
    }

    public void setData(List<ProductModel> data) {
        this.data = data;
    }
}
