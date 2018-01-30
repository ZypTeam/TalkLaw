package cn.com.talklaw.model;

import com.chuxin.law.model.IntegralModel;
import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2917:06
 * @Email zyp@jusfoun.com
 * @Description ${所有商品 产品 item model}
 */
public class GoodsDataModel extends BaseModel implements Serializable {
    public List<IntegralModel.GoodsItemModel> data;
}
