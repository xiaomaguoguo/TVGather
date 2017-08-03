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

    //需要用几个线程来下载该文件，默认为-1，底层实现为3个线程下载，如果嫌慢，可以自定义该值 ，且：必须大于0且大于3
    public int threadCount = -1;

}
