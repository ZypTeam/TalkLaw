package com.chuxin.law.audioplayer.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;

import com.chuxin.law.audioplayer.AudioStopEvent;
import com.chuxin.law.audioplayer.ResourceConstants;
import com.chuxin.law.audioplayer.db.DownloadThreadDB;
import com.chuxin.law.audioplayer.manage.AudioPlayerManager;
import com.chuxin.law.audioplayer.manage.OnLineAudioManager;
import com.chuxin.law.audioplayer.model.AudioInfo;
import com.chuxin.law.audioplayer.model.AudioMessage;
import com.chuxin.law.audioplayer.receiver.AudioBroadcastReceiver;
import com.chuxin.law.audioplayer.receiver.NotificationReceiver;
import com.chuxin.law.audioplayer.util.AudioPlayUtils;
import com.chuxin.law.audioplayer.util.ResourceFileUtil;
import com.jrmf360.rylib.common.util.ToastUtil;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.Util.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * @author wangcc
 * @date 2018/3/11
 * @describe 播放服务
 */

public class AudioPlayerService extends Service {

    /**
     * 播放器
     */
    private IjkMediaPlayer mMediaPlayer;

    /**
     * 播放线程
     */
    private Thread mPlayerThread = null;

//    private HPApplication mHPApplication;

    private int mNotificationPlayBarId = 19900420;
    private AudioPlayUtils audioPlayUtils;

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
     * 是否正在快进
     */
    private boolean isSeekTo = false;


    @Override
    public void onCreate() {
        super.onCreate();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("talklaw", "talklaw", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(getApplicationContext(), "talklaw").build();
            startForeground(mNotificationPlayBarId, notification);
        }*/

        EventBus.getDefault().register(this);
        audioPlayUtils=AudioPlayUtils.getInstance();

        //注册接收音频播放广播
        mAudioBroadcastReceiver = new AudioBroadcastReceiver(getApplicationContext());
        mAudioBroadcastReceiver.setAudioReceiverListener(mAudioReceiverListener);
        mAudioBroadcastReceiver.registerReceiver(getApplicationContext());

        LogUtil.i("音频播放服务启动");

        //初始化通知栏
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mAudioBroadcastReceiver.unregisterReceiver(getApplicationContext());

        //关闭通知栏
        stopForeground(true);

        releasePlayer();
        LogUtil.i("音频播放服务销毁");
        super.onDestroy();
    }

