package com.chuxin.law.model;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/1/18
 * @describe 我的咨询 javabean
 */

public class MyConsultModel implements Serializable {

    /**
     * id : 22
     * userid : 64
     * touserid : 31
     * createtime : 2018-03-14 10:29
     * state : 咨询中
     * method : 0
     * prepayid : wx20180314102952ff2cfa5dad0099732043
     * order : 1497704102201803141029512
     * mp_order : 4200000096201803148408911329
     * session : 22
     * endtime : 1520998192
     * money : 0.01
     * user : {"userid":31,"name":"花语之晨","headimg":"http://img.law.wzgeek.com/7b97e9e5726c00.jpeg","intro":"","sex":1,"birthday":0,"province":0,"county":0,"city":0,"email":"","address":""}
     */

    private int id;
    private int userid;
    private int touserid;
    private String createtime;
    private String state;
    private int method;
    private String prepayid;
    private String order;
    private String mp_order;
    private String session;
    private int endtime;
    private String money;
    private UserModel user;

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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

}
