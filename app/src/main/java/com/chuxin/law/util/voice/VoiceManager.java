package com.chuxin.law.util.voice;

import android.util.SparseArray;

import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.hyphenate.util.VoiceRecorder;

import java.io.IOException;

/**
 * @author wangcc
 * @date 2018/2/9
 * @describe
 */

public class VoiceManager {

    private final static String TAG = "VoiceManager";
    private static VoiceManager self;

    private SparseArray<VoicePlayer> mPlayer = null;

    private VoiceManagerListener mPlayListener = null;
    private VoiceManagerListener mRecListener = null;

    private static class SingelHolder{
        private static VoiceManager instance=new VoiceManager();
    }

    private VoiceManager() {
        mPlayer = new SparseArray<>();
    }

    public static VoiceManager getVoiceManager() {
        return SingelHolder.instance;
    }

    // **************play*******************

    public void setPlayListener(VoiceManagerListener listener) {
        this.mPlayListener = listener;

        //TODO: For Memory leak
        if (listener == null && mPlayer != null)
        {
            for (int i=0; i<mPlayer.size(); i++)
            {
                mPlayer.get(i).setVoiceManagerListener(null);
            }
        }
    }

    public void initPlaying(int index) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            temp.setVoiceManagerListener(mPlayListener);
        } else {
            temp = new VoicePlayer(TalkLawApplication.getInstance(), index);
            mPlayer.put(index, temp);
            temp.setVoiceManagerListener(mPlayListener);
        }
    }

//	public void setVolumeFade(int index, double duration, boolean isFadeIn)
//	{
//		VoicePlayer temp = mPlayer.get(index);
//		if (temp != null) {
//			temp.setVolumeFade(duration, isFadeIn);
//		}
//	}
    /**
     * 播放wav文件
     *
     * @param name
     *            待播放的文件名
     */
    public void initVoice(int index, String name) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            temp.initVoice(name);
        }
    }

    public void setVoiceLooping(int index, boolean loop) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            temp.setLooping(loop);
        }
    }

    public void startVoice(int index) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            temp.startVoice();
        }
    }

    /**
     * 是否播放
     */
    public boolean isPlaying(int index) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            return temp.isPlaying();
        }
        return false;
    }

    /**
     * 是否暂停
     */
    public boolean isPaused(int index) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            return temp.isPaused();
        }
        return false;
    }

    /**
     * 暂停播放
     */
    public void pauseVoice(int index) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            temp.pauseVoice();
        }
    }

    /**
     * resumeVoice
     */
    public void resumePlayingVoice(int index) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            temp.resumeVoice();
        }
    }

    /**
     * stopPlayingVoice
     */
    public boolean stopPlayingVoice(int index) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            return temp.stopVoice();
        }
        return false;
    }

    /**
     * setVolume
     */
    public void setPlayingVolume(int index, float vol) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            temp.setVolume(vol);
        }
    }

    /**
     * getVolume
     */
    public float getPlayingVolume(int index) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            return temp.getVolume();
        }
        return 0;
    }

    public String getPlayingVoicePath(int index) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            return temp.getPlayingVoicePath();
        }
        return "";
    }

    /**
     * seekPlayingPostion
     */
    public void seekPlayingPostion(int index, double millis) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            temp.seekPosition((int) millis);
        }
    }

    public void enableVolumeMute(int index)
    {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            temp.enableVolumeMute();
        }
    }

    public boolean getVolumeMuteFlag(int index)
    {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            return temp.getVolumeMuteFlag();
        }
        return false;
    }


    public void disableVolumeMute(int index)
    {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            temp.disableVolumeMute();
        }
    }

    /**
     * getPlayingCurrPostion
     */
    public long getPlayingCurrPostion(int index) {
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            return temp.getCurrentPosition();
        }
        return 0;
    }

    public long getLength(int index){
        VoicePlayer temp = mPlayer.get(index);
        if (temp != null) {
            return temp.getVoiceLength();
        }
        return 0;
    }
}
