package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

/**
 * @author wangcc
 * @date 2018/3/14
 * @describe 检查咨询 model
 */

public class CheckConsultModel extends BaseModel {

    private CheckConsultData data;

    public CheckConsultData getData() {
        return data;
    }

    public void setData(CheckConsultData data) {
        this.data = data;
    }

    public final class CheckConsultData{

        /**
         * id : 23
         * userid : 3
         * touserid : 31
         * createtime : 1521040055
         * state : 1
         * method : 0
         * prepayid : wx2018031423073579c55b7cc30980685530
         * order : 1497704102201803142307355
         * mp_order : 4200000086201803148842390795
         * session : 23
         * endtime : 1521043655
         * money : 0.01
         */

        private int id;
        private int userid;
        private int touserid;
        private int createtime;
        private int state;
        private int method;
        private String prepayid;
        private String order;
        private String mp_order;
        private String session;
        private int endtime;
        private String money;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getTouserid() {
            return touserid;
        }

        public void setTouserid(int touserid) {
            this.touserid = touserid;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getMethod() {
            return method;
        }

        public void setMethod(int method) {
            this.method = method;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getMp_order() {
            return mp_order;
        }

        public void setMp_order(String mp_order) {
            this.mp_order = mp_order;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }

        public int getEndtime() {
            return endtime;
        }

        public void setEndtime(int endtime) {
            this.endtime = endtime;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
