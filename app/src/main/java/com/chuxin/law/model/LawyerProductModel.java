package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/1/30
 * @describe 律师详情
 */

public class LawyerProductModel extends BaseModel implements Serializable{
    private LawyerProductData data;

    public LawyerProductData getData() {
        return data;
    }

    public void setData(LawyerProductData data) {
        this.data = data;
    }

    public class LawyerProductData implements Serializable{
        private UserModel lawyer;
        private LawyerAudioModel article;

        public UserModel getLawyer() {
            return lawyer;
        }

        public void setLawyer(UserModel lawyer) {
            this.lawyer = lawyer;
        }

        public LawyerAudioModel getArticle() {
            return article;
        }

        public void setArticle(LawyerAudioModel article) {
            this.article = article;
        }
    }
}
