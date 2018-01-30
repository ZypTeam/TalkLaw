package com.chuxin.law.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chuxin.law.ui.util.LawyerDefViewPagerUtils;

/**
 * @author wangcc
 * @date 2018/1/21
 * @describe 律师 pager adapter
 */

public class LawyerDefPagerAdapter extends FragmentPagerAdapter {
    public LawyerDefPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return LawyerDefViewPagerUtils.getFragment(position);
    }

    @Override
    public int getCount() {
        return LawyerDefViewPagerUtils.COUNT;
    }
}
