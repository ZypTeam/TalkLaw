package cn.com.talklaw.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2317:24
 * @Email zyp@jusfoun.com
 * @Description ${说法首页model}
 */
public class StatementListModel extends BaseModel {

    public StatementDataModel data;

    public class StatementDataModel extends BaseModel {
        public List<ProductItemModel> hot;
        public List<ProductItemModel> need;
        public List<ProductItemModel> free;
        public List<CarouseModel> carouse;
        public long freetime;
    }
}
