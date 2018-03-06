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
        private OrderPayModel.WxPayOrderDic wxorder;

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
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
    }
}
