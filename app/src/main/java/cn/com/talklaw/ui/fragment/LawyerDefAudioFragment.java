package cn.com.talklaw.ui.fragment;

import android.view.View;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.base.BaseTalkLawFragment;

/**
 * @author wangcc
 * @date 2018/1/21
 * @describe 律师 音频 fragemnt
 */

public class LawyerDefAudioFragment extends BaseTalkLawFragment {

    public static LawyerDefAudioFragment getInstance() {
        LawyerDefAudioFragment fragment = new LawyerDefAudioFragment();
        return fragment;
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_lawyer_def_aduio;
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
