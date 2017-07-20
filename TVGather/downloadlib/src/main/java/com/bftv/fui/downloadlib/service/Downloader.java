package com.bftv.fui.downloadlib.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.bftv.fui.downloadlib.database.Dao;
import com.bftv.fui.downloadlib.entity.DownloadEntity;
import com.bftv.fui.downloadlib.entity.LoadEntity;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Downloader {

    private int threadcount = 3;// 线程数,默认为3个线程去下载文件,太多就浪费了,没必要
    private int fileSize;// 所要下载的文件的大小
    private List<DownloadEntity> infos;// 存放下载信息类的集合

    //定义三种下载的状态：初始化状态，正在下载状态，暂停状态
    private static final int INIT = 1;
    private static final int DOWNLOADING = 2;
    private static final int PAUSE = 3;
    private int state = INIT;
    private Dao mDao = null;

    private DownloadTaskEntity downloadTaskEntity = null;

    private MyTaskHandler myTaskHandler = null;

    public Downloader(Context context, DownloadTaskEntity downloadTaskEntity) {
        this.downloadTaskEntity = downloadTaskEntity;
        this.mDao = new Dao(context);
        this.myTaskHandler = new MyTaskHandler(downloadTaskEntity);
    }

    /**
     * 判断是否正在下载
     */
    public boolean isdownloading() {
        return state == DOWNLOADING;
    }

    /**
     * 得到downloader里的信息
     * 首先进行判断是否是第一次下载，如果是第一次就要进行初始化，并将下载器的信息保存到数据库中
     * 如果不是第一次下载，那就要从数据库中读出之前下载的信息（起始位置，结束为止，文件大小等），并将下载信息返回给下载器
     */
    public LoadEntity getDownloaderInfors() {
        if (isFirst(downloadTaskEntity.downloadUrl)) {
            Log.v("TAG", "isFirst");
            init();
            int range = fileSize / 1024 / 1024 > 10  ? fileSize / threadcount : fileSize; // 大于10M用多线程下载，小于10M用单线程
            infos = new ArrayList<>();
            for (int i = 0; i < threadcount - 1; i++) {
                DownloadEntity info = new DownloadEntity(i, i * range, (i + 1) * range - 1, 0, downloadTaskEntity.downloadUrl);
                infos.add(info);
            }
            DownloadEntity info = new DownloadEntity(threadcount - 1, (threadcount - 1) * range, fileSize - 1, 0, downloadTaskEntity.downloadUrl);
            infos.add(info);
            //保存infos中的数据到数据库
            mDao.saveInfos(infos);
            //创建一个LoadInfo对象记载下载器的具体信息
            LoadEntity loadInfo = new LoadEntity(fileSize, 0, downloadTaskEntity.downloadUrl);
            return loadInfo;
        } else {
            //得到数据库中已有的urlstr的下载器的具体信息
            infos = mDao.getInfos(downloadTaskEntity.downloadUrl);
            Log.v("TAG", "not isFirst size=" + infos.size());
            int size = 0;
            int compeleteSize = 0;
            for (DownloadEntity info : infos) {
                compeleteSize += info.getCompeleteSize();
                size += info.getEndPos() - info.getStartPos() + 1;
            }
            return new LoadEntity(size, compeleteSize, downloadTaskEntity.downloadUrl);
        }
    }

    /**
     * 初始化
     */
    private void init() {
        try {
            URL url = new URL(downloadTaskEntity.downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            fileSize = connection.getContentLength();

            File file = new File(downloadTaskEntity.savePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 本地访问文件
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(fileSize);
            accessFile.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否是第一次 下载
     */
    private boolean isFirst(String urlstr) {
        return mDao.isHasInfors(urlstr);
    }

    /**
     * 利用线程开始下载数据
     */
    public void download() {
        if (infos != null) {
            if (state == DOWNLOADING){
                return;
            }
            state = DOWNLOADING;
            for (DownloadEntity info : infos) {
                new MyThread(info.getThreadId(), info.getStartPos(),
                        info.getEndPos(), info.getCompeleteSize(),
                        info.getUrl()).start();
            }
        }
    }

    public class MyThread extends Thread {
        private int threadId;
        private int startPos;
        private int endPos;
        private int compeleteSize;
        private String urlstr;

        public MyThread(int threadId, int startPos, int endPos,
                        int compeleteSize, String urlstr) {
            this.threadId = threadId;
            this.startPos = startPos;
            this.endPos = endPos;
            this.compeleteSize = compeleteSize;
            this.urlstr = urlstr;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile randomAccessFile = null;
            InputStream is = null;
            try {
                //告诉UI开始下载了
                Message startMsg = myTaskHandler.obtainMessage();
                startMsg.what = MyTaskHandler.IHandlerState.START;
                myTaskHandler.sendMessage(startMsg);

                URL url = new URL(urlstr);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                // 设置范围，格式为Range：bytes x-y;
                connection.setRequestProperty("Range", "bytes=" + (startPos + compeleteSize) + "-" + endPos);

                randomAccessFile = new RandomAccessFile(downloadTaskEntity.savePath, "rwd");
                randomAccessFile.seek(startPos + compeleteSize);
                // 将要下载的文件写到保存在保存路径下的文件中
                is = connection.getInputStream();
                byte[] buffer = new byte[4096];
                int length = -1;
                while ((length = is.read(buffer)) != -1) {
                    randomAccessFile.write(buffer, 0, length);
                    compeleteSize += length;
                    // 更新数据库中的下载信息
                    mDao.updataInfos(threadId, compeleteSize, urlstr);
                    // 用消息将下载信息传给进度条，对进度条进行更新
                    LoadEntity loadEntity = getDownloaderInfors();
                    Message execuMsg = myTaskHandler.obtainMessage();
                    if(loadEntity.getComplete() == loadEntity.getFileSize()){ // 下载完成
                        execuMsg.what = MyTaskHandler.IHandlerState.SUCCESS;
                    }else{ // 正在下载
                        execuMsg.what = MyTaskHandler.IHandlerState.DOWNLOADING;
                        execuMsg.obj = loadEntity;
                        execuMsg.arg1 = length; // 下载过程中，可以用此变量更新进度条
                    }
                    //发送消息
                    execuMsg.sendToTarget();

                    if (state == PAUSE) {
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Message errMsg = myTaskHandler.obtainMessage();
                errMsg.obj = e.toString();
                errMsg.sendToTarget();
            }
        }
    }

    //删除数据库中urlstr对应的下载器信息
    public void delete(String urlstr) {
        mDao.delete(urlstr);
    }

    //设置暂停
    public void pause() {
        state = PAUSE;
    }

    //重置下载状态
    public void reset() {
        state = INIT;
    }
}