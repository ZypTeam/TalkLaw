package com.chuxin.law.util.voice;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;

import com.jusfoun.baselibrary.Util.LogUtil;

import static com.chuxin.law.util.voice.VoiceManagerConstant.VM_PLAY_PREPARED;

/**
 * @author wangcc
 * @date 2018/2/9
 * @describe
 */

public class VoicePlayer {


    private String TAG = "VoicePlayer";

    private MediaPlayer mediaPlayer;

    private static final int STATUS_PREPARED=0;
    private static final int STATUS_PLAY=1;
    private static final int STATUS_PAUSE=2;
    private static final int STATUS_SEEK=3;

    private int status=STATUS_PREPARED;

    private boolean isPlaying = false; // 播放状态
    private boolean isPaused = false;
    private boolean isLooping = false;
    private boolean resumeMainMusicPlayer = false;
    private boolean intercept = false;
    private String voicePath;
    private float mCurrVolume = 0.5f;
    //private OnPlayCompleteListener onPlayCompleteListener;
    //private OnPlayCompleteListener prePlayCompleteListener;
    private int mIndex;
    VoiceManagerListener mListener = null;
    private boolean isMute = false;
    private Context mContext;

    private long duration;

    public VoicePlayer(Context context, int index) {
        mContext = context;
        mIndex = index;
        TAG = TAG + "_" + index;
    }

    public boolean getVolumeMuteFlag() {
        return isMute;
    }

