package cn.com.talklaw.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2611:18
 * @Email zyp@jusfoun.com
 * @Description ${案件类型 model}
 */
public class CaseTypeModel extends BaseModel implements Serializable{

    public List<CaseTypeItemModel> list;

    public class CaseTypeItemModel extends BaseModel implements Serializable{
        public String title;
        public int id;
        public int type;
    }
}
