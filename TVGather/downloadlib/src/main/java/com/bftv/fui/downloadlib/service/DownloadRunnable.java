package com.bftv.fui.downloadlib.service;

import android.content.Context;
import android.os.Handler;
import android.util.ArrayMap;

import com.bftv.fui.downloadlib.entity.LoadInfo;

/**
 * Created by KNothing on 2017/7/19.
 */
public class DownloadRunnable implements Runnable {

    private Context mContext = null;

    private String urlStr = null;

    private String savePath = null;

    private int threadCount = 4;

    private Downloader downloader = null;

    private Handler mHandler = null;

    private ArrayMap<String, Downloader> downloaders = null;

    public DownloadRunnable(Context context,String urlstr, String savePath, Handler mHandler, ArrayMap<String, Downloader> downloaders){
        this.mContext = context;
        this.urlStr = urlstr;
        this.savePath = savePath;
        this.mHandler = mHandler;
        this.downloaders = downloaders;
    }

    @Override
    public void run() {
        executeStart();
        executeTask();
        executeAfter();
    }

    private void executeStart() {
//        Button btn_start=(Button)((View)v.getParent()).findViewById(R.id.btn_start);
//        Button btn_pause=(Button)((View)v.getParent()).findViewById(R.id.btn_pause);
//        btn_start.setVisibility(View.GONE);
//        btn_pause.setVisibility(View.VISIBLE);
    }

    private void executeTask() {
        downloader = downloaders.get(urlStr);
        if (downloader == null) {
            downloader = new Downloader(urlStr, savePath, threadCount, mContext, mHandler);
            downloaders.put(urlStr, downloader);
        }
        if (!downloader.isdownloading()){
            LoadInfo loadInfo = downloader.getDownloaderInfors();
//            bar.setMax(loadInfo.getFileSize());
//            bar.setProgress(loadInfo.getComplete());
            downloader.download();
        }
    }

    private void executeAfter() {
    }


}
