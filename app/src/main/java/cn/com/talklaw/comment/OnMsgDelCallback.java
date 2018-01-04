package cn.com.talklaw.comment;

import cn.com.talklaw.model.MyMsgModel;

/**
 * @author wangcc
 * @date 2018/1/4
 * @describe 消息删除回调
 */

public interface OnMsgDelCallback {
    void del(MyMsgModel model, int position);
}
