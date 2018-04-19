package com.chuxin.law.ui.fragment;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.audioplayer.AudioStopEvent;
import com.chuxin.law.audioplayer.db.DownloadThreadDB;
import com.chuxin.law.audioplayer.download.DownloadMessage;
import com.chuxin.law.audioplayer.manage.AudioPlayerManager;
import com.chuxin.law.audioplayer.manage.OnLineAudioManager;
import com.chuxin.law.audioplayer.model.AudioInfo;
import com.chuxin.law.audioplayer.model.AudioMessage;
import com.chuxin.law.audioplayer.receiver.AudioBroadcastReceiver;
import com.chuxin.law.audioplayer.receiver.OnLineAudioReceiver;
import com.chuxin.law.audioplayer.util.AudioPlayUtils;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.common.CommonLogic;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.ui.widget.HtmlTextView;
import com.chuxin.law.util.DateUtil;
import com.chuxin.law.util.ImageLoderUtil;
import com.chuxin.law.util.LawyerDefViewPagerUtils;
import com.chuxin.law.util.UIUtils;
import com.chuxin.law.util.voice.VoiceHelper;
import com.jrmf360.rylib.common.util.ToastUtil;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.Util.MD5Util;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.Util.TouchUtil;
import com.jusfoun.baselibrary.dialog.MyProgressBar;
import com.jusfoun.baselibrary.permissiongen.PermissionFail;
import com.jusfoun.baselibrary.permissiongen.PermissionGen;
import com.jusfoun.baselibrary.permissiongen.PermissionSuccess;
import com.jusfoun.baselibrary.task.WeakHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.EVENT_BUY_LAWYER;

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
    protected HtmlTextView content;
    protected TextView timeAll;
    protected SeekBar seek;
    protected TextView dashang;
    private String mContent;
    private String url;
    private String id;
    private String imgUrl;
    private LawyerProductModel.LawyerProductData data;

    private AudioInfo audioInfo;
    private VoiceHelper voiceHelper;
    private MyProgressBar progressBar;

    private KeyguardManager mKeyguardManager;

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
                int downloadedSize = DownloadThreadDB.getDownloadThreadDB(mContext.getApplicationContext()).getDownloadedSize(downloadMessage.getTaskId(), OnLineAudioManager.threadNum);
                double pre = downloadedSize * 1.0 / AudioPlayUtils.getInstance().getmCurrentAudio().getFileSize();
                int downloadProgress = (int) (seek.getMax() * pre);
                seek.setSecondaryProgress(downloadProgress);
            }
        } else if (action.equals(OnLineAudioReceiver.ACTION_ONLINEMUSICERROR)) {
            DownloadMessage downloadMessage = (DownloadMessage) intent.getSerializableExtra(DownloadMessage.KEY);
            if (AudioPlayUtils.getInstance().getmCurrentAudio().getHash().equals(downloadMessage.getTaskId())) {
                ToastUtil.showToast(mContext.getApplicationContext(), downloadMessage.getErrorMsg());
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
        LogUtil.e("action==" + action);
        if (action.equals(AudioBroadcastReceiver.ACTION_NULLMUSIC)) {

        } else if (action.equals(AudioBroadcastReceiver.ACTION_INITMUSIC)) {
            //初始化
            AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();//(AudioMessage) intent.getSerializableExtra(AudioMessage.KEY);
            //
            time.setText(DateUtil.parseTimeToString((int) audioMessage.getPlayProgress()));

            //
            seek.setEnabled(true);

            seek.setProgress((int) audioMessage.getPlayProgress());
            seek.setSecondaryProgress(0);

        } else if (action.equals(AudioBroadcastReceiver.ACTION_SERVICE_PLAYMUSIC)) {
            if (progressBar.isShow()){
                progressBar.hide();
            }
            //播放
            AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();//(AudioMessage) intent.getSerializableExtra(AudioMessage.KEY);

            if (audioMessage.getPlayDuration() != seek.getMax()) {
                seek.setMax((int) audioMessage.getPlayDuration());
                timeAll.setText(DateUtil.parseTimeToString((int) audioMessage.getPlayDuration()));
            }
            play.setImageResource(R.mipmap.icon_lawyer_player);
            time.setText(DateUtil.parseTimeToString((int) audioMessage.getPlayProgress()));
            seek.setProgress((int) audioMessage.getPlayProgress());

        } else if (action.equals(AudioBroadcastReceiver.ACTION_SERVICE_PAUSEMUSIC)) {
            //暂停完成
            play.setImageResource(R.mipmap.icon_lawyer_pause);

        } else if (action.equals(AudioBroadcastReceiver.ACTION_SERVICE_RESUMEMUSIC)) {
            //唤醒完成
            play.setImageResource(R.mipmap.icon_lawyer_player);

        } else if (action.equals(AudioBroadcastReceiver.ACTION_SERVICE_PLAYINGMUSIC)) {
            if (progressBar.isShow()){
                progressBar.hide();
            }
            //播放中
            AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();//(AudioMessage) intent.getSerializableExtra(AudioMessage.KEY);
            if (audioMessage != null) {
                if (audioMessage.getPlayDuration() != seek.getMax()) {
                    seek.setMax((int) audioMessage.getPlayDuration());
                    timeAll.setText(DateUtil.parseTimeToString((int) audioMessage.getPlayDuration()));
                    play.setImageResource(R.mipmap.icon_lawyer_player);
                }
                time.setText(DateUtil.parseTimeToString((int) audioMessage.getPlayProgress()));
                seek.setProgress((int) audioMessage.getPlayProgress());
            }

        }
    }

    public static LawyerDefAudioFragment getInstance(Bundle args) {
        LawyerDefAudioFragment fragment = new LawyerDefAudioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void refreshData() {
        ImageLoderUtil.loadCircleImage(mContext, imgAudio, imgUrl, R.mipmap.icon_head_def_cir);
        if (!checkPer()){
            applyPer();
            return;
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_lawyer_def_aduio;
    }

    @Override
    public void initDatas() {

        mKeyguardManager= (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);

        data = (LawyerProductModel.LawyerProductData) getArguments().getSerializable(LawyerDefViewPagerUtils.DATA);
        if (data == null || data.getArticle() == null || data.getLawyer() == null) {
            return;
        }
        EventBus.getDefault().register(this);
        mContent = data.getArticle().getContent();
        url = data.getArticle().getMp3();
        id = data.getLawyer().getUserid();
        imgUrl = data.getArticle().getImg();
        audioInfo = new AudioInfo();
        audioInfo.setDownloadUrl(url);
        audioInfo.setHash(MD5Util.getMD5Str(url));
        audioInfo.setSongName(data.getArticle().getId());
        audioInfo.setSingerName(data.getArticle().getId());
        audioInfo.setFileExt("mp3");
        audioInfo.setType(AudioInfo.NET);
        voiceHelper=new VoiceHelper();
        voiceHelper.initVoice(VoiceHelper.MUSIC_INDEX,url);
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
        content = (HtmlTextView) rootView.findViewById(R.id.content);
        dashang = (TextView) rootView.findViewById(R.id.dashang);

        progressBar=rootView.findViewById(R.id.progress_bar);

    }

    @Override
    public void initAction() {
        play.setImageResource(R.mipmap.icon_lawyer_pause);
        content.setHtmlFromString(mContent,false);
        dashang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.goGratuity(mContext, data);
            }
        });
        imgAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (data == null || data.getArticle() == null || data.getArticle().getIs_buy() == 0) {
                    showToast("请先购买");
                    return;
                }
                if (!checkPer()){
                    applyPer();
                    return;
                }

                if (StringUtil.isEmpty(url)
                        ||!url.startsWith("http://")){
                    ToastUtil.showToast(mContext,"音乐格式错误");
                    return;
                }

                if (CommonLogic.getInstance().getLawyerProductData() != null
                        && CommonLogic.getInstance().getLawyerProductData().getArticle() != null
                        && !StringUtil.equals(CommonLogic.getInstance().getLawyerProductData().getArticle().getId(), data.getArticle().getId())) {
                    initService();
                    CommonLogic.getInstance().setLawyerProductData(data);
                    Intent playIntent = new Intent(AudioBroadcastReceiver.ACTION_PLAYMUSIC);
                    AudioMessage audioMessage = new AudioMessage();
                    audioMessage.setAudioInfo(audioInfo);
                    playIntent.putExtra(AudioMessage.KEY, audioMessage);
                    playIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    mContext.sendBroadcast(playIntent);
                    play.setImageResource(R.mipmap.icon_lawyer_player);
                    progressBar.show();
                    return;
                }
                int status = AudioPlayUtils.getInstance().getPlayStatus();
                if (status == AudioPlayerManager.PAUSE) {
                    AudioInfo audioInfo = AudioPlayUtils.getInstance().getmCurrentAudio();
                    if (audioInfo != null) {

                        AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();
                        Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_RESUMEMUSIC);
                        resumeIntent.putExtra(AudioMessage.KEY, audioMessage);
                        resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        mContext.sendBroadcast(resumeIntent);

                    }
                    play.setImageResource(R.mipmap.icon_lawyer_player);
                } else if (status == AudioPlayerManager.PLAYING) {

                    Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_PAUSEMUSIC);
                    resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    mContext.sendBroadcast(resumeIntent);
                    play.setImageResource(R.mipmap.icon_lawyer_pause);
                } else {
                    CommonLogic.getInstance().setLawyerProductData(data);
                    Intent playIntent = new Intent(AudioBroadcastReceiver.ACTION_PLAYMUSIC);
                    AudioMessage audioMessage = new AudioMessage();
                    audioMessage.setAudioInfo(audioInfo);
                    audioMessage.setPlayProgress(0);
                    playIntent.putExtra(AudioMessage.KEY, audioMessage);
                    playIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    mContext.sendBroadcast(playIntent);
                    play.setImageResource(R.mipmap.icon_lawyer_player);
                    progressBar.show();
                }
            }
        });

        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int playStatus = AudioPlayUtils.getInstance().getPlayStatus();
                if (playStatus == AudioPlayerManager.PLAYING) {
                    progressBar.show();
                    //正在播放
                    if (AudioPlayUtils.getInstance().getCurAudioMessage() != null) {
                        AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();
                        if (audioMessage != null) {
                            long progress = audioMessage.getPlayProgress() - 10000;
                            if (progress < 0) {
                                progress = 0;
                            }
                            audioMessage.setPlayProgress(progress);
                            Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_SEEKTOMUSIC);
                            resumeIntent.putExtra(AudioMessage.KEY, audioMessage);
                            resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                            mContext.sendBroadcast(resumeIntent);
                        }
                    }
                }
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //正在播放
                if (AudioPlayUtils.getInstance().getCurAudioMessage() != null) {
                    progressBar.show();
                    AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();
                    if (audioMessage != null) {
                        long progress = audioMessage.getPlayProgress() + 10000;
                        if (progress >= audioMessage.getPlayDuration()) {
                            progress = audioMessage.getPlayDuration();
                        }
                        audioMessage.setPlayProgress(progress);
                        Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_SEEKTOMUSIC);
                        resumeIntent.putExtra(AudioMessage.KEY, audioMessage);
                        resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        mContext.sendBroadcast(resumeIntent);
                    }
                }
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //正在播放
                if (AudioPlayUtils.getInstance().getCurAudioMessage() != null) {
                    progressBar.show();
                    AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();
                    if (audioMessage != null) {
                        long progress = audioMessage.getPlayProgress() - 2000;
                        if (progress < 0) {
                            progress = 0;
                        }
                        audioMessage.setPlayProgress(progress);
                        Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_SEEKTOMUSIC);
                        resumeIntent.putExtra(AudioMessage.KEY, audioMessage);
                        resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        mContext.sendBroadcast(resumeIntent);
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //正在播放
                if (AudioPlayUtils.getInstance().getCurAudioMessage() != null) {
                    progressBar.show();
                    AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();
                    if (audioMessage != null) {
                        long progress = audioMessage.getPlayProgress() + 2000;
                        if (progress >= audioMessage.getPlayDuration()) {
                            progress = audioMessage.getPlayDuration();
                        }
                        audioMessage.setPlayProgress(progress);
                        Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_SEEKTOMUSIC);
                        resumeIntent.putExtra(AudioMessage.KEY, audioMessage);
                        resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        mContext.sendBroadcast(resumeIntent);
                    }
                }
            }
        });

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //正在播放
                if (AudioPlayUtils.getInstance().getCurAudioMessage() != null) {
                    progressBar.show();
                    AudioMessage audioMessage = AudioPlayUtils.getInstance().getCurAudioMessage();
                    if (audioMessage != null) {
                        long progress = seekBar.getProgress();
                        audioMessage.setPlayProgress(progress);
                        Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_SEEKTOMUSIC);
                        resumeIntent.putExtra(AudioMessage.KEY, audioMessage);
                        resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        mContext.sendBroadcast(resumeIntent);
                    }
                }
            }
        });


        rxManage.on(EVENT_BUY_LAWYER, new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (data != null && data.getArticle() != null) {
                    data.getArticle().setIs_buy(1);
                }
            }
        });

        voiceHelper.setListener(new VoiceHelper.OnPlayListener() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void prepared() {
                timeAll.setText(DateUtil.parseTimeToString((int) voiceHelper.getLength()));
            }
        });
        voiceHelper.initAudio();
    }

    /**
     * 初始化服务
     */
    private void initService() {

        //注册接收音频播放广播
        mAudioBroadcastReceiver = new AudioBroadcastReceiver(mContext);
        mAudioBroadcastReceiver.setAudioReceiverListener(mAudioReceiverListener);
        mAudioBroadcastReceiver.registerReceiver(mContext);

        //在线音乐广播
        mOnLineAudioReceiver = new OnLineAudioReceiver(mContext);
        mOnLineAudioReceiver.setOnlineAudioReceiverListener(mOnlineAudioReceiverListener);
        mOnLineAudioReceiver.registerReceiver(mContext);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (CommonLogic.getInstance().getLawyerProductData() == null
                || CommonLogic.getInstance().getLawyerProductData().getArticle() == null
                || StringUtil.equals(CommonLogic.getInstance().getLawyerProductData().getArticle().getId(), data.getArticle().getId())) {
            initService();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAudioBroadcastReceiver!=null) {
            mAudioBroadcastReceiver.unregisterReceiver(mContext);
            mAudioBroadcastReceiver=null;
        }
        if (mOnLineAudioReceiver!=null) {
            mOnLineAudioReceiver.unregisterReceiver(mContext);
            mOnLineAudioReceiver=null;
        }

        if (mKeyguardManager.inKeyguardRestrictedInputMode()){
            play.setImageResource(R.mipmap.icon_lawyer_pause);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 检查权限
     */
    private void applyPer(){
        PermissionGen.with(this).addRequestCode(100)
                .permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
                .request();
    }

    private boolean checkPer(){
        return PermissionGen.checkPermissions(getContext(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @PermissionFail(requestCode = 100)
    private void perFail() {
        showToast("无法获取权限,请设置权限");
    }

    @PermissionSuccess(requestCode = 100)
    private void perSuc() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioStopEvent event){
        seek.setProgress(0);
        time.setText("00:00");
        play.setImageResource(R.mipmap.icon_lawyer_pause);
    }
}
