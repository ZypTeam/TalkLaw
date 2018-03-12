package com.chuxin.law.audioplayer.download;

import java.io.Serializable;

/**
 * @author wangcc
 * @date 2018/3/11
 * @describe 下载任务
 */

public class DownloadMessage implements Serializable {
    public static final String KEY = "com.zlm.hp.dm.key";
    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务hash
     */
    private String taskHash;

    public DownloadMessage() {

    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskHash() {
        return taskHash;
    }

    public void setTaskHash(String taskHash) {
        this.taskHash = taskHash;
    }

    @Override
    public String toString() {
        return "DownloadMessage{" +
                "errorMsg='" + errorMsg + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskHash='" + taskHash + '\'' +
                '}';
    }
}
