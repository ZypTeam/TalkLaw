package com.chuxin.law.ry.my.mymessage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.chuxin.law.R;
import com.google.gson.Gson;

import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * @author zhaoyapeng
 * @version create time:18/3/914:16
 * @Email zyp@jusfoun.com
 * @Description ${支付 }
 */
public class PayPlugin  implements IPluginModule {
    private Context context;
    private Conversation.ConversationType conversationType;
    private String targetId;

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.mipmap.logo);
    }

    @Override
    public String obtainTitle(Context context) {
        return "支付";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {

        context = fragment.getActivity();
        conversationType = rongExtension.getConversationType();
        targetId = rongExtension.getTargetId();

        PayMessage payMessage =  PayMessage.obtain("1000元");

        Log.e("tag","conversationType="+conversationType+" "+targetId);

        RongIM.getInstance().sendMessage(Message.obtain("65", Conversation.ConversationType.PRIVATE, payMessage),
                "11111", "11111", new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        Log.e("tag","RongIM-onAttached");
                    }

                    @Override
                    public void onSuccess(Message message) {

                        Log.e("tag","RongIM-onSuccess");
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        Log.e("tag","RongIM-onError="+new Gson().toJson(message)+" "+errorCode);
                    }
                });

    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
