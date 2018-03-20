package com.chuxin.law.ui.viewholder;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/3/2017:22
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class AccountDetailModel extends BaseModel implements Serializable {

    public List<AccountItemModel> data;


    public class AccountItemModel extends BaseModel implements Serializable {

        public String id;
        public String userid;
        public String cardid;
        public String money;
        public String level;
        public String createtime;

    }
}
