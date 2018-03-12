package com.chuxin.law.audioplayer.manage;

import android.content.Context;

import com.chuxin.law.audioplayer.util.AudioPlayUtils;

/**
 * @author wangcc
 * @date 2018/3/11
 * @describe 音频播放处理类
 */

public class AudioPlayerManager {

    /**
     *
     */
    private Context mContext;
    private AudioPlayUtils audioPlayUtils;
    /**
     * 正在播放
     */
    public static final int PLAYING = 0;
    /**
     * 暂停
     */
    public static final int PAUSE = 1;
    /**
     * 停止
     */
    public static final int STOP = 2;
    /**
     * 播放在线音乐
     */
    public static final int PLAYNET = 3;

    private static AudioPlayerManager _AudioPlayerManager;

    public AudioPlayerManager(Context context) {
        this.audioPlayUtils = AudioPlayUtils.getInstance();
        //
        this.mContext = context;
    }

    /**
     * @param context
     * @return
     */
    public static AudioPlayerManager getAudioPlayerManager(Context context) {

        if (_AudioPlayerManager == null) {
            _AudioPlayerManager = new AudioPlayerManager(context);
        }
        return _AudioPlayerManager;
    }

    /***
     * 初始化播放歌曲数据
     */
    public void initSongInfoData() {

    }

    /**
     *
     */
    private void resetData() {
        //清空之前的播放数据
        audioPlayUtils.setPlayStatus(STOP);
    }
}
