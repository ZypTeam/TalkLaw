package com.chuxin.law.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liangjie on 2017/4/24.
 * Description:TODO
 */

public class AreaBean implements Serializable {
    private String id;
    private String name;
    private List<AreaBean> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaBean> getList() {
        return list;
    }

    public void setList(List<AreaBean> list) {
        this.list = list;
    }
}
