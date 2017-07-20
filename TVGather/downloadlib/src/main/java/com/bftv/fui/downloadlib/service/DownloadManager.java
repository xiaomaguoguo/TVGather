package com.bftv.fui.downloadlib.service;

import android.util.ArrayMap;

/**
 * Created by MaZhihua on 2017/7/20.
 * 下载任务管理器
 */
public class DownloadManager {

    private static DownloadManager downloadManager = null;

    private static Object lock = new Object();

    // 存放各个下载器
    private ArrayMap<String, Downloader> downloaders = new ArrayMap<>();

    public static DownloadManager getInstance(){
        if(downloadManager == null){
            synchronized (lock){
                if(downloadManager == null){
                    downloadManager = new DownloadManager();
                }
            }
        }
        return downloadManager;
    }

    public Downloader get(String downloadUrl){
        return downloadUrl != null && !downloadUrl.isEmpty() ? downloaders.get(downloadUrl) : null;
    }

    public void put(String downloadUrl,Downloader downloader){
        downloaders.put(downloadUrl,downloader);
    }


    /**
     * 删除下载完成或废弃的任务
     * @param downloadUrl
     */
    public void delete(String downloadUrl){
        downloaders.get(downloadUrl).delete(downloadUrl);
        downloaders.get(downloadUrl).reset();
        downloaders.remove(downloadUrl);
    }

    public void pause(String downloadUrl){
        if(downloaders.get(downloadUrl) == null){
            return;
        }
        downloaders.get(downloadUrl).pause();
    }

    public void release(){
        downloaders.clear();
    }

}
