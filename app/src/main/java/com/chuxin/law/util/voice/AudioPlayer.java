package com.chuxin.law.util.voice;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wangcc
 * @date 2018/2/9
 * @describe
 */

public class AudioPlayer implements MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnPreparedListener {
    public MediaPlayer mediaPlayer;
    private SeekBar skbProgress;
    private Timer mTimer = new Timer();

    public AudioPlayer(SeekBar skbProgress) {
        this.skbProgress = skbProgress;
        initView();
    }

    private void initView() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.e("mediaPlayer", "onCompletion");
                }
            });
        } catch (Exception e) {
            Log.e("mediaPlayer", "error", e);
        }
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /*******************************************************
     * 通过定时器和Handler来更新进度条
     ******************************************************/
    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                if (mediaPlayer == null)
                    return;
                if (mediaPlayer.isPlaying() && !skbProgress.isPressed()) {
                    handleProgress.sendEmptyMessage(0);
                }
            } catch (Exception e) {

            }

        }
    };

    Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {

            if (mediaPlayer == null) {
                handleProgress.removeMessages(0);
                return;
            }
            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();

            if (duration > 0) {
                long pos = skbProgress.getMax() * position / duration;

                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    /*TransceiverAudioEvent event = new TransceiverAudioEvent(-1);
                    event.setState(TransceiverAudioEvent.STATE_TIME);
                    event.setCurrentTime(dealTime(position));
                    event.setTotalTime(dealTime(duration));
                    EventBus.getDefault().post(event);*/
                    skbProgress.setProgress((int) pos);
                }
            }
        }

        ;
    };
    //*****************************************************


    public void playUrl(String videoUrl) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepareAsync();//prepare之后自动播放
            //mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void playUrlAsync(String videoUrl) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepareAsync();//prepare之后自动播放
            //mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void pause() {
        if (mediaPlayer != null)
            mediaPlayer.pause();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            /*TransceiverAudioEvent event = new TransceiverAudioEvent(-1);
            event.setState(TransceiverAudioEvent.STATE_STOP);
            EventBus.getDefault().post(event);*/
        }
    }

    public void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            skbProgress=null;
            mTimerTask=null;
        }
    }

    @Override
    /**
     * 通过onPrepared播放
     */
    public void onPrepared(MediaPlayer arg0) {
        arg0.start();
    }


    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
        skbProgress.setSecondaryProgress(bufferingProgress);
    }




    public void restore() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    private String dealTime(int time) {
        time = time / 1000;
        if (time < 60) {
            if (time < 10) {
                return "00:0" + time;
            }
            return "00:" + time;
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


    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            if (mediaPlayer != null)
                mediaPlayer.seekTo(progress);
        }
    }
}
