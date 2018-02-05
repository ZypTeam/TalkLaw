package com.chuxin.law.ui.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaoyapeng
 * @version create time:18/2/521:19
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class ThreadUtil {
    private static ThreadUtil ourInstance = new ThreadUtil();
    ExecutorService SingleThreadExecutor;
    ExecutorService fixThreadExecutor;

    public static ThreadUtil getInstance() {
        return ourInstance;
    }

    private ThreadUtil() {
        SingleThreadExecutor = Executors.newSingleThreadExecutor();
        fixThreadExecutor = Executors.newFixedThreadPool(4);
    }

    public ExecutorService getSingleThreadExecutor() {
        return SingleThreadExecutor;
    }

    public ExecutorService getFixThreadExecutor() {
        return fixThreadExecutor;
    }
}
