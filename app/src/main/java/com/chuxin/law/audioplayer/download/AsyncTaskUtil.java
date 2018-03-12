package com.chuxin.law.audioplayer.download;

import android.os.AsyncTask;

/**
 * @author wangcc
 * @date 2018/3/11
 * @describe
 */

public class AsyncTaskUtil extends AsyncTask<String, Integer, Void> {

    private AsyncTaskListener mAsyncTaskListener;

    private int sleepTime = 0;

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mAsyncTaskListener != null) {
            mAsyncTaskListener.onPostExecute();
        }
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mAsyncTaskListener != null) {
            mAsyncTaskListener.doInBackground();
        }

        return null;

    }

    public interface AsyncTaskListener {
        void doInBackground();

        void onPostExecute();

    }

    public void setAsyncTaskListener(AsyncTaskListener mAsyncTaskListener) {
        this.mAsyncTaskListener = mAsyncTaskListener;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }
}
