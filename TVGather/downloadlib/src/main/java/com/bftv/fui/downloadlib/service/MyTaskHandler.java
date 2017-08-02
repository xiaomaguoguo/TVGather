package com.bftv.fui.downloadlib.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.bftv.fui.downloadlib.entity.LoadEntity;

/**
 * Created by MaZhihua on 2017/7/20.
 * 将最终结果反馈给UI,用来更新UI或者其它操作
 */
public class MyTaskHandler extends Handler {

    private DownloadTaskEntity downloadTaskEntity = null;

    public MyTaskHandler(DownloadTaskEntity downloadTaskEntity){
        super(Looper.getMainLooper()); // 回归主线程
        this.downloadTaskEntity = downloadTaskEntity;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what){

            case IHandlerState.START: // 开始下载
                if(!isCallbackNull()){
                    downloadTaskEntity.downloadCallback.downloadStart();
                }
                break;

            case IHandlerState.DOWNLOADING: // 正在下载
                if(!isCallbackNull()){
                    LoadEntity loadInfo = (LoadEntity) msg.obj;
                    downloadTaskEntity.downloadCallback.downloading(loadInfo);
                }
                break;

            case IHandlerState.SUCCESS: // 下载成功
                if(!isCallbackNull()){
                    DownloadManager.getInstance().delete(downloadTaskEntity.downloadUrl);
                    downloadTaskEntity.downloadCallback.downloadSuccess(downloadTaskEntity);
                }
                break;

            case IHandlerState.FAILED: // 下载失败
                if(!isCallbackNull()){
                    String failedCause = (String) msg.obj;
                    downloadTaskEntity.failedCause = failedCause;
                    downloadTaskEntity.downloadCallback.downloadFailed(downloadTaskEntity);
                }
                break;

        }
    }

    /**
     * 状态回调是否为空
     * @return
     */
    private boolean isCallbackNull(){
        if(downloadTaskEntity == null || downloadTaskEntity.downloadCallback == null){
            return true;
        }
        return false;
    }

    public interface IHandlerState{
        int START = 1;
        int DOWNLOADING = 2;
        int SUCCESS = 3;
        int FAILED = 4;
    }

}
