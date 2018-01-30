package com.chuxin.law.ui.util;

import com.chuxin.law.ui.fragment.HomeFragment;
import com.chuxin.law.ui.fragment.MyFragment;
import com.chuxin.law.ui.fragment.StatementFragment;
import com.chuxin.law.base.BaseTalkLawFragment;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午2:52
 * @Email zyp@jusfoun.com
 * @Description $ fragemnt 工具类
 */
public class HomeFragmentUtil {
    private static int TYPE_OPINION = 0;
    private static int TYPE_STATEMENT= 1;
    private static int TYPE_PERSONAL = 2;

    public static BaseTalkLawFragment getInstance(int index) {
        BaseTalkLawFragment fragment = null;
        if (index == TYPE_OPINION) {
            fragment = StatementFragment.getInstance();
        } else if (index == TYPE_STATEMENT) {
            fragment = HomeFragment.getInstance();


        } else if (index == TYPE_PERSONAL) {
            fragment = MyFragment.getInstance();
        }

        return fragment;
    }
}
