package cn.com.talklaw.ui.util;


import android.support.v4.app.Fragment;

import cn.com.talklaw.ui.fragment.LawyerDefAudioFragment;
import cn.com.talklaw.ui.fragment.LawyerDefImageFragment;
import cn.com.talklaw.ui.fragment.LawyerDefVedioFragment;

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
