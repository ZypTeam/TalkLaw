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
        private String partnerid;
        private String prepayId;
        private String noncestr;
        private String timestamp;
        private String packagestr;
        private String sign;

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

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
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
                    +"\npartnerid=="+partnerid
                    +"\nprepayId=="+ prepayId
                    +"\nnoncestr=="+noncestr
                    +"\ntimestamp=="+timestamp
                    +"\npackagestr=="+packagestr
                    +"\nsign=="+sign
                    ;
        }
    }
}
