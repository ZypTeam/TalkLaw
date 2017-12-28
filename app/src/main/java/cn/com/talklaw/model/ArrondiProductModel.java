package cn.com.talklaw.model;

import com.jusfoun.baselibrary.base.BaseModel;

/**
 * @author wangcc
 * @date 2017/12/24
 * @describe 专区 产品分类 model
 */

public class ArrondiProductModel extends BaseModel {
    private int imageResId;
    private String imgeUrl;
    private String name;
    private String title;

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getImgeUrl() {
        return imgeUrl;
    }

    public void setImgeUrl(String imgeUrl) {
        this.imgeUrl = imgeUrl;
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
}
