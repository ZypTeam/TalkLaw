package com.chuxin.law.model;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/2/4
 * @describe
 */

public class AuthLawyerModel implements Serializable {
    private String starttime;
    private String endtime;
    private String law_firm;
    private String headimg;
    private String certificate;
    private String certificate_year;

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

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
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
}
