package com.chuxin.law.comment;

import android.content.Context;

import com.wang.dblibrary.DaoMaster;
import com.wang.dblibrary.DaoSession;

/**
 * @author wangcc
 * @date 2017/11/17
 * @describe 数据库
 */

public class DaoInstance {

    private DaoMaster.DevOpenHelper devOpenHelper;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private static final class SingletonHolder{
        private static DaoInstance instance=new DaoInstance();
    }

    public static DaoInstance getInstance(){
        return SingletonHolder.instance;
    }


    private DaoInstance(){

    }

    public void regester(Context mContext){
        devOpenHelper = new DaoMaster.DevOpenHelper(mContext.getApplicationContext(), "test.db", null);
        daoMaster=new DaoMaster(devOpenHelper.getWritableDb());
        daoSession=daoMaster.newSession();
    }

    public DaoMaster.DevOpenHelper getDevOpenHelper() {
        if (devOpenHelper==null){
            throw  new IllegalArgumentException("not register db");
        }
        return devOpenHelper;
    }

    public DaoMaster getDaoMaster() {
        if (devOpenHelper==null){
            throw  new IllegalArgumentException("not register db");
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (devOpenHelper==null){
            throw  new IllegalArgumentException("not register db");
        }
        return daoSession;
    }

}
