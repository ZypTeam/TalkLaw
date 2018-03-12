package com.chuxin.law.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.audioplayer.db.DownloadThreadDB;
import com.chuxin.law.audioplayer.download.AudioInfoDB;
import com.chuxin.law.audioplayer.download.DownloadInfoDB;
import com.chuxin.law.audioplayer.download.DownloadMessage;
import com.chuxin.law.audioplayer.manage.AudioPlayerManager;
import com.chuxin.law.audioplayer.manage.OnLineAudioManager;
import com.chuxin.law.audioplayer.model.AudioInfo;
import com.chuxin.law.audioplayer.model.AudioMessage;
import com.chuxin.law.audioplayer.receiver.AudioBroadcastReceiver;
import com.chuxin.law.audioplayer.receiver.OnLineAudioReceiver;
import com.chuxin.law.audioplayer.util.AudioPlayUtils;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.common.CommonLogic;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.model.ShareModel;
import com.chuxin.law.ui.dialog.ShareDialog;
import com.chuxin.law.util.DateUtil;
import com.chuxin.law.util.ImageLoderUtil;
import com.chuxin.law.util.UIUtils;
import com.chuxin.law.util.voice.VoiceHelper;
import com.jrmf360.rylib.common.util.ToastUtil;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.task.WeakHandler;

import java.util.HashMap;
import java.util.List;

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

    /**
     * 音频广播
     */
    private AudioBroadcastReceiver mAudioBroadcastReceiver;

    /**
     * 广播监听
     */
    private AudioBroadcastReceiver.AudioReceiverListener mAudioReceiverListener = new AudioBroadcastReceiver.AudioReceiverListener() {
        @Override
        public void onReceive(Context context, Intent intent) {
            doAudioReceive(context, intent);
        }
    };

    /**
     * 在线音乐广播
     */
    private OnLineAudioReceiver mOnLineAudioReceiver;
    private OnLineAudioReceiver.OnlineAudioReceiverListener mOnlineAudioReceiverListener = new OnLineAudioReceiver.OnlineAudioReceiverListener() {
        @Override
        public void onReceive(Context context, Intent intent) {
            doNetMusicReceive(context, intent);
        }
    };

    /**
     * 处理网络歌曲广播
     *
     * @param context
     * @param intent
     */
    private void doNetMusicReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(OnLineAudioReceiver.ACTION_ONLINEMUSICDOWNLOADING)) {
            DownloadMessage downloadMessage = (DownloadMessage) intent.getSerializableExtra(DownloadMessage.KEY);
            if (AudioPlayUtils.getInstance().getmCurrentAudio().getHash().equals(downloadMessage.getTaskId())) {
                int downloadedSize = DownloadThreadDB.getDownloadThreadDB(getApplicationContext()).getDownloadedSize(downloadMessage.getTaskId(), OnLineAudioManager.threadNum);
                double pre = downloadedSize * 1.0 / AudioPlayUtils.getInstance().getmCurrentAudio().getFileSize();
                int downloadProgress = (int) (seek.getMax() * pre);
                seek.setSecondaryProgress(downloadProgress);
            }
        } else if (action.equals(OnLineAudioReceiver.ACTION_ONLINEMUSICERROR)) {
            DownloadMessage downloadMessage = (DownloadMessage) intent.getSerializableExtra(DownloadMessage.KEY);
            if (AudioPlayUtils.getInstance().getmCurrentAudio().getHash().equals(downloadMessage.getTaskId())) {
                ToastUtil.showToast(getApplicationContext(), downloadMessage.getErrorMsg());
            }
        }

    }


    /**
     * 处理音频广播事件
     *
     * @param context
     * @param intent
     */
    private void doAudioReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(AudioBroadcastReceiver.ACTION_NULLMUSIC)) {

        } else if (action.equals(AudioBroadcastReceiver.ACTION_INITMUSIC)) {
            //初始化
            AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();//(AudioMessage) intent.getSerializableExtra(AudioMessage.KEY);
            AudioInfo audioInfo = AudioPlayUtils.getInstance().getmCurrentAudio();

            if (AudioPlayUtils.getInstance().getPlayStatus() == AudioPlayerManager.PLAYING) {
                play.setImageResource(R.mipmap.icon_audio_pause);
            } else {
                play.setImageResource(R.mipmap.icon_audio_player);
            }


            //
            time.setText(DateUtil.parseTimeToString((int) audioMessage.getPlayProgress()));
            timeAll.setText(DateUtil.parseTimeToString((int) audioInfo.getDuration()));
            //
            seek.setEnabled(true);
            seek.setMax((int) audioInfo.getDuration());
            seek.setProgress((int) audioMessage.getPlayProgress());
            seek.setSecondaryProgress(0);

            if (audioInfo.getType() == AudioInfo.NET || audioInfo.getType() == AudioInfo.DOWNLOAD) {

                //下载
                if (DownloadInfoDB.getAudioInfoDB(getApplicationContext()).isExists(audioInfo.getHash()) || AudioInfoDB.getAudioInfoDB(getApplicationContext()).isNetAudioExists(audioInfo.getHash())) {


                } else {

                }

            } else {

            }

        } else if (action.equals(AudioBroadcastReceiver.ACTION_SERVICE_PLAYMUSIC)) {
            //播放

            AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();//(AudioMessage) intent.getSerializableExtra(AudioMessage.KEY);

            play.setImageResource(R.mipmap.icon_audio_pause);

            //
            time.setText(DateUtil.parseTimeToString((int) audioMessage.getPlayProgress()));
            //
            seek.setProgress((int) audioMessage.getPlayProgress());

        } else if (action.equals(AudioBroadcastReceiver.ACTION_SERVICE_PAUSEMUSIC)) {
            //暂停完成
            play.setImageResource(R.mipmap.icon_audio_player);

        } else if (action.equals(AudioBroadcastReceiver.ACTION_SERVICE_RESUMEMUSIC)) {
            //唤醒完成
            play.setImageResource(R.mipmap.icon_audio_player);

        } else if (action.equals(AudioBroadcastReceiver.ACTION_SERVICE_PLAYINGMUSIC)) {
            //播放中
            AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();//(AudioMessage) intent.getSerializableExtra(AudioMessage.KEY);
            if (audioMessage != null) {
                time.setText(DateUtil.parseTimeToString((int) audioMessage.getPlayProgress()));
                seek.setProgress((int) audioMessage.getPlayProgress());
            }

        }
    }


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
                /*int playStatus = AudioPlayUtils.getInstance().getPlayStatus();
                if (playStatus == AudioPlayerManager.PAUSE) {

                    AudioInfo audioInfo = AudioPlayUtils.getInstance().getmCurrentAudio();
                    if (audioInfo != null) {

                        AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();
                        Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_RESUMEMUSIC);
                        resumeIntent.putExtra(AudioMessage.KEY, audioMessage);
                        resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        sendBroadcast(resumeIntent);

                    }

                } else {
                    if (AudioPlayUtils.getInstance().getCurAudioMessage() != null) {
                        AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();
                        AudioInfo audioInfo = AudioPlayUtils.getInstance().getmCurrentAudio();
                        if (audioInfo != null) {
                            audioMessage.setAudioInfo(audioInfo);
                            Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_PLAYMUSIC);
                            resumeIntent.putExtra(AudioMessage.KEY, audioMessage);
                            resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                            sendBroadcast(resumeIntent);
                        }
                    }
                }*/
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
