package com.chuxin.law.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcc
 * @date 2018/3/7
 * @describe
 */

public class HotKeyListModel extends BaseModel {

    private List<HotKeyModel> data;

    public List<HotKeyModel> getData() {
        return data;
    }

    public void setData(List<HotKeyModel> data) {
        this.data = data;
    }

    public final class HotKeyModel implements Serializable{
        private String id;
        private String keyword;
        private String num;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
