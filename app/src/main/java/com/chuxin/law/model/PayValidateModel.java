package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/3/6
 * @describe
 */

public class PayValidateModel extends BaseModel {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public final class Data implements Serializable{
        private int state;
        private String partnerid;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }
    }
}
