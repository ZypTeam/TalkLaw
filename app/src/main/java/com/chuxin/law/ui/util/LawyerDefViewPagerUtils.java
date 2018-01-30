package com.chuxin.law.ui.util;


import android.support.v4.app.Fragment;

import com.chuxin.law.ui.fragment.LawyerDefAudioFragment;
import com.chuxin.law.ui.fragment.LawyerDefImageFragment;
import com.chuxin.law.ui.fragment.LawyerDefVedioFragment;

/**
 * @author wangcc
 * @date 2018/1/21
 * @describe 律师详情底部viewpager utils
 */

public class LawyerDefViewPagerUtils {

    public static final int COUNT = 3;

    public static Fragment getFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment= LawyerDefAudioFragment.getInstance();
                break;
            case 1:
                fragment= LawyerDefImageFragment.getInstance();
                break;
            case 2:
                fragment= LawyerDefVedioFragment.getInstance();
                break;
            default:
        }

        return fragment;
    }

}
