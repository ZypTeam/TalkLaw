package com.chuxin.law.ry.my;

import com.chuxin.law.ry.my.mymessage.PayPlugin;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imlib.model.Conversation;

/**
 * @author zhaoyapeng
 * @version create time:18/3/810:00
 * @Email zyp@jusfoun.com
 * @Description ${聊天页面 底部功能选项 自定义 真费劲}
 */
public class MyExtensionModule extends DefaultExtensionModule {

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules =  new ArrayList<>();
        IPluginModule image = new ImagePlugin();
//        IPluginModule locationPlugin = new DefaultLocationPlugin();
        pluginModules.add(image);
//        pluginModules.add(locationPlugin);
        pluginModules.add(new PayPlugin());
        return pluginModules;
    }
}
