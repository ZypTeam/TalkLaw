package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;

/**
 * @author zhaoyapeng
 * @version create time:18/3/1916:10
 * @Email zyp@jusfoun.com
 * @Description ${保证金普通订单model}
 */
public class GuaranteeRequestModel extends BaseModel implements Serializable {


    public GuaranteeRequestItemModel data;

    public class GuaranteeRequestItemModel {
        public String state;
        public String order;

    }
}
