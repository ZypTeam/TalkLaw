package com.chuxin.law.audioplayer.util;

import android.util.Log;

import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.audioplayer.ResourceConstants;
import com.chuxin.law.audioplayer.model.AudioInfo;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.Util.SharePrefenceUtils;
import com.chuxin.law.audioplayer.manage.AudioPlayerManager;
import com.chuxin.law.audioplayer.model.AudioMessage;

import java.io.File;

import static com.jrmf360.rylib.wallet.JrmfWalletClient.getApplicationContext;

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
        if (mCurrentAudio == null) {
            LogUtil.e("curAudioInfo为空，从本地获取");
            String filePath = ResourceFileUtil.getFilePath(TalkLawApplication.getInstance(), ResourceConstants.PATH_CACHE_SERIALIZABLE, "curAudioInfo.ser");
            mCurrentAudio = (AudioInfo) SerializableObjUtil.readObj(filePath);
        }
        return mCurrentAudio;
    }

    public void setmCurrentAudio(final AudioInfo mCurrentAudio) {
        this.mCurrentAudio = mCurrentAudio;
        new Thread() {
            @Override
            public void run() {
                String filePath = ResourceFileUtil.getFilePath(TalkLawApplication.getInstance(), ResourceConstants.PATH_CACHE_SERIALIZABLE, "curAudioInfo.ser");
                if (mCurrentAudio != null) {
                    SerializableObjUtil.saveObj(filePath, mCurrentAudio);
                } else {
                    File file = new File(filePath);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }.start();
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

    public void setCurAudioMessage(AudioMessage curAudioMessage){
        this.curAudioMessage=curAudioMessage;
    }
}
