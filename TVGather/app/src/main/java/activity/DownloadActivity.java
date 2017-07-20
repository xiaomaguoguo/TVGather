package activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bftv.fui.downloadlib.entity.LoadEntity;
import com.bftv.fui.downloadlib.service.DownloadCallback;
import com.bftv.fui.downloadlib.service.DownloadManager;
import com.bftv.fui.downloadlib.service.DownloadRunnable;
import com.bftv.fui.downloadlib.service.DownloadTaskEntity;
import com.bftv.knothing.firsttv.R;

import java.io.File;

public class DownloadActivity extends Activity {

    public static final String TAG = DownloadActivity.class.getSimpleName();

     // 固定存放下载的音乐的路径：SD卡目录下
     private static final String SD_PATH = "/mnt/sdcard/";

    //王者荣耀apk下载地址
     public static final String DOWNLOADURL = "http://dlied5.myapp.com/myapp/1104466820/sgame/2017_com.tencent.tmgp.sgame_h100_1.20.1.21.apk";

    private TextView tv_resouce_name = null;

     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.download);
         tv_resouce_name = (TextView) findViewById(R.id.tv_resouce_name);
     }


     /**
      * 响应开始下载按钮的点击事件
      */
     public void startDownload(View v) {
         String savePath = SD_PATH + "WZRY.apk";
         DownloadTaskEntity downloadTaskEntity = new DownloadTaskEntity();
         downloadTaskEntity.downloadUrl = DOWNLOADURL;
         downloadTaskEntity.savePath = savePath;
         downloadTaskEntity.downloadCallback = new DownloadCallback() {

             @Override
             public void downloadStart() {
                //可在此处直接操作UI,此处已回归主线程
                 Log.i(TAG,"开始下载");
             }

             @Override
             public void downloading(LoadEntity loadInfo) {
                //可在此处直接操作UI,此处已回归主线程
                 Log.i(TAG,"fileSize = " + loadInfo.getFileSize() + " ; completeSize = " + loadInfo.getComplete());
                 tv_resouce_name.setText("总：" + loadInfo.getFileSize() + " / "  + "已下载：" + loadInfo.getComplete());
             }

             @Override
             public void downloadSuccess(DownloadTaskEntity downloadTaskEntity) {
                 //可在此处直接操作UI,此处已回归主线程
                 Log.i(TAG,downloadTaskEntity.downloadUrl +"下载完成");
                 tv_resouce_name.setText("下载完成");
                 DownloadManager.getInstance().delete(downloadTaskEntity.downloadUrl);
                 Intent intent = new Intent();
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 intent.setAction(android.content.Intent.ACTION_VIEW);
                 intent.setDataAndType(Uri.fromFile(new File(downloadTaskEntity.savePath)),"application/vnd.android.package-archive");
                 startActivity(intent);
             }

             @Override
             public void downloadFailed(DownloadTaskEntity downloadTaskEntity) {
                //可在此处直接操作UI,此处已回归主线程
                 Toast.makeText(DownloadActivity.this, "下载失败，请看失败日志", Toast.LENGTH_SHORT).show();
                 Log.i(TAG,"下载失败，失败原因 ：" + downloadTaskEntity.failedCause);
             }

         };

         DownloadRunnable downloadRunnable = new DownloadRunnable(getApplicationContext(),downloadTaskEntity);
         Thread mThread = new Thread(downloadRunnable);
         mThread.start();
     };

     /**
      * 暂停下载
      */
     public void pauseDownload(View v) {
         DownloadManager.getInstance().pause(DOWNLOADURL);
     }

 }