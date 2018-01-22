package cn.com.talklaw.ui.fragment;

import android.view.View;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawFragment;

/**
 * @author wangcc
 * @date 2018/1/21
 * @describe 律师 视频 fragemnt
 */

public class LawyerDefVedioFragment extends BaseTalkLawFragment{

    public static LawyerDefVedioFragment getInstance() {
        LawyerDefVedioFragment fragment = new LawyerDefVedioFragment();
        return fragment;
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_lawyer_def_video;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initAction() {

    }
}
