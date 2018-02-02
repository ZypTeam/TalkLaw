package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/2/218:28
 * @Email zyp@jusfoun.com
 * @Description ${收货地址model}
 */
public class ShippingAddressModel extends BaseModel implements Serializable {

    public List<ShippingAddressItemModel> list;

    public class ShippingAddressItemModel extends BaseModel implements Serializable {
        public String name;
        public String sex;
        public String phone;
        public String city;
        public String area;
        public String address;
        public int id = -1;
        public boolean isSelect = false;
    }
}
