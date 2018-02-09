package com.chuxin.law.util.voice;

/**
 * @author wangcc
 * @date 2018/2/9
 * @describe
 */

public interface VoiceManagerListener {
    void onRecordNotify(int event, byte[] buffer, int nSize, int res);
    void onPlayNotify(int event, int index, int res);
}
