package com.jusfoun.baselibrary.base;


import android.text.TextUtils;

import com.jusfoun.baselibrary.Util.LogUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Author  wangchenchen
 * CreateDate 2016/7/6.
 * Email wcc@jusfoun.com
 * Description 管理rxjava 相关代码生命周期
 */
public class RxManage {

    private CompositeSubscription mCompositeSubscription=new CompositeSubscription();

    public void add(Subscription m){
        mCompositeSubscription.add(m);
    }

    /**
     * 添加网络请求
     * @param observable
     * @param action1
     * @param error
     */
    public <T extends BaseModel> void add(Observable<T> observable, Action1<T> action1, final Action1<Throwable> error){
        mCompositeSubscription.add(
                observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(action1, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LogUtil.e("throwable",throwable.getMessage());
                                if (error!=null) {
                                    error.call(throwable);
                                }
                            }
                        })
        );
    }

    public void clear(){
        mCompositeSubscription.unsubscribe();
    }
}
