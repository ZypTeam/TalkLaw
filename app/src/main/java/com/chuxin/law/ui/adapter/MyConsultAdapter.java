package com.chuxin.law.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chuxin.law.ui.util.MyConsultPagerUtils;

/**
 * @author wangcc
 * @date 2018/1/18
 * @describe
 */

public class MyConsultAdapter extends FragmentPagerAdapter {

    public MyConsultAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return MyConsultPagerUtils.COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return MyConsultPagerUtils.getFragment(position);
    }
}
