package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcc
 * @date 2018/1/30
 * @describe 专区model
 */

public class ArrondiModel extends BaseModel {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean implements Serializable{
        private List<ProductModel> article;
        private List<ProductModel> carouse;
        private String price;
        private String oldPrice;
        private List<ArrondiProductModel> catList;

        public List<ProductModel> getArticle() {
            return article;
        }

        public void setArticle(List<ProductModel> article) {
            this.article = article;
        }

        public List<ProductModel> getCarouse() {
            return carouse;
        }

        public void setCarouse(List<ProductModel> carouse) {
            this.carouse = carouse;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public List<ArrondiProductModel> getCatList() {
            return catList;
        }

        public void setCatList(List<ArrondiProductModel> catList) {
            this.catList = catList;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
        }
    }
}
