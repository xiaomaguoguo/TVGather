package com.bftv.fui.downloadlib.service;

/**
 * Created by MaZhihua on 2017/7/20.
 *
 * 实际需要下载的文件实体，包含如下信息：
 *
 * 1.下载状态回调
 * 2.下载请求的URL地址
 * 3.最终下载成功后保存的路径
 *
 */
public class DownloadTaskEntity {

    //下载回调
    public DownloadCallback downloadCallback = null;

    //需要下载的文件对应的url地址
    public String downloadUrl = null;

    //下载的文件最终保存的路径
    public String savePath = null;

    //如果下载失败，失败的原因保存在这里
    public String failedCause = null;

}
