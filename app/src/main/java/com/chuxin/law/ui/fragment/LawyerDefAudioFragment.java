package com.chuxin.law.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.util.ImageLoderUtil;
import com.chuxin.law.util.LawyerDefViewPagerUtils;
import com.chuxin.law.util.UIUtils;
import com.chuxin.law.util.voice.VoiceHelper;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.task.WeakHandler;

import java.util.Observable;

import rx.Subscriber;
import rx.schedulers.Schedulers;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

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
    protected TextView timeAll, content;
    protected SeekBar seek;
    protected TextView dashang;
    private String mContent;
    private String url;
    private String id;
    private String imgUrl;
    private LawyerProductModel.LawyerProductData data;
    private VoiceHelper voiceHelper;

    private WeakHandler handler = new WeakHandler();

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            int progress = (int) ((voiceHelper.getPlayingCurrPostion()/1000f) /(voiceHelper.getLength()/1000f)*100);
            if (progress < 100) {
                time.setText(setTimeAll(voiceHelper.getPlayingCurrPostion()));
                seek.setProgress((int) progress);
                handler.postDelayed(task, 1000);
            } else {
                handler.removeCallbacks(task);
            }
        }
    };

    public static LawyerDefAudioFragment getInstance(Bundle args) {
        LawyerDefAudioFragment fragment = new LawyerDefAudioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void refreshData() {
        voiceHelper.initVoice(VoiceHelper.MUSIC_INDEX, url);
        ImageLoderUtil.loadCircleImage(mContext, imgAudio, imgUrl, R.mipmap.icon_head_def_cir);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_lawyer_def_aduio;
    }

    @Override
    public void initDatas() {
        data = (LawyerProductModel.LawyerProductData) getArguments().getSerializable(LawyerDefViewPagerUtils.DATA);
        if (data == null || data.getArticle() == null || data.getLawyer() == null) {
            return;
        }
        mContent = data.getArticle().getContent();
        url = data.getArticle().getMp3();
        id = data.getLawyer().getUserid();
        voiceHelper = new VoiceHelper();
        voiceHelper.initAudio();
        imgUrl = data.getArticle().getImg();
        Log.e("voiceurl", url);
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

        time.setText("00:00");

    }

    @Override
    public void initAction() {
        content.setText(UIUtils.getHtmlTxt(mContent));
        dashang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.goGratuity(mContext, data);
            }
        });
        imgAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voiceHelper.isPlay()) {
                    handler.removeCallbacks(task);
                    voiceHelper.pauseVoice();
                    play.setImageResource(R.mipmap.icon_lawyer_pause);
                } else {
                    handler.postDelayed(task,1000);
                    voiceHelper.startVoice();
                    play.setImageResource(R.mipmap.icon_lawyer_player);
                }
            }
        });

        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceHelper.seekVoice(10000);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceHelper.seekVoice(10000);
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceHelper.seekVoice(2000);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceHelper.seekVoice(2000);
            }
        });

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = (int) (progress * voiceHelper.getLength()
                        / seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (voiceHelper != null)
                    voiceHelper.seek(progress* voiceHelper.getLength()/100);
            }
        });

        voiceHelper.setListener(new VoiceHelper.OnPlayListener() {
            @Override
            public void onComplete() {
                handler.removeCallbacks(task);
            }

            @Override
            public void onError() {
                handler.removeCallbacks(task);
            }

            @Override
            public void prepared() {
                timeAll.setText(setTimeAll(voiceHelper.getLength()));
            }
        });

    }

    private String setTimeAll(long time) {
        time/=1000;
        if (time < 60) {
            if (time < 10) {
                return  "00:0" + time;
            }
            return  "00:" + time;
        } else {

            String time1 = time / 60 + "";
            if (time1.length() < 2) {
                time1 = "0" + time1;
            }

            String time2 = time % 60 + "";
            if (time2.length() < 2) {
                time2 = "0" + time2;
            }

            return time1 + ":" + time2;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (voiceHelper != null) {
                if (voiceHelper.isPlay()) {
                    handler.removeCallbacks(task);
                    voiceHelper.stopVoice();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (voiceHelper != null) {
            if (voiceHelper.isPlay()) {
                handler.removeCallbacks(task);
                voiceHelper.stopVoice();
            }
        }
    }
}
