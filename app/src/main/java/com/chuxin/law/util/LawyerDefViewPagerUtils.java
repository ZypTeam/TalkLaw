package com.chuxin.law.util;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.chuxin.law.model.LawyerAudioModel;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.ui.fragment.LawyerDefAudioFragment;
import com.chuxin.law.ui.fragment.LawyerDefImageFragment;
import com.chuxin.law.ui.fragment.LawyerDefVedioFragment;
import com.jusfoun.baselibrary.Util.StringUtil;

/**
 * @author wangcc
 * @date 2018/1/21
 * @describe 律师详情底部viewpager utils
 */

public class LawyerDefViewPagerUtils {

    public static final int COUNT = 3;
    public static final String DATA="data";
    public static Fragment getFragment(int position, LawyerProductModel.LawyerProductData data) {

        Fragment fragment = null;
        Bundle bundle=new Bundle();
        bundle.putSerializable(DATA,data);
        if (data==null||data.getArticle()==null){
            return null;
        }
        LawyerAudioModel model=data.getArticle();
        if (!StringUtil.isEmpty(model.getMp3())
                &&!StringUtil.isEmpty(model.getMp4())){
            switch (position) {
                case 0:
                    fragment= LawyerDefAudioFragment.getInstance(bundle);
                    break;
                case 1:
                    fragment= LawyerDefImageFragment.getInstance(bundle);
                    break;
                case 2:
                    fragment= LawyerDefVedioFragment.getInstance(bundle);
                    break;
                default:
            }
            return fragment;
        }

        if (StringUtil.isEmpty(model.getMp4())
                &&StringUtil.isEmpty(model.getMp3())){
            fragment= LawyerDefImageFragment.getInstance(bundle);
            return fragment;
        }

        if (StringUtil.isEmpty(model.getMp3())){
            switch (position) {
                case 0:
                    fragment= LawyerDefImageFragment.getInstance(bundle);
                    break;
                case 1:
                    fragment= LawyerDefVedioFragment.getInstance(bundle);
                    break;
                default:
            }
            return fragment;
        }

        if (StringUtil.isEmpty(model.getMp4())){
            switch (position) {
                case 0:
                    fragment= LawyerDefAudioFragment.getInstance(bundle);
                    break;
                case 1:
                    fragment= LawyerDefImageFragment.getInstance(bundle);
                    break;
                default:
            }
            return fragment;
        }

        return null;
    }

    public static int getCount(LawyerProductModel.LawyerProductData data){
        if (data==null||data.getArticle()==null){
            return 0;
        }
        LawyerAudioModel model=data.getArticle();
        if (!StringUtil.isEmpty(model.getMp3())
                &&!StringUtil.isEmpty(model.getMp4())){
            return 3;
        }

        if (StringUtil.isEmpty(model.getMp4())
                &&StringUtil.isEmpty(model.getMp3())){
            return 1;
        }

        return 2;
    }

    public static int getImagePosition(LawyerProductModel.LawyerProductData data){
        if (data==null||data.getArticle()==null){
            return 0;
        }
        LawyerAudioModel model=data.getArticle();
        if (!StringUtil.isEmpty(model.getMp3())
                &&!StringUtil.isEmpty(model.getMp4())){
            return 1;
        }

        if (StringUtil.isEmpty(model.getMp4())
                &&StringUtil.isEmpty(model.getMp3())){
            return 0;
        }

        if (StringUtil.isEmpty(model.getMp4())){
            return 1;
        }

        if (StringUtil.isEmpty(model.getMp3())){
            return 0;
        }

        return 0;
    }

    public static int getVideoPosition(LawyerProductModel.LawyerProductData data){
        if (data==null||data.getArticle()==null){
            return 0;
        }
        LawyerAudioModel model=data.getArticle();
        if (!StringUtil.isEmpty(model.getMp3())
                &&!StringUtil.isEmpty(model.getMp4())){
            return 2;
        }

        if (StringUtil.isEmpty(model.getMp3())){
            return 1;
        }

        return 0;
    }

}
