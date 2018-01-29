package com.chuxin.law.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chuxin.law.ui.util.HomeFragmentUtil;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2115:44
 * @Email zyp@jusfoun.com
 * @Description ${首页fragment}
 */
public class HomeAdapter extends FragmentPagerAdapter {


    public HomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return HomeFragmentUtil.getInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
