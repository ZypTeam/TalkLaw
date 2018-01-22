package cn.com.talklaw.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import cn.com.talklaw.ui.util.MyConsultPagerUtils;

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