    /**
     * 广播处理
     *
     * @param context
     * @param intent
     */
    private void doAudioReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(AudioBroadcastReceiver.ACTION_NULLMUSIC)) {
            releasePlayer();
            resetPlayData();

        } else if (action.equals(AudioBroadcastReceiver.ACTION_PLAYMUSIC)) {
            //播放歌曲
            playMusic((AudioMessage) intent.getSerializableExtra(AudioMessage.KEY));

        } else if (action.equals(AudioBroadcastReceiver.ACTION_PAUSEMUSIC)) {
            //暂停歌曲
            pauseMusic();
        } else if (action.equals(AudioBroadcastReceiver.ACTION_RESUMEMUSIC)) {
            //唤醒歌曲
            resumeMusic((AudioMessage) intent.getSerializableExtra(AudioMessage.KEY));
        } else if (action.equals(AudioBroadcastReceiver.ACTION_SEEKTOMUSIC)) {
            //歌曲快进
            seekToMusic((AudioMessage) intent.getSerializableExtra(AudioMessage.KEY));
        }else if (StringUtil.equals(action,AudioBroadcastReceiver.ACTION_STOP)){
            stopMusic();
        }
    }

    /**
     * 快进
     *
     * @param audioMessage
     */
    private void seekToMusic(AudioMessage audioMessage) {

        if (mMediaPlayer != null) {
            isSeekTo = true;
            mMediaPlayer.seekTo(audioMessage.getPlayProgress());
        }

    }

    /**
     * 唤醒播放
     */
    private void resumeMusic(AudioMessage audioMessage) {

        //如果是网络歌曲，先进行下载，再进行播放
        if (audioPlayUtils.getmCurrentAudio() != null && audioPlayUtils.getmCurrentAudio().getType() == AudioInfo.NET) {
            //如果进度为0，表示上一次下载直接错误。
            int downloadedSize = DownloadThreadDB.getDownloadThreadDB(getApplicationContext()).getDownloadedSize(audioPlayUtils.getmCurrentAudio().getHash(), OnLineAudioManager.threadNum);
            if (downloadedSize == 0) {
                //发送init的广播
                Intent initIntent = new Intent(AudioBroadcastReceiver.ACTION_INITMUSIC);
                //initIntent.putExtra(AudioMessage.KEY, audioMessage);
                initIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(initIntent);
            }
            doNetMusic();
        } else {
            if (mMediaPlayer != null) {
                isSeekTo = true;
                mMediaPlayer.seekTo(audioMessage.getPlayProgress());
            }
            audioPlayUtils.setPlayStatus(AudioPlayerManager.PLAYING);
        }

    }

    /**
     * 暂停播放
     */
    private void pauseMusic() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        }
        audioPlayUtils.setPlayStatus(AudioPlayerManager.PAUSE);
        Intent nextIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PAUSEMUSIC);
        nextIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(nextIntent);
    }

    private void stopMusic(){
        if (mMediaPlayer!=null){
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
        }
        audioPlayUtils.setPlayStatus(AudioPlayerManager.STOP);
       /* Intent nextIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_STOP);
        nextIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(nextIntent);*/
    }

    /**
     * 播放歌曲
     *
     * @param audioMessage
     */
    private void playMusic(AudioMessage audioMessage) {
        releasePlayer();
        // resetPlayData();

        AudioInfo audioInfo = audioMessage.getAudioInfo();
        audioPlayUtils.setmCurrentAudio(audioInfo);
        audioPlayUtils.setCurAudioMessage(audioMessage);
        //发送init的广播
        Intent initIntent = new Intent(AudioBroadcastReceiver.ACTION_INITMUSIC);
        //initIntent.putExtra(AudioMessage.KEY, audioMessage);
        initIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(initIntent);


        if (audioInfo.getType() == AudioInfo.LOCAL) {
            //播放本地歌曲
            playLocalMusic(audioMessage);
        } else {
            String fileName = audioInfo.getSingerName() + " - " + audioInfo.getSongName();
            String filePath = ResourceFileUtil.getFilePath(getApplicationContext(), ResourceConstants.PATH_AUDIO, fileName + "." + audioInfo.getFileExt());
            //设置文件路径
            audioInfo.setFilePath(filePath);
            File audioFile = new File(filePath);
            if (audioFile.exists()) {
                //播放本地歌曲
                playLocalMusic(audioMessage);
            } else {
                //播放网络歌曲
                doNetMusic();
            }
        }
    }

    /**
     * 下载线程
     */
    private Handler mDownloadHandler = new Handler();

    /**
     *
     */
    private Runnable mDownloadCheckRunnable = new Runnable() {
        @Override
        public void run() {
            if (audioPlayUtils.getPlayStatus() == AudioPlayerManager.PLAYNET) {

                int downloadedSize = DownloadThreadDB.getDownloadThreadDB(getApplicationContext()).getDownloadedSize(audioPlayUtils.getmCurrentAudio().getHash(), OnLineAudioManager.threadNum);
                LogUtil.e("在线播放任务名称：" + audioPlayUtils.getmCurrentAudio().getSongName() + "  缓存播放时，监听已下载大小：" + downloadedSize);

                mDownloadHandler.removeCallbacks(mDownloadCheckRunnable);
                if (downloadedSize > 1024 * 200) {

                    if (audioPlayUtils.getPlayStatus() != AudioPlayerManager.PAUSE) {
                        playNetMusic();
                    }

                } else {
                    mDownloadHandler.postDelayed(mDownloadCheckRunnable, 1000);
                }
            }
        }
    };

    /**
     * 播放网络歌曲
     */
    private void playNetMusic() {
        if (audioPlayUtils.getCurAudioMessage() != null && audioPlayUtils.getmCurrentAudio() != null) {
            String filePath = ResourceFileUtil.getFilePath(getApplicationContext(), ResourceConstants.PATH_CACHE_AUDIO, audioPlayUtils.getmCurrentAudio().getHash() + ".temp");
            try {
                mMediaPlayer = new IjkMediaPlayer();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setDataSource(filePath);
                mMediaPlayer.prepareAsync();
                //
                mMediaPlayer.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(IMediaPlayer mp) {
                        mMediaPlayer.start();
                        isSeekTo = false;

                    }
                });
                mMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(IMediaPlayer mp) {

                        /*if (mMediaPlayer.getCurrentPosition() < (audioPlayUtils.getmCurrentAudio().getDuration() - 2 * 1000)) {
                            playNetMusic();
                        } else {
                            //播放完成，执行下一首操作
//                            nextMusic();
                        }*/

                    }
                });
                mMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(IMediaPlayer mp, int what, int extra) {

                        //发送播放错误广播
                        Intent errorIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYERRORMUSIC);
                        errorIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        sendBroadcast(errorIntent);

                        ToastUtil.showToast(getApplicationContext(), "播放歌曲出错");


                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    //播放下一首
