package com.chuxin.law.util;

import com.chuxin.law.ui.fragment.DateCalculatorFragment;
import com.chuxin.law.ui.fragment.DayCalculatorFragment;
import com.chuxin.law.base.BaseTalkLawFragment;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午2:52
 * @Email zyp@jusfoun.com
 * @Description $ fragemnt 工具类
 */
public class DateCalculatorUtil {
    private static int TYPE_DAY = 0;
    private static int TYPE_DATE =1;
    public static BaseTalkLawFragment getInstance(int index) {
        BaseTalkLawFragment fragment = null;
        if (index == TYPE_DAY) {
            fragment = DayCalculatorFragment.getInstance();
        } else if (index == TYPE_DATE) {
            fragment = DateCalculatorFragment.getInstance();
        }
        return fragment;
    }
}
