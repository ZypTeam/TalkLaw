package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

/**
 * @author wangcc
 * @date 2017/12/24
 * @describe 专区 产品分类 model
 */

public class ArrondiProductModel extends BaseModel {
    private int imageResId;
    private String img;
    private String name;
    private String title;
    private String id;

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
