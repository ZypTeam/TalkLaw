package com.chuxin.law.model;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/2/4
 * @describe
 */

public class LawyerModel implements Serializable {


    /**
     * userid : 2
     * starttime : 1544544000
     * endtime : 1987344000
     * law_firm : 123123
     * createtime : 1514135815
     * certificate : b8d477cdbad1dcc606393ae2f34a1406.jpg
     * certificate_year : 3985804f9c4585161bacbdf1f4643a9d.jpg
     * price : 39.00
     * level : 签约级
     * state : 0
     * intro :
     * name : 张三
     * headimg :
     * donenum : 100
     * praise : 100
     * win : 100
     */

    private int userid;
    private int starttime;
    private int endtime;
    private String law_firm;
    private int createtime;
    private String certificate;
    private String certificate_year;
    private String price;
    private String level;
    private int state;
    private String intro;
    private String name;
    private String headimg;
    private int donenum;
    private int praise;
    private int win;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public int getEndtime() {
        return endtime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    public String getLaw_firm() {
        return law_firm;
    }

    public void setLaw_firm(String law_firm) {
        this.law_firm = law_firm;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCertificate_year() {
        return certificate_year;
    }

    public void setCertificate_year(String certificate_year) {
        this.certificate_year = certificate_year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public int getDonenum() {
        return donenum;
    }

    public void setDonenum(int donenum) {
        this.donenum = donenum;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }
}
