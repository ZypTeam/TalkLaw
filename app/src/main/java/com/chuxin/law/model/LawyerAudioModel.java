package com.chuxin.law.model;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/1/31
 * @describe 律师音视频model
 */

public class LawyerAudioModel implements Serializable {


    /**
     * id : 6
     * point : 1
     * price : 1.00
     * createtime : 2018-01-09
     * buynum : 123
     * title : 测试
     * content : 测试
     * mp3 : http://api.law.wzgeek.com/test.mp3
     * mp4 : http://api.law.wzgeek.com/test.mp4
     * img : http://api.law.wzgeek.com/test.jpg
     * commment_num : 0
     * like_num : 0
     * is_like : 1
     * is_colle : 1
     * url : http://law.wzgeek.com/article/view?id=6
     */

    private String id;
    private int point;
    private String price;
    private String createtime;
    private int buynum;
    private String title;
    private String content;
    private String mp3;
    private String mp4;
    private String img;
    private String commment_num;
    private String like_num;
    private int is_like;
    private int is_colle;
    private int is_buy;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCommment_num() {
        return commment_num;
    }

    public void setCommment_num(String commment_num) {
        this.commment_num = commment_num;
    }

    public String getLike_num() {
        return like_num;
    }

    public void setLike_num(String like_num) {
        this.like_num = like_num;
    }

    public int getIs_like() {
        return is_like;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    public int getIs_colle() {
        return is_colle;
    }

    public void setIs_colle(int is_colle) {
        this.is_colle = is_colle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(int is_buy) {
        this.is_buy = is_buy;
    }
}
