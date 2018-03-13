package com.chuxin.law.audioplayer.model;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/3/11
 * @describe 广播信息类
 */

public class AudioMessage implements Serializable {
    public static final String KEY = "com.zlm.hp.am.key";
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 播放进度
     */
    private long playProgress;

    /**
     * 总时长
     */
    private long playDuration;
    /**
     * 音频信息
     */
    private AudioInfo audioInfo;
    /**
     *
     */
    private String hash;

    public AudioMessage() {

    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public long getPlayProgress() {
        return playProgress;
    }

    public long getPlayDuration() {
        return playDuration;
    }

    public void setPlayDuration(long playDuration) {
        this.playDuration = playDuration;
    }

    public void setPlayProgress(long playProgress) {
        this.playProgress = playProgress;
    }

    public AudioInfo getAudioInfo() {
        return audioInfo;
    }

    public void setAudioInfo(AudioInfo audioInfo) {
        this.audioInfo = audioInfo;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "AudioMessage{" +
                "errorMsg='" + errorMsg + '\'' +
                ", playProgress=" + playProgress +
                ", audioInfo=" + audioInfo +
                ", hash='" + hash + '\'' +
                '}';
    }
}
