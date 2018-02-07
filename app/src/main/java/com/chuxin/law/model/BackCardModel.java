package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/2/720:56
 * @Email zyp@jusfoun.com
 * @Description ${银行卡 model}
 */
public class BackCardModel extends BaseModel implements Serializable {


    public List<BackCardItemModel> data;

    public class BackCardItemModel extends BaseModel implements Serializable{
        public String id;
        public String card;
        public String userid;
        public String name;
        public String phone;

        public boolean isSelect;
    }


}
