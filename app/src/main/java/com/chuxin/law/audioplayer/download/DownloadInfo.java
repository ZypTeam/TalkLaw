package com.chuxin.law.audioplayer.download;

import com.chuxin.law.audioplayer.model.AudioInfo;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/3/12
 * @describe 下载任务
 */

public class DownloadInfo implements Serializable {
    public static final String KEY = "com.zlm.hp.dli.key";
    /**
     * 歌曲id
     */
    private String dHash;

    /**
     * 下载大小
     */
    private long downloadedSize;
    /**
     * 歌曲信息
     */
    private AudioInfo audioInfo;

    public DownloadInfo() {

    }

    public String getDHash() {
        return dHash;
    }

    public void setDHash(String dHash) {
        this.dHash = dHash;
    }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public AudioInfo getAudioInfo() {
        return audioInfo;
    }

    public void setAudioInfo(AudioInfo audioInfo) {
        this.audioInfo = audioInfo;
    }

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "dHash='" + dHash + '\'' +
                ", downloadedSize=" + downloadedSize +
                ", audioInfo=" + audioInfo +
                '}';
    }
}
