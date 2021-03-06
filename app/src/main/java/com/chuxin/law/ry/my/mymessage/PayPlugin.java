package com.chuxin.law.ry.my.mymessage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.ry.ui.fragment.ConversationFragmentEx;
import com.chuxin.law.ui.activity.GuaranteeRequestActivity;
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
    private String toUserId;

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.img_chat_bail);
    }

    @Override
    public String obtainTitle(Context context) {
        return "保证金";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {

        context = fragment.getActivity();
        conversationType = rongExtension.getConversationType();

        if(fragment instanceof ConversationFragmentEx){

            toUserId= ((ConversationFragmentEx) fragment).getTouserid();
            Log.e("tag","toUserId1=="+toUserId);
        }
        Log.e("tag","toUserId2=="+toUserId);

        targetId = rongExtension.getTargetId();
        Log.e("tag","targetId=="+targetId);





        Intent intent = new Intent(context, GuaranteeRequestActivity.class);
        intent.putExtra("targetId",targetId);
        intent.putExtra("toUserId",toUserId);
        context.startActivity(intent);

    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