    public void enableVolumeMute() {
        isMute = true;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(0.0f, 0.0f);
        }
    }

    public void disableVolumeMute() {
        isMute = false;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(mCurrVolume, mCurrVolume);
        }
    }

    public void setVoiceManagerListener(VoiceManagerListener listener) {
        this.mListener = listener;
    }

    public void setVolume(float vol) {
        if (mediaPlayer != null && !isMute) {
            //LogUtil.i(TAG, "set volume " + vol);
            mediaPlayer.setVolume(vol, vol);
            mCurrVolume = vol;
        }
    }

    public float getVolume() {
        return mCurrVolume;
    }

    public void seekPosition(int msec) {
        if (mediaPlayer != null) {
            status=STATUS_SEEK;
            mediaPlayer.seekTo(msec);
        }
    }

    public long getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void initMusic(String path) {
        try {
            if (intercept) {
                if (mListener != null) {
                    mListener.onPlayNotify(VoiceManagerConstant.VM_PLAY_COMPLETE, mIndex, 0);
                }
                return;
            }
            if (TextUtils.isEmpty(path)) {
                LogUtil.e(TAG, path);
                if (mListener != null) {
                    mListener.onPlayNotify(VoiceManagerConstant.VM_PLAY_COMPLETE, mIndex, 0);
                }
                return;
            }
            voicePath = path;
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setLooping(false);
                setVolume(mCurrVolume);

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer iMediaPlayer) {
                        LogUtil.i(TAG, "voice player prepared, path:" + voicePath);
                    }
                });

            } else {
                if (isPaused() || isPlaying()) {
                    if (resumeMainMusicPlayer) {
                        resumeMainMusicPlayer = stopVoice(true, true);
                    }
                    if (mListener != null) {
                        mListener.onPlayNotify(VoiceManagerConstant.VM_PRE_PLAY_COMPLETE, mIndex, 0);
                    }
                }
                mediaPlayer.reset();
            }
            //LogUtil.w(LogUtil.DANNY_TEST_TAG, "test 201612301700, path: " + path);

            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            // 设置播放结束时监听
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer iMediaPlayer) {
                    LogUtil.i(TAG, "GpuImageContainer voice completion, start" +
                            " path: " + voicePath +
                            " isPlaying:" + isPlaying +
                            " mIndex:" + mIndex +
                            " isLooping:" + isLooping
                    );
                    if (!isLooping && isPlaying) {
                        isPlaying = false;
                    }
                    if (mListener != null) {
                        mListener.onPlayNotify(VoiceManagerConstant.VM_PLAY_COMPLETE, mIndex, 0);
                    }

                    LogUtil.i(TAG, "GpuImageContainer voice completion, end  " +
                            " path: " + voicePath +
                            " isPlaying:" + isPlaying +
                            " mIndex:" + mIndex +
                            " isLooping:" + isLooping
                    );
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer iMediaPlayer, int i, int i1) {
                    if (isPlaying) {
                        isPlaying = false;
                    }
                    if (mListener != null) {
                        mListener.onPlayNotify(VoiceManagerConstant.VM_PLAY_COMPLETE, mIndex, 0);
                    }
                    LogUtil.e(TAG, "voice error, what:" + i + "  extra:" + i1 + " mIndex:" + mIndex +
                            " path: " + voicePath
                    );
                    return false;
                }
            });
        } catch (Exception e) {
            if (mListener != null) {
                mListener.onPlayNotify(VoiceManagerConstant.VM_PLAY_FAIL, mIndex, 0);
            }
            LogUtil.e(TAG, e);
        }
        if (isMute) {
            enableVolumeMute();
        }
    }

    public void initVoice(String path) {
        try {
            if (intercept) {
                if (mListener != null) {
                    mListener.onPlayNotify(VoiceManagerConstant.VM_PLAY_COMPLETE, mIndex, 0);
                }
                return;
            }
            if (TextUtils.isEmpty(path)) {
                LogUtil.e(TAG, path);
                if (mListener != null) {
                    mListener.onPlayNotify(VoiceManagerConstant.VM_PLAY_COMPLETE, mIndex, 0);
                }
                return;
            }
            voicePath = path;
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setLooping(false);
                setVolume(mCurrVolume);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        duration=mediaPlayer.getDuration();
                        if (mListener!=null){
                            mListener.onPlayNotify(VM_PLAY_PREPARED,0,0);
                        }
                        if (mp!=null){
                            //seek为异步，防止先播放后seek,seek完成后根据状态重新设置播放或者暂停
                            mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                                @Override
                                public void onSeekComplete(MediaPlayer mp) {
                                    if (mp.isPlaying()){
                                        if (status==STATUS_SEEK||status==STATUS_PAUSE) {
                                            mp.pause();
                                        }else if (status==STATUS_PLAY){
                                            mp.start();
                                        }
                                    }
                                }
                            });
                        }
                        LogUtil.i(TAG, "voice player prepared, path:" + voicePath);
                    }
                });

            } else {
                if (isPaused() || isPlaying()) {
                    if (resumeMainMusicPlayer) {
                        resumeMainMusicPlayer = stopVoice(true, true);
                    }
                    if (mListener != null) {
                        mListener.onPlayNotify(VoiceManagerConstant.VM_PRE_PLAY_COMPLETE, mIndex, 0);
                    }
                }
                mediaPlayer.reset();
            }
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();

            // 设置播放结束时监听
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    LogUtil.i(TAG, "GpuImageContainer voice completion, start" +
                            " path: " + voicePath +
                            " isPlaying:" + isPlaying +
                            " mIndex:" + mIndex +
                            " isLooping:" + isLooping
                    );
                    if (!isLooping && isPlaying) {
                        isPlaying = false;
                    }
                    if (mListener != null) {
                        mListener.onPlayNotify(VoiceManagerConstant.VM_PLAY_COMPLETE, mIndex, 0);
                    }

                    LogUtil.i(TAG, "GpuImageContainer voice completion, end  " +
                            " path: " + voicePath +
                            " isPlaying:" + isPlaying +
                            " mIndex:" + mIndex +
                            " isLooping:" + isLooping
                    );
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (isPlaying) {
                        isPlaying = false;
                    }
                    if (mListener != null) {
                        mListener.onPlayNotify(VoiceManagerConstant.VM_PLAY_COMPLETE, mIndex, 0);
                    }
                    LogUtil.e(TAG, "voice error, what:" + what + "  extra:" + extra + " mIndex:" + mIndex +
                            " path: " + voicePath
                    );
                    return false;
                }
            });
        } catch (Exception e) {
            if (mListener != null) {
                mListener.onPlayNotify(VoiceManagerConstant.VM_PLAY_FAIL, mIndex, 0);
            }
            LogUtil.e(TAG, e);
        }
        if (isMute) {
            enableVolumeMute();
        }
    }

    public void setLooping(boolean loop) {
        isLooping = loop;
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(loop);
        }
    }

    public void startVoice() {
        if (mediaPlayer != null) {
            status=STATUS_PLAY;
            isPaused = false;
            isPlaying = true;
            mediaPlayer.start();
        }
    }

    public void pauseVoice() {
        if (mediaPlayer != null && mediaPlayer.isPlaying() && !isPaused) {
            try {
                status=STATUS_PAUSE;
                isPaused = true;
                isPlaying = false;
                mediaPlayer.pause();
            } catch (Exception e) {
                LogUtil.e(TAG, e);
            }
        }
    }

    public void resumeVoice() {
        if (mediaPlayer != null && isPaused) {
            try {
                isPaused = false;
                isPlaying = true;
                mediaPlayer.start();
            } catch (Exception e) {
                LogUtil.e(TAG, e);
            }
        }
    }

    public boolean stopVoice(boolean... noResumeMainPlayer) {
        voicePath = null;
        boolean dontResumeMainPlayer = false;
        boolean joinMainMusicPlayerStatus = false;
        if (noResumeMainPlayer.length > 0) {
            dontResumeMainPlayer = noResumeMainPlayer[0];
        }
        if (mediaPlayer != null) {
            try {
                isPlaying = false;
                isPaused = false;
                mediaPlayer.stop();

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (noResumeMainPlayer.length < 2) {
                if (mListener != null) {
                    mListener.onPlayNotify(VoiceManagerConstant.VM_PLAY_COMPLETE, mIndex, 0);
                }
            }

        }
        return joinMainMusicPlayerStatus;
    }

    public long getVoiceLength() {
        return duration;
    }

    public synchronized void interceptPlay(boolean isBlock) {
        this.intercept = isBlock;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public String getPlayingVoicePath() {
        return voicePath;
    }
}
