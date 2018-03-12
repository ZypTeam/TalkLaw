package com.chuxin.law.audioplayer.util;

import android.util.Log;

import com.chuxin.law.audioplayer.model.AudioInfo;
import com.jusfoun.baselibrary.Util.SharePrefenceUtils;
import com.chuxin.law.audioplayer.manage.AudioPlayerManager;
import com.chuxin.law.audioplayer.model.AudioMessage;

/**
 * @author wangcc
 * @date 2018/3/11
 * @describe
 */

public class AudioPlayUtils {

    /**
     * 当前歌曲
     */
    private AudioInfo mCurrentAudio;
    private AudioMessage curAudioMessage;

    private boolean playServiceForceDestroy;

    private AudioPlayUtils(){

    }

    private static class SingleHolder{
        private static AudioPlayUtils instance=new AudioPlayUtils();
    }

    public static AudioPlayUtils getInstance(){
        return SingleHolder.instance;
    }
    public int getPlayStatus() {
        return (int) SharePrefenceUtils.getInstance().getInt("playStatus_KEY", AudioPlayerManager.STOP);
    }

    public void setPlayStatus(int playStatus) {
        SharePrefenceUtils.getInstance().setInt("playStatus_KEY", playStatus);
    }

    public boolean isPlayServiceForceDestroy() {
        return playServiceForceDestroy;
    }

    public AudioInfo getmCurrentAudio() {
        return mCurrentAudio;
    }

    public void setmCurrentAudio(AudioInfo mCurrentAudio) {
        this.mCurrentAudio = mCurrentAudio;
    }

    public void setPlayServiceForceDestroy(boolean playServiceForceDestroy) {
        this.playServiceForceDestroy = playServiceForceDestroy;
    }

    public AudioMessage getCurAudioMessage() {
        if (curAudioMessage == null) {
            Log.e("audio","curAudioMessage为空，从本地获取");
        }
        return curAudioMessage;
    }
}
