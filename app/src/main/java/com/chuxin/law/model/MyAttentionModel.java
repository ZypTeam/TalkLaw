package com.chuxin.law.model;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe
 */

public class MyAttentionModel implements Serializable {


    /**
     * id : 2
     * name : 123
     * headimg : 123
     * level :
     * donenum : 100
     * praise : 100
     * win : 100
     */

    private String id;
    private String name;
    private String headimg;
    private String level;
    private int donenum;
    private int praise;
    private int win;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
