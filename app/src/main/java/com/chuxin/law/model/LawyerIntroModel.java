package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcc
 * @date 2018/1/31
 * @describe 律师介绍model
 */

public class LawyerIntroModel extends BaseModel {

    private LawyerIntroData data;

    public LawyerIntroData getData() {
        return data;
    }

    public void setData(LawyerIntroData data) {
        this.data = data;
    }

    public class LawyerIntroData implements Serializable{
        private UserModel law;
        private String effect;
        private int is_follow;
        private List<ProductModel> list;
        public OrderModel order;


        public UserModel getLaw() {
            return law;
        }

        public void setLaw(UserModel law) {
            this.law = law;
        }

        public List<ProductModel> getList() {
            return list;
        }

        public void setList(List<ProductModel> list) {
            this.list = list;
        }

        public String getEffect() {
            return effect;
        }

        public void setEffect(String effect) {
            this.effect = effect;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }
    }
}
