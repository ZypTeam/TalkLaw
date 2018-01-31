package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/1/3114:49
 * @Email zyp@jusfoun.com
 * @Description ${积分详情model}
 */
public class IntegralDetailDataModel extends BaseModel implements Serializable {


    public IntegralDetailListModel data;


    public class IntegralDetailListModel extends BaseModel implements Serializable {
        public List<IntegralDetailItemModel> list;
        public String pointTotle;

    }


    public class IntegralDetailItemModel extends BaseModel implements Serializable {
        public String id;
        public String userid;
        public String point;
        public String createtime;
        public String type;
        public String remarks;
    }
}
