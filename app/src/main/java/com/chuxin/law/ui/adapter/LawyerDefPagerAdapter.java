package com.chuxin.law.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chuxin.law.model.LawyerAudioModel;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.ui.util.LawyerDefViewPagerUtils;

/**
 * @author wangcc
 * @date 2018/1/21
 * @describe 律师 pager adapter
 */

public class LawyerDefPagerAdapter extends FragmentPagerAdapter {
    private LawyerProductModel.LawyerProductData data;
    public LawyerDefPagerAdapter(FragmentManager fm, LawyerProductModel.LawyerProductData data) {
        super(fm);
        this.data=data;
    }

    @Override
    public Fragment getItem(int position) {
        return LawyerDefViewPagerUtils.getFragment(position,data);
    }

    @Override
    public int getCount() {
        return LawyerDefViewPagerUtils.COUNT;
    }

}
