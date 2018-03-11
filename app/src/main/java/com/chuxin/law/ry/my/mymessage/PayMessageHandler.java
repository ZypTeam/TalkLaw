package com.chuxin.law.ry.my.mymessage;

import android.content.Context;

import io.rong.imlib.model.Message;
import io.rong.message.MessageHandler;

/**
 * @author zhaoyapeng
 * @version create time:18/3/916:19
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class PayMessageHandler extends MessageHandler<PayMessage> {
    public PayMessageHandler(Context context) {
        super(context);
    }

    @Override
    public void decodeMessage(Message message, PayMessage payMessage) {

    }

    @Override
    public void encodeMessage(Message message) {

    }

}
