package com.bftv.fui.downloadlib.service;

import android.app.ActivityManager;
import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by KNothing on 2015/12/11.
 * Description: Multiply thread manager utils
 */
public class TaskUtils {
    /**
     * 每次只执行一个任务的线程池
     */
    private static ExecutorService sSingleTaskExecutor;

    /**
     * 每次执行限定个数个任务的线程池
     */
    private static ExecutorService sLimitedTaskExecutor;

    private static final Object OBJECT = new Object();


    /**
     * 每次只能执行一个线程的线程池
     *
     * @return
     */
    public synchronized static ExecutorService getSingleTaskExecutor() {
        if (sSingleTaskExecutor == null) {
            synchronized (OBJECT) {
                if (sSingleTaskExecutor == null) {
                    sSingleTaskExecutor = Executors.newSingleThreadExecutor();
                }
            }
        }
        if (sSingleTaskExecutor.isShutdown()) {
            sSingleTaskExecutor = null;
            return getSingleTaskExecutor();
        }
        return sSingleTaskExecutor;
    }

    /**
     * 获取固定大小的线程池
     *
     * @return
     */
    public synchronized static ExecutorService getLimitedTaskExecutor() {
        if (sLimitedTaskExecutor == null) {
            synchronized (OBJECT) {
                if (sLimitedTaskExecutor == null) {
//                    sLimitedTaskExecutor = Executors.newFixedThreadPool((DeviceUtils.getCPUCores() << 2) + 1);
                    sLimitedTaskExecutor = Executors.newCachedThreadPool();
                }
            }
        }
        if (sLimitedTaskExecutor.isShutdown()) {
            sLimitedTaskExecutor = null;
            return getLimitedTaskExecutor();
        }
        return sLimitedTaskExecutor;
    }

    public static void releaseExecutor() {
        releaseService(sSingleTaskExecutor);
        releaseService(sLimitedTaskExecutor);
    }

    private static void releaseService(ExecutorService service) {
        if (service != null && !service.isShutdown()) {
            service.shutdown();
        }
    }

}
