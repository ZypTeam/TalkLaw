package cn.com.talklaw.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2509:34
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class ProductListModel extends BaseModel {
    public ProductDataModel data;

    public class ProductDataModel {
        public List<ProductModel> article;
        public List<CarouseModel> carouse;
    }
}
