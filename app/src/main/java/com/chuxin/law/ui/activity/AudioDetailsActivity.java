package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.common.CommonLogic;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.model.ShareModel;
import com.chuxin.law.ui.dialog.ShareDialog;
import com.chuxin.law.util.ImageLoderUtil;
import com.chuxin.law.util.UIUtils;
import com.chuxin.law.util.voice.VoiceHelper;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.task.WeakHandler;

import java.util.HashMap;

import rx.functions.Action1;

import static com.chuxin.law.ui.activity.CommentListActivity.COMMENT_COUNT;

/**
 * @author wangcc
 * @date 2017/12/14
 * @describe
 */

public class AudioDetailsActivity extends BaseTalkLawActivity {
    protected TextView lawyerName;
    protected ImageView back;
    protected ImageView share;
    protected ImageView theme;
    protected TextView themeDes;
    protected TextView time;
    protected TextView timeAll;
    protected SeekBar seek;
    protected ImageView play;
    protected ImageView last;
    protected ImageView rewind;
    protected ImageView next;
    protected ImageView forward;
    protected ImageView download;
    protected ImageView like;
    protected ImageView comment;
    private LawyerProductModel.LawyerProductData data;
    private VoiceHelper voiceHelper = new VoiceHelper();
    private String url;
    private String imageUrl;
    private ShareDialog shareDialog;

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


    @Override
    public int getLayoutResId() {
        return R.layout.activity_details_audio;
    }

    @Override
    public void initDatas() {
        setStatusBarLight(false);
        data = CommonLogic.getInstance().getLawyerProductData();
        if (data.getArticle()!=null) {
            url = data.getArticle().getMp3();
            imageUrl=data.getArticle().getImg();
        }
        voiceHelper.initAudio();
        shareDialog=new ShareDialog(this);
    }

    @Override
    public void initView() {
        lawyerName = (TextView) findViewById(R.id.lawyer_name);
        back = (ImageView) findViewById(R.id.back);
        share = (ImageView) findViewById(R.id.share);
        theme = (ImageView) findViewById(R.id.theme);
        themeDes = (TextView) findViewById(R.id.theme_des);
        time = (TextView) findViewById(R.id.time);
        timeAll = (TextView) findViewById(R.id.time_all);
        seek = (SeekBar) findViewById(R.id.seek);
        play = (ImageView) findViewById(R.id.play);
        last = (ImageView) findViewById(R.id.last);
        rewind = (ImageView) findViewById(R.id.rewind);
        next = (ImageView) findViewById(R.id.next);
        forward = (ImageView) findViewById(R.id.forward);
        download = (ImageView) findViewById(R.id.download);
        like = (ImageView) findViewById(R.id.like);
        comment = (ImageView) findViewById(R.id.comment);

    }

    @Override
    public void initAction() {
        time.setText("00:00");

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voiceHelper.isPlay() &&StringUtil.equals(url,voiceHelper.getPlayingVoicePath())) {
                    handler.removeCallbacks(task);
                    voiceHelper.pauseVoice();
                    play.setImageResource(R.mipmap.icon_audio_player);
                }else if (voiceHelper.isPause()&&StringUtil.equals(url,voiceHelper.getPlayingVoicePath())){
                    handler.postDelayed(task,1000);
                    voiceHelper.startVoice();
                    play.setImageResource(R.mipmap.icon_audio_pause);
                }else {
                    CommonLogic.getInstance().setLawyerProductData(data);
                    handler.post(task);
                    voiceHelper.initVoice(VoiceHelper.MUSIC_INDEX, url);
                    voiceHelper.startVoice();
                    play.setImageResource(R.mipmap.icon_audio_pause);
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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getLawyer()!=null){
                    ShareModel shareModel=new ShareModel();
                    shareModel.setShareUrl(getString(R.string.share_url));
                    shareModel.setShareTitle("说法");
                    shareModel.setShareContent("说法");
                    shareDialog.setShareModel(shareModel);
                    shareDialog.show();
                }
            }
        });

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getArticle()!=null){
                    UIUtils.goCommentList(mContext,data.getArticle().getId());
                }
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

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getArticle().getIs_colle()==0){
                    collection();
                }else {
                    uncollection();
                }
            }
        });

        if (voiceHelper.isPlay()&&StringUtil.equals(url,voiceHelper.getPlayingVoicePath())){
            handler.post(task);
            timeAll.setText(setTimeAll(voiceHelper.getLength()));
            time.setText(setTimeAll(voiceHelper.getPlayingCurrPostion()));
            play.setImageResource(R.mipmap.icon_audio_pause);
        }else if (voiceHelper.isPause()&&StringUtil.equals(url,voiceHelper.getPlayingVoicePath())){
            timeAll.setText(setTimeAll(voiceHelper.getLength()));
            time.setText(setTimeAll(voiceHelper.getPlayingCurrPostion()));
            play.setImageResource(R.mipmap.icon_audio_player);
        }
        ImageLoderUtil.loadCircleImage(mContext, theme, imageUrl, R.mipmap.icon_head_def_cir);
        if (data.getArticle()!=null) {
            themeDes.setText(data.getArticle().getTitle());
            if (data.getArticle().getIs_colle()==0){
                like.setImageResource(R.mipmap.icon_audio_like);
            }else {
                like.setImageResource(R.mipmap.icon_audio_liked);
            }
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (data.getLawyer()!=null){
            lawyerName.setText(data.getLawyer().getName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== CommonConstant.COMMENT_RESULT_CODE){
            if (data!=null&&AudioDetailsActivity.this.data.getArticle()!=null){
                String count=data.getStringExtra(COMMENT_COUNT);
//                commentCount.setText(count);
//                audioModel.setCommment_num(count);
            }
        }
        if (shareDialog!=null){
            shareDialog.onActivityResult(requestCode, resultCode, data);
        }
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

    private void uncollection(){
        if (data.getArticle()==null){
            return;
        }
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("id",data.getArticle().getId());
        addNetwork(Api.getInstance().getService(ApiService.class).delCollection(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode()== CommonConstant.NET_SUC_CODE){
                            data.getArticle().setIs_colle(0);
                            like.setImageResource(R.mipmap.icon_audio_like);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void collection(){
        if (data.getArticle()==null){
            return;
        }
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("id",data.getArticle().getId());
        addNetwork(Api.getInstance().getService(ApiService.class).setCollection(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode()== CommonConstant.NET_SUC_CODE){
                            data.getArticle().setIs_colle(1);
                            like.setImageResource(R.mipmap.icon_audio_liked);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }
}
