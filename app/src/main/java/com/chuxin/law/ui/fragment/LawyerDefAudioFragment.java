package com.chuxin.law.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.ui.util.ImageLoderUtil;
import com.chuxin.law.ui.util.LawyerDefViewPagerUtils;
import com.chuxin.law.ui.util.UIUtils;

/**
 * @author wangcc
 * @date 2018/1/21
 * @describe 律师 音频 fragemnt
 */

public class LawyerDefAudioFragment extends BaseTalkLawFragment {

    protected ImageView imgAudio;
    protected ImageView play;
    protected ImageView last;
    protected ImageView rewind;
    protected ImageView next;
    protected ImageView forward;
    protected TextView time;
    protected TextView timeAll,content;
    protected SeekBar seek;
    protected TextView dashang;
    private String mContent;
    private String url;
    private String id;
    private LawyerProductModel.LawyerProductData data;
    public static LawyerDefAudioFragment getInstance(Bundle args) {
        LawyerDefAudioFragment fragment = new LawyerDefAudioFragment();
        fragment.setArguments(args);
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
        data= (LawyerProductModel.LawyerProductData) getArguments().getSerializable(LawyerDefViewPagerUtils.DATA);
        if (data==null||data.getArticle()==null||data.getLawyer()==null){
            return;
        }
        mContent = data.getArticle().getContent();
        url = data.getArticle().getMp3();
        id=data.getLawyer().getUserid();
    }

    @Override
    public void initView(View rootView) {
        imgAudio = (ImageView) rootView.findViewById(R.id.img_audio);
        play = (ImageView) rootView.findViewById(R.id.play);
        last = (ImageView) rootView.findViewById(R.id.last);
        rewind = (ImageView) rootView.findViewById(R.id.rewind);
        next = (ImageView) rootView.findViewById(R.id.next);
        forward = (ImageView) rootView.findViewById(R.id.forward);
        time = (TextView) rootView.findViewById(R.id.time);
        timeAll = (TextView) rootView.findViewById(R.id.time_all);
        seek = (SeekBar) rootView.findViewById(R.id.seek);
        content = (TextView) rootView.findViewById(R.id.content);
        dashang = (TextView) rootView.findViewById(R.id.dashang);

    }

    @Override
    public void initAction() {
        content.setText(UIUtils.getHtmlTxt(mContent));
        dashang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.goGratuity(mContext,data);
            }
        });
        ImageLoderUtil.loadNormalImg(mContext,imgAudio,data.getLawyer().getHeadimg());
    }
}
