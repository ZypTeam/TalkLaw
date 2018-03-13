package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/2/823:54
 * @Email zyp@jusfoun.com
 * @Description ${热门企业 列表data}
 */
public class HotListData extends BaseModel implements Serializable {
    public List<ProductItemModel> data;
    public int total;
}
