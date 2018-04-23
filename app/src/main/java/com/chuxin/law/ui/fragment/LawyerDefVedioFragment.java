package com.chuxin.law.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.audioplayer.AudioStopEvent;
import com.chuxin.law.audioplayer.receiver.AudioBroadcastReceiver;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.ui.widget.HtmlTextView;
import com.chuxin.law.util.ImageLoderUtil;
import com.chuxin.law.util.LawyerDefViewPagerUtils;
import com.chuxin.law.util.UIUtils;

import org.greenrobot.eventbus.EventBus;

import rx.functions.Action1;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import witmob.com.videolibrary.media.VideoPlayView;

import static com.chuxin.law.common.CommonConstant.AUDIO_PLAYER;
import static com.chuxin.law.common.CommonConstant.EVENT_BUY_LAWYER;

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
    protected HtmlTextView content;
    protected TextView dashang;
    private String mContent;
    private String url;
    private String imgUrl;
    private VideoPlayView videoPlayView;
    private String id;
    private LawyerProductModel.LawyerProductData data;
    private ImageView videoImg,play;

    public static LawyerDefVedioFragment getInstance(Bundle args) {
        LawyerDefVedioFragment fragment = new LawyerDefVedioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void refreshData() {
        ImageLoderUtil.loadNormalImg(mContext,videoImg,imgUrl,R.mipmap.icon_def_img);
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
        imgUrl=data.getArticle().getImg();
        //TODO 服务返回url不可用，暂时使用固定的  2018年02月09日11:15:36 by wang
//        url="http://flv2.bn.netease.com/tvmrepo/2016/4/G/O/EBKQOA8GO/SD/EBKQOA8GO-mobile.mp4";
    }

    @Override
    public void initView(View rootView) {
        video = (FrameLayout) rootView.findViewById(R.id.video);
        full = (ImageView) rootView.findViewById(R.id.full);
        time = (TextView) rootView.findViewById(R.id.time);
        timeAll = (TextView) rootView.findViewById(R.id.time_all);
        seek = (SeekBar) rootView.findViewById(R.id.seek);
        content = (HtmlTextView) rootView.findViewById(R.id.content);
        dashang = (TextView) rootView.findViewById(R.id.dashang);
        videoImg=rootView.findViewById(R.id.img_video);
        play=rootView.findViewById(R.id.play);


        videoPlayView=new VideoPlayView(mContext);
    }

    @Override
    public void initAction() {
        content.setHtmlFromString(mContent,false);
        dashang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.goGratuity(mContext,data);
            }
        });

        videoPlayView.setCompletionListener(new VideoPlayView.CompletionListener() {
            @Override
            public void completion(IMediaPlayer mp) {
                videoImg.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
                video.removeAllViews();
                videoPlayView.release();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data==null||data.getArticle()==null||data.getArticle().getIs_buy()==0){
                    showToast("请先购买");
                    return;
                }
                Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_PAUSEMUSIC);
                resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                mContext.sendBroadcast(resumeIntent);
                videoImg.setVisibility(View.GONE);
                play.setVisibility(View.GONE);
                video.removeAllViews();
                video.addView(videoPlayView);
                videoPlayView.start(url);
            }
        });

        rxManage.on(EVENT_BUY_LAWYER, new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (data!=null&&data.getArticle()!=null){
                    data.getArticle().setIs_buy(1);
                }
            }
        });

        rxManage.on(AUDIO_PLAYER, new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (videoPlayView.isPlay()){
                    videoPlayView.stop();
                    video.removeAllViews();
                    videoImg.setVisibility(View.VISIBLE);
                    play.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser){
            if (videoPlayView!=null){
                if (videoPlayView.isPlay()){
                    videoPlayView.stop();
                    video.removeAllViews();
                    videoImg.setVisibility(View.VISIBLE);
                    play.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoPlayView!=null){
            if (videoPlayView.isPlay()){
                videoPlayView.stop();
                video.removeAllViews();
                videoImg.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
            }
        }
    }
}
