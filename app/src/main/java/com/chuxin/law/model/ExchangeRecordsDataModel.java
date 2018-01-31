package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2814:31
 * @Email zyp@jusfoun.com
 * @Description ${兑换记录 model}
 */
public class ExchangeRecordsDataModel extends BaseModel implements Serializable {


    public List<ExchangeRecordsItemModel> data;
    public String id;
    public String userid;
    public String goodsid;
    public long createtime;
    public String img;


    public class ExchangeRecordsItemModel extends BaseModel implements Serializable{
        public String id;
        public String userid;
        public String goodsid;
        public long createtime;
        public String img;
        public IntegralModel.GoodsItemModel goods;

    }
}
