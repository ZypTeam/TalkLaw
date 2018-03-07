package com.chuxin.law.model;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/2/4
 * @describe
 */

public class OrderPayModel {

    private String tradenum;
    private String alipayorderStr;
    private int orderFailure;
    private WxPayOrderDic wxpayorderDic;

    public String getTradenum() {
        return tradenum;
    }

    public void setTradenum(String tradenum) {
        this.tradenum = tradenum;
    }

    public String getAlipayorderStr() {
        return alipayorderStr;
    }

    public void setAlipayorderStr(String alipayorderStr) {
        this.alipayorderStr = alipayorderStr;
    }

    public WxPayOrderDic getWxpayorderDic() {
        return wxpayorderDic;
    }

    public void setWxpayorderDic(WxPayOrderDic wxpayorderDic) {
        this.wxpayorderDic = wxpayorderDic;
    }

    public int getOrderFailure() {
        return orderFailure;
    }

    public void setOrderFailure(int orderFailure) {
        this.orderFailure = orderFailure;
    }

    public class WxPayOrderDic implements Serializable {
        private String appid;
        private String timestamp;
        private String packagestr;
        private String sign;
        /**
         * return_code : SUCCESS
         * return_msg : OK
         * mch_id : 1497704102
         * nonce_str : 3eha8F5q80LzXqYK
         * result_code : SUCCESS
         * prepay_id : wx201803072210572cb8d5c1d30068797230
         * trade_type : APP
         */

        private String return_code;
        private String return_msg;
        private String mch_id;
        private String nonce_str;
        private String result_code;
        private String prepay_id;
        private String trade_type;


        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getPackagestr() {
            return packagestr;
        }

        public void setPackagestr(String packagestr) {
            this.packagestr = packagestr;
        }


        @Override
        public String toString() {
            return "appid"+appid
                    +"\nprepayId=="+ prepay_id
                    +"\nnoncestr=="+nonce_str
                    +"\ntimestamp=="+timestamp
                    +"\npackagestr=="+packagestr
                    +"\nsign=="+sign
                    ;
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

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
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
