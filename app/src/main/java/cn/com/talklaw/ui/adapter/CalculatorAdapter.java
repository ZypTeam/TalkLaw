package cn.com.talklaw.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.com.talklaw.ui.util.DateCalculatorUtil;
import cn.com.talklaw.ui.util.HomeFragmentUtil;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2115:44
 * @Email zyp@jusfoun.com
 * @Description ${首页fragment}
 */
public class CalculatorAdapter extends FragmentPagerAdapter {


    public CalculatorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DateCalculatorUtil.getInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
