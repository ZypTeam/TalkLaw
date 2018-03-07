package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2017/12/18
 * @describe 用户信息
 */

public class UserModel extends BaseModel implements Serializable {

    /**
     * id : 2
     * type : 1
     * level : 0
     * phone : 15811078604
     * createtime : 1513472359
     * userid : 2
     * name : 123
     * headimg : 123
     * intro : 123
     * sex : 1
     * birthday : 12312313
     * province : 11
     * county : 110101
     * city : 1101
     * email : 123123
     * address : 12312312312
     * hx_username :
     * accessToken :
     * auth_key :
     * appToken : d1b595df8dcb59a907c0a213610a03d4
     */

    private String id;
    private String userid;

    private int type;
    private String level;
    private String phone;
    private String createtime;
    private String name;
    private String headimg;
    private String intro;
    private String starttime;
    private String endtime;
    private String law_firm;
    private String certificate;
    private String certificate_year;
    private String price;
    private int sex;
    private int state;
    private String birthday;
    private String province;
    private String county;
    private String city;
    private String email;
    private String address;
    private String hx_username;
    private String accessToken;
    private String auth_key;
    private String appToken;
    private String praise;
    private String win;
    public String rToken;

    private String donenum;
    /**
     * id : 5
     * createtime : 1515429997
     * userid : 5
     * birthday : 0
     * province : 0
     * county : 0
     * city : 0
     * law : []
     * points :
     * money :
     * follow : 0
     * qrcode :
     */

    private String points;
    private String money;
    private String follow;
    private String qrcode;
    private UserModel law;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHx_username() {
        return hx_username;
    }

    public void setHx_username(String hx_username) {
        this.hx_username = hx_username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public String getDonenum() {
        return donenum;
    }

    public void setDonenum(String donenum) {
        this.donenum = donenum;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getLaw_firm() {
        return law_firm;
    }

    public void setLaw_firm(String law_firm) {
        this.law_firm = law_firm;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public UserModel getLaw() {
        return law;
    }

    public void setLaw(UserModel law) {
        this.law = law;
    }
}
