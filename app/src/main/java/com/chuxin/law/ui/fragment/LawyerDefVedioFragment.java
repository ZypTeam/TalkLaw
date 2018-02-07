package com.chuxin.law.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.ui.util.LawyerDefViewPagerUtils;
import com.chuxin.law.ui.util.UIUtils;

import witmob.com.videolibrary.media.VideoPlayView;

/**
 * @author wangcc
 * @date 2018/1/21
 * @describe 律师 视频 fragemnt
 */

public class LawyerDefVedioFragment extends BaseTalkLawFragment {

    protected FrameLayout video;
    protected ImageView full;
    protected TextView time;
    protected TextView timeAll;
    protected SeekBar seek;
    protected TextView content;
    protected TextView dashang;
    private String mContent;
    private String url;
    private String imgUrl;
    private VideoPlayView videoPlayView;
    private String id;
    private LawyerProductModel.LawyerProductData data;

    public static LawyerDefVedioFragment getInstance(Bundle args) {
        LawyerDefVedioFragment fragment = new LawyerDefVedioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void refreshData() {
        videoPlayView.setUri(url);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_lawyer_def_video;
    }

    @Override
    public void initDatas() {
        data= (LawyerProductModel.LawyerProductData) getArguments().getSerializable(LawyerDefViewPagerUtils.DATA);
        if (data==null||data.getArticle()==null||data.getLawyer()==null){
            return;
        }
        mContent = data.getArticle().getContent();
        url = data.getArticle().getMp4();
        id=data.getLawyer().getUserid();
    }

    @Override
    public void initView(View rootView) {
        video = (FrameLayout) rootView.findViewById(R.id.video);
        full = (ImageView) rootView.findViewById(R.id.full);
        time = (TextView) rootView.findViewById(R.id.time);
        timeAll = (TextView) rootView.findViewById(R.id.time_all);
        seek = (SeekBar) rootView.findViewById(R.id.seek);
        content = (TextView) rootView.findViewById(R.id.content);
        dashang = (TextView) rootView.findViewById(R.id.dashang);

        videoPlayView=new VideoPlayView(mContext);
        video.addView(videoPlayView);

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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        video.removeAllViews();
    }
}
