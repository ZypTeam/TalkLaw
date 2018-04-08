package com.chuxin.law.update;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.chuxin.law.R;
import com.jusfoun.baselibrary.Util.AppUtil;
import com.jusfoun.baselibrary.Util.IOUtil;
import com.jusfoun.baselibrary.base.UploadProgressModel;
import com.jusfoun.baselibrary.listener.DownloadProgressListener;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import rx.Subscriber;

/**
 * Created by wang on 2016/11/14.
 * 下载服务
 */

public class ApkDownloadService extends IntentService {

    public static final String NOTIFICATION_ID="notification_id";
    public static final String DOWNLOAD_URL ="download_url";

    private final static String UPDATE_SHAREDPREFERENCES = "update";

    private final static String STATE_KEY = "state";

    private final static String TIME_KEY = "time_key";

    private String filePath;
    private String downloadUrl;

    protected NotificationManager nm;
    protected NotificationCompat.Builder mBuilder;
    protected RemoteViews contentViews;
    private long startTime;
    private int usedTime;
    private int nofiId;

    public ApkDownloadService() {
        super("ApkDownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        contentViews = new RemoteViews(getPackageName(), R.layout.download_warn);
        //通过控件的Id设置属性
        contentViews.setImageViewResource(R.id.download_icon, R.drawable.ic_launcher);
        contentViews.setTextViewText(R.id.download_title,  getResources().getString(R.string.download_title));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("My notification")
                .setTicker("new message");
        //自动管理通知栏消息
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(pendingIntent);
        ///自定义布局
        mBuilder.setContent(contentViews);
        //使用默认提示音
        mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        downloadUrl = intent.getStringExtra(DOWNLOAD_URL);
        nofiId = intent.getIntExtra(NOTIFICATION_ID, -100000000);
        Log.e("TAG","downloadUrl:"+downloadUrl);
        //设置参数
        filePath = IOUtil.getDownloadPath(getApplicationContext());
        //开始下载
        download();

    }

    private void download(){
        DownloadProgressListener listener=new DownloadProgressListener() {
            @Override
            public void update(UploadProgressModel model) {
                //自定义显示布局,进度提示,新建remoteviews,防止卡顿
                contentViews = new RemoteViews(getPackageName(), R.layout.download_warn);
                int progress= (int) ((double)model.getBytesLength()/model.getContentLength()*100);
                usedTime= (int) ((System.currentTimeMillis()-startTime)/1000);
                if (usedTime==0) {
                    usedTime = 1;
                }
                int downloadSpeed= (int) (model.getBytesLength()/usedTime/1024);
                String progressTxt = String.format(getString(R.string.download_progress),progress,downloadSpeed);
                contentViews.setImageViewResource(R.id.download_icon, R.drawable.ic_launcher);
                contentViews.setTextViewText(R.id.download_progress_text, progressTxt);
                contentViews.setProgressBar(R.id.download_progressbar, 100, progress, false);
                contentViews.setTextViewText(R.id.download_title,  getResources().getString(R.string.download_title));
                mBuilder.setContent(contentViews);
                nm.notify(nofiId, mBuilder.build());

            }

        };
        File file= new File(filePath,"talklaw.apk");
        if (file.exists() && file.isFile()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new ApiDownload(listener,getString(R.string.url)).downloadFile(downloadUrl, file, new Subscriber() {

            @Override
            public void onStart() {
                super.onStart();
                startTime=System.currentTimeMillis();
                saveState(true,ApkDownloadService.this);

            }

            @Override
            public void onCompleted() {
                //下载成功
                downloadCompleted();
                Log.e("ApkDownload_onCompleted","onNext");
            }

            @Override
            public void onError(Throwable e) {
                //下载失败
                Log.e("ApkDownload_error",e.getMessage());
                contentViews.setTextViewText(R.id.download_progress_text, getResources().getString(R.string.download_fail));

                Intent myIntent = new Intent(ApkDownloadService.this, ApkDownloadService.class);
                myIntent.putExtra(ApkDownloadService.NOTIFICATION_ID, nofiId);
                myIntent.putExtra(ApkDownloadService.DOWNLOAD_URL, downloadUrl);
                PendingIntent contentIntent = PendingIntent.getService(ApkDownloadService.this, 0, myIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                contentViews.setOnClickPendingIntent(R.id.download_box, contentIntent);
                mBuilder.setContentIntent(contentIntent);
                mBuilder.setContent(contentViews);
                nm.notify(nofiId, mBuilder.build());
                saveState(false,ApkDownloadService.this);
            }
            @Override
            public void onNext(Object o) {
                Log.e("ApkDownload_onNext","onNext");
            }
        });
    }

    //下载成功
    private void downloadCompleted(){
        nm.cancel(nofiId);
        saveState(false,ApkDownloadService.this);
        AppUtil.installApk(getApplicationContext(),filePath);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    public static void saveState(boolean state, Context context) {
        SharedPreferences lastTimePreferences = context.getSharedPreferences(context.getPackageName()
                + UPDATE_SHAREDPREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = lastTimePreferences.edit();
        editor.putBoolean(STATE_KEY, state);
        if (state) {
            // 做处理 记录当前设置了下载开始时间
            Date date = new Date();
            long time = date.getTime();
            editor.putLong(TIME_KEY, time);
        }
        editor.commit();
    }

    public static boolean getState(Context context) {
        SharedPreferences lastTimePreferences = context.getSharedPreferences(context.getPackageName()
                + UPDATE_SHAREDPREFERENCES, Activity.MODE_PRIVATE);
        boolean state = lastTimePreferences.getBoolean(STATE_KEY, false);
        if (state) {
            long time = lastTimePreferences.getLong(TIME_KEY, -1);
            if (time > 0) {
                Date date = new Date();
                long nowTime = date.getTime();
                if ((nowTime - time) > (10 * 60 * 1000)) {
                    // 如果相隔 30分钟 则认为是下载失败或者是持久化数据出问题了 则直接返回false。
                    return false;
                }
            } else {
                // 如果无time 则是第一次 则直接返回false。
                return false;
            }
        }
        return state;
    }
}
