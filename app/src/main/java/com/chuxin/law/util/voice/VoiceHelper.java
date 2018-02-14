package com.chuxin.law.util.voice;

import com.jusfoun.baselibrary.Util.LogUtil;

import static com.chuxin.law.util.voice.VoiceManagerConstant.VM_PLAY_COMPLETE;
import static com.chuxin.law.util.voice.VoiceManagerConstant.VM_PLAY_PREPARED;

/**
 * @author wangcc
 * @date 2018/2/9
 * @describe
 */

public class VoiceHelper {

    protected final String TAG = "VoiceHelper";

    public static final int MUSIC_INDEX = 0;

    private String path;
    private int BKLevel = 50; //默认为50，背景音乐的音量(原声: 100 - BKLevel)

    private VoiceManager mVoiceManager = VoiceManager.getVoiceManager();

    public static VoiceManager getVoiceManager() {
        return VoiceManager.getVoiceManager();
    }

    public int getBKLevel() {
        return BKLevel;
    }

    public void initAudio() {
        mVoiceManager.setPlayListener(new VoiceManagerListener() {
            @Override
            public void onRecordNotify(int event, byte[] buffer, int nSize, int res) {
            }

            @Override
            public void onPlayNotify(int event, int index, int res) {
                if (listener == null) {
                    return;
                }
                if (event == VM_PLAY_COMPLETE) {
                    listener.onComplete();
                } else if (event == VM_PLAY_PREPARED) {
                    listener.prepared();
                } else {
                    listener.onError();
                    LogUtil.e(TAG, "onPlayNotify,event VM_PLAY_FAIL,  index: " + index);
                }
            }
        });
        mVoiceManager.initPlaying(MUSIC_INDEX);
    }

    public void release() {
        VoiceHelper.getVoiceManager().setPlayListener(null);
    }

    public void initVoice(int index, String path) {
        this.path = path;
        mVoiceManager.initVoice(index, path);
    }

    public void startVoice() {
        LogUtil.i(TAG,
                ", isPlaying(MUSIC_INDEX): " + mVoiceManager.isPlaying(MUSIC_INDEX) +
                        ", getVolumeMuteFlag(MUSIC_INDEX): " + mVoiceManager.getVolumeMuteFlag(MUSIC_INDEX));

        mVoiceManager.startVoice(MUSIC_INDEX);
    }

    public boolean isPlay() {
        return mVoiceManager.isPlaying(MUSIC_INDEX);
    }

    public boolean isPause(){
        return mVoiceManager.isPaused(MUSIC_INDEX);
    }

    public void pauseVoice() {
        LogUtil.i(TAG, "voice pause");
        pauseVoice(MUSIC_INDEX);
    }

    public void pauseVoice(int index) {
        LogUtil.i(TAG, "voice pause index: " + index);
        mVoiceManager.pauseVoice(index);
    }

    public void stopVoice() {
        LogUtil.i(TAG, "voice stop ");
        mVoiceManager.stopPlayingVoice(MUSIC_INDEX);
    }

    public void resetVoice() {
        LogUtil.i(TAG, "voice reset ");
        resetVoice(MUSIC_INDEX);
    }

    public void resetVoice(int index) {
        LogUtil.i(TAG, "voice reset , index: " + index + "  music:" + path);
        mVoiceManager.stopPlayingVoice(index);
        mVoiceManager.initVoice(index, path);
    }

    public void seekVoice(long millis) {
        LogUtil.i(TAG, "seekVoice, sec: " + millis);
        if (millis == 0) {
            mVoiceManager.seekPlayingPostion(MUSIC_INDEX, millis);
        } else {
            mVoiceManager.seekPlayingPostion(MUSIC_INDEX, getPlayingCurrPostion() + millis);
        }

    }

    public void seek(long millis){
        mVoiceManager.seekPlayingPostion(MUSIC_INDEX,millis);
    }

    public void setBKLevel(int level) {
        if (level > 100) BKLevel = 100;
        else if (level < 0) BKLevel = 0;

        BKLevel = level;
        mVoiceManager.setPlayingVolume(MUSIC_INDEX, getVolume(MUSIC_INDEX, BKLevel));
    }

    private float getVolume(int index, int level) {
        float volume = 0;
        if (index == MUSIC_INDEX) {
            volume = level / 100f;
        }
        return volume;
    }

    public long getPlayingCurrPostion() {
        return mVoiceManager.getPlayingCurrPostion(MUSIC_INDEX);
    }

    public long getLength() {
        return mVoiceManager.getLength(MUSIC_INDEX);
    }

    public void setPath(String path) {
        this.path = path;
    }

    private OnPlayListener listener;

    public void setListener(OnPlayListener listener) {
        this.listener = listener;
    }

    public interface OnPlayListener {
        void onComplete();

        void onError();

        void prepared();
    }
}
