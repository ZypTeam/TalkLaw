package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;

/**
 * @author zhaoyapeng
 * @version create time:18/4/1021:19
 * @Email zyp@jusfoun.com
 * @Description ${广告model}
 */
public class AdModel extends BaseModel implements Serializable{


    public AdDataModel data;

    public class AdDataModel extends BaseModel implements Serializable {
        public String url;
        public String img;
    }
}
