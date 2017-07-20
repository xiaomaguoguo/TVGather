package com.bftv.fui.downloadlib.service;

import com.bftv.fui.downloadlib.entity.LoadEntity;

/**
 * Created by MaZhihua on 2017/7/20.
 * 下载进度回调
 */

public interface DownloadCallback {

    /**
     * 开始下载
     */
    void downloadStart();

    /**
     * 正在下载
     */
    void downloading(LoadEntity loadInfo);

    /**
     * 下载成功
     */
    void downloadSuccess(DownloadTaskEntity downloadTaskEntity);


    /**
     * 下载失败
     */
    void downloadFailed(DownloadTaskEntity downloadTaskEntity);

}
