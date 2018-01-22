package cn.com.talklaw.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.util.List;

/**
 * @author wangcc
 * @date 2018/1/17
 * @describe 消息列表 javabean
 */

public class MyMsgListModel extends BaseModel {
    private List<MyMsgModel> data;

    public List<MyMsgModel> getData() {
        return data;
    }

    public void setData(List<MyMsgModel> data) {
        this.data = data;
    }
}
