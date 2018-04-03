package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2517:21
 * @Email zyp@jusfoun.com
 * @Description ${积分首页model}
 */
public class IntegralModel extends BaseModel implements Serializable {
    public IntegralData data;
    public class IntegralData extends BaseModel implements Serializable{
        public List<GoodsItemModel> goods;
        public List<CarouseModel> carouse;
        public List<CatItemModel> cat;
    }

    public class GoodsItemModel extends BaseModel implements Serializable {
        public String id;
        public String title;
        public String content;
        public String cat;
        public String createtime;
        public String point;
        public String intro;
        public String img;
        public String url;
        public String num;
    }

    public class CatItemModel extends BaseModel implements Serializable {
        public String id;
        public String name;
        public String img;
    }
}
