package com.chuxin.law.ui.util;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.chuxin.law.model.LawyerAudioModel;
import com.chuxin.law.model.LawyerProductModel;
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
    public static final String CONTENT="content";
    public static final String IMAGE="image";
    public static final String URL="url";
    public static final String ID="id";
    public static Fragment getFragment(int position, LawyerAudioModel model) {
        Fragment fragment = null;
        Bundle bundle=new Bundle();
        bundle.putString(CONTENT,model.getContent());
        bundle.putString(IMAGE,model.getImg());
        bundle.putString(ID,model.getId());
        switch (position) {
            case 0:
                bundle.putString(URL,model.getMp3());
                fragment= LawyerDefAudioFragment.getInstance(bundle);
                break;
            case 1:
                fragment= LawyerDefImageFragment.getInstance(bundle);
                break;
            case 2:
                bundle.putString(URL,model.getMp4());
                fragment= LawyerDefVedioFragment.getInstance(bundle);
                break;
            default:
        }

        return fragment;
    }

}
