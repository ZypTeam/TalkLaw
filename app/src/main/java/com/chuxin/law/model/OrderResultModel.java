package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

/**
 * @author wangcc
 * @date 2018/3/6
 * @describe
 */

public class OrderResultModel extends BaseModel {

    private Item data;

    public Item getData() {
        return data;
    }

    public void setData(Item data) {
        this.data = data;
    }

    public final class Item{
        private Order order;
        private String aporder;
        private OrderPayModel.WxPayOrderDic wxorder;

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public String getAporder() {
            return aporder;
        }

        public void setAporder(String aporder) {
            this.aporder = aporder;
        }

        public OrderPayModel.WxPayOrderDic getWxorder() {
            return wxorder;
        }

        public void setWxorder(OrderPayModel.WxPayOrderDic wxorder) {
            this.wxorder = wxorder;
        }
    }

    public final class Order{
        private int state;
        private String partnerid;
        private String return_code;
        private String return_msg;
        private String aped;
        private String mch_id;
        private String nonce_str;
        private String prepay_id;
        private String trade_type;
        /**
         * method : 1
         * money : 0.01
         * userid : 4
         * artid : 10
         * type : 1
         * prepayid : wx20180320204727dd592fde800445042453
         * order : 1497704102201803202047271
         * createtime : 1521550047
         * id : 140
         */

        private String method;
        private String money;
        private int userid;
        private String artid;
        private String type;
        private String prepayid;
        private String order;
        private int createtime;
        private int id;


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

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getAped() {
            return aped;
        }

        public void setAped(String aped) {
            this.aped = aped;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getArtid() {
            return artid;
        }

        public void setArtid(String artid) {
            this.artid = artid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