//                                    nextMusic();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                        return false;
                    }
                });
                mMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(IMediaPlayer mp) {

                        if (audioPlayUtils.getCurAudioMessage() != null) {
                            AudioMessage audioMessage = audioPlayUtils.getCurAudioMessage();
                            audioMessage.setPlayDuration(mMediaPlayer.getDuration());
                            if (audioMessage.getPlayProgress() > 0) {
                                isSeekTo = true;
                                mMediaPlayer.seekTo(audioMessage.getPlayProgress());
                            } else {
                                mMediaPlayer.start();
                            }


                            //设置当前播放的状态
                            audioPlayUtils.setPlayStatus(AudioPlayerManager.PLAYING);

                            //发送play的广播
                            Intent playIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYMUSIC);
                            playIntent.putExtra(AudioMessage.KEY, audioMessage);
                            playIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                            sendBroadcast(playIntent);
                        }
                    }
                });

                if (mPlayerThread == null) {
                    mPlayerThread = new Thread(new PlayerRunable());
                    mPlayerThread.start();
                }

            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e(e.getMessage());

                //发送播放错误广播
                Intent errorIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYERRORMUSIC);
                audioPlayUtils.getCurAudioMessage().setErrorMsg(e.getMessage());
                errorIntent.putExtra(AudioMessage.KEY, audioPlayUtils.getCurAudioMessage());
                errorIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(errorIntent);

                ToastUtil.showToast(getApplicationContext(), "播放歌曲出错");
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            //播放下一首
//                            nextMusic();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }


        }
    }

    /**
     * 播放网络歌曲
     */
    private void doNetMusic() {
        AudioInfo audioInfo = audioPlayUtils.getmCurrentAudio();
        mDownloadHandler.removeCallbacks(mDownloadCheckRunnable);
        //设置当前的播放状态
        audioPlayUtils.setPlayStatus(AudioPlayerManager.PLAYNET);

        //下载
        if (!OnLineAudioManager.getOnLineAudioManager( getApplicationContext()).taskIsExists(audioInfo.getHash())) {
            OnLineAudioManager.getOnLineAudioManager( getApplicationContext()).addTask(audioInfo);
            mDownloadHandler.postAtTime(mDownloadCheckRunnable, 1000);
            LogUtil.e("准备播放在线歌曲：" + audioInfo.getSongName());
        }

    }

    /**
     * 播放本地歌曲
     *
     * @param audioMessage
     */
    private void playLocalMusic(AudioMessage audioMessage) {

        try {
            mMediaPlayer = new IjkMediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(audioMessage.getAudioInfo().getFilePath());
            mMediaPlayer.prepareAsync();
            //
            mMediaPlayer.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(IMediaPlayer mp) {
                    mMediaPlayer.start();
                    /*if (mHPApplication.isLrcSeekTo()) {

                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHPApplication.setLrcSeekTo(false);
                    }*/
                    isSeekTo = false;

                }
            });
            mMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer mp) {

                    //播放完成，执行下一首操作
//                    nextMusic();

                }
            });
            mMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(IMediaPlayer mp, int what, int extra) {

                    //发送播放错误广播
                    Intent errorIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYERRORMUSIC);
                    errorIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    sendBroadcast(errorIntent);

                    ToastUtil.showToast(getApplicationContext(), "播放歌曲出错，1秒后播放下一首");


                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                //播放下一首
//                                nextMusic();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                    return false;
                }
            });
            mMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer mp) {

                    if (audioPlayUtils.getCurAudioMessage() != null) {
                        AudioMessage audioMessage = audioPlayUtils.getCurAudioMessage();
                        audioMessage.setPlayDuration(mMediaPlayer.getDuration());

                        if (audioMessage.getPlayProgress() > 0) {
                            isSeekTo = true;
                            mMediaPlayer.seekTo(audioMessage.getPlayProgress());
                        } else {
                            mMediaPlayer.start();
                        }


                        //设置当前播放的状态
                        audioPlayUtils.setPlayStatus(AudioPlayerManager.PLAYING);

                        //发送play的广播
                        Intent playIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYMUSIC);
                        //playIntent.putExtra(AudioMessage.KEY, audioMessage);
                        playIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        sendBroadcast(playIntent);
                    }
                }
            });

            if (mPlayerThread == null) {
                mPlayerThread = new Thread(new PlayerRunable());
                mPlayerThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(e.getMessage());

            //发送播放错误广播
            Intent errorIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYERRORMUSIC);
            audioMessage.setErrorMsg(e.getMessage());
            errorIntent.putExtra(AudioMessage.KEY, audioMessage);
            errorIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(errorIntent);

            ToastUtil.showToast(getApplicationContext(), "播放歌曲出错");
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        //播放下一首
//                        nextMusic();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    /**
     * 播放线程
     */
    private class PlayerRunable implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);//方便后面用来刷新歌词
                    if (!isSeekTo && mMediaPlayer != null && mMediaPlayer.isPlaying()) {

                        if (audioPlayUtils.getCurAudioMessage() != null) {
                            audioPlayUtils.getCurAudioMessage().setPlayProgress(mMediaPlayer.getCurrentPosition());

                            //发送正在播放中的广播
                            Intent playingIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYINGMUSIC);
                            //playingIntent.putExtra(AudioMessage.KEY, mHPApplication.getCurAudioMessage());
                            playingIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                            sendBroadcast(playingIntent);

                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 释放播放器
     */
    private void releasePlayer() {
        audioPlayUtils.setPlayStatus(AudioPlayerManager.STOP);
        if (mPlayerThread != null) {
            mPlayerThread = null;
        }
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            //mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        System.gc();
    }

    /**
     * 重置播放数据
     */
    private void resetPlayData() {
        //设置当前播放的状态
        audioPlayUtils.setmCurrentAudio(null);
        audioPlayUtils.setCurAudioMessage(null);
    }

    @Subscribe
    public void onEvent(AudioStopEvent event){
        stopMusic();
    }
}
