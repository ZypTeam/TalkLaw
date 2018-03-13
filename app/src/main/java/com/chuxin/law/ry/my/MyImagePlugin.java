package com.chuxin.law.ry.my;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.chuxin.law.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.image.PictureSelectorActivity;
import io.rong.imkit.utilities.PermissionCheckUtil;
import io.rong.imlib.model.Conversation;

/**
 * @author zhaoyapeng
 * @version create time:18/3/1315:45
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class MyImagePlugin implements IPluginModule {
    Conversation.ConversationType conversationType;
    String targetId;

    public MyImagePlugin() {
    }

    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.img_chat_phone);
    }

    public String obtainTitle(Context context) {
        return context.getString(io.rong.imkit.R.string.rc_plugin_image);
    }

    public void onClick(Fragment currentFragment, RongExtension extension) {
        String[] permissions = new String[]{"android.permission.READ_EXTERNAL_STORAGE"};
        if(PermissionCheckUtil.requestPermissions(currentFragment, permissions)) {
            this.conversationType = extension.getConversationType();
            this.targetId = extension.getTargetId();
            Intent intent = new Intent(currentFragment.getActivity(), PictureSelectorActivity.class);
            extension.startActivityForPluginResult(intent, 23, this);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
