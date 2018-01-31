package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

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

    public class LawyerIntroData{
        private UserModel law;
        private List<ProductModel> list;

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
    }
}
