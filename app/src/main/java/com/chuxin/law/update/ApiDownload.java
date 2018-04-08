package com.chuxin.law.update;

import com.chuxin.law.common.ApiService;
import com.jusfoun.baselibrary.Util.IOUtil;
import com.jusfoun.baselibrary.listener.DownloadProgressListener;
import com.jusfoun.baselibrary.net.DownloadProgressInterceptor;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wang on 2016/11/14.
 */

public class ApiDownload {

    private int TIMEOUT = 10000;
    public Retrofit retrofit;

    public ApiDownload(DownloadProgressListener listener,String url){

        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(new DownloadProgressInterceptor(listener))
                .retryOnConnectionFailure(true)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public void downloadFile(String url, final File file, Subscriber subscriber) {
        retrofit.create(ApiService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody body) {
                        return body.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        IOUtil.inputStreamToFile(inputStream,file);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
