package com.bftv.fui.downloadlib.service;

import android.content.Context;
import android.util.ArrayMap;

import com.bftv.fui.downloadlib.entity.LoadEntity;

/**
 * Created by KNothing on 2017/7/19.
 * 下载任务具体的实现逻辑类
 */
public class DownloadRunnable implements Runnable {

    private Context mContext = null;

    private Downloader downloader = null;

    private DownloadTaskEntity downloadTaskEntity = null;

    public DownloadRunnable(Context context,DownloadTaskEntity downloadTaskEntity){
        this.mContext = context;
        this.downloadTaskEntity = downloadTaskEntity;
    }

    @Override
    public void run() {
        downloader = DownloadManager.getInstance().get(downloadTaskEntity.downloadUrl);
        if (downloader == null) {
            downloader = new Downloader(mContext,downloadTaskEntity);
            DownloadManager.getInstance().put(downloadTaskEntity.downloadUrl, downloader);
        }


        if (!downloader.isdownloading()){
            downloader.getDownloaderInfors();
            downloader.download();
        }
    }

}
