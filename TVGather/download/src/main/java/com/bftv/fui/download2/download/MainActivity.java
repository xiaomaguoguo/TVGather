package com.bftv.fui.download2.download;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bftv.fui.download2.net.download.DownloadProgressListener;
import com.bftv.fui.download2.net.download.FileDownloader;

public class MainActivity extends Activity {
	private static final int PROCESSING = 1;
	private static final int FAILURE = -1;

	private EditText pathText;
	private TextView resultView;
	private Button downloadButton;
	private Button stopButton;
	private ProgressBar progressBar;

	private Handler handler = new UIHandler();

	private final class UIHandler extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PROCESSING:
				progressBar.setProgress(msg.getData().getInt("size"));
				float num = (float) progressBar.getProgress()
						/ (float) progressBar.getMax();
				int result = (int) (num * 100);
				resultView.setText(result + "%");
				if (progressBar.getProgress() == progressBar.getMax()) {
//					Toast.makeText(getApplicationContext(), R.string.success,
//							Toast.LENGTH_LONG).show();
				}
				break;
			case FAILURE:
//				Toast.makeText(getApplicationContext(), R.string.error,
//						Toast.LENGTH_LONG).show();
				break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
//		pathText = (EditText) findViewById(R.id.path);
//		resultView = (TextView) findViewById(R.id.resultView);
//		downloadButton = (Button) findViewById(R.id.downloadbutton);
//		stopButton = (Button) findViewById(R.id.stopbutton);
//		progressBar = (ProgressBar) findViewById(R.id.progressBar);
//		ButtonClickListener listener = new ButtonClickListener();
//		downloadButton.setOnClickListener(listener);
//		stopButton.setOnClickListener(listener);
	}

	private final class ButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
//			case R.id.downloadbutton:
//				String path = pathText.getText().toString();
//				String filename = path.substring(path.lastIndexOf('/') + 1);
//
//				try {
//					filename = URLEncoder.encode(filename, "UTF-8");
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//
//				path = path.substring(0, path.lastIndexOf("/") + 1) + filename;
//				if (Environment.getExternalStorageState().equals(
//						Environment.MEDIA_MOUNTED)) {
//					// File savDir =
//					// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
//					File savDir = Environment.getExternalStorageDirectory();
//					download(path, savDir);
//				} else {
//					Toast.makeText(getApplicationContext(), "sdcarderror", Toast.LENGTH_LONG).show();
//				}
//				downloadButton.setEnabled(false);
//				stopButton.setEnabled(true);
//				break;
//			case R.id.stopbutton:
//				exit();
//				Toast.makeText(getApplicationContext(),
//						"Now thread is Stopping!!", Toast.LENGTH_LONG).show();
//				downloadButton.setEnabled(true);
//				stopButton.setEnabled(false);
//				break;
			}
		}

		private DownloadTask task;

		private void exit() {
			if (task != null)
				task.exit();
		}

		private void download(String path, File savDir) {
			task = new DownloadTask(path, savDir);
			new Thread(task).start();
		}

		private final class DownloadTask implements Runnable {
			private String path;
			private File saveDir;
			private FileDownloader loader;

			public DownloadTask(String path, File saveDir) {
				this.path = path;
				this.saveDir = saveDir;
			}

			public void exit() {
				if (loader != null)
					loader.exit();
			}

			DownloadProgressListener downloadProgressListener = new DownloadProgressListener() {
				@Override
				public void onDownloadSize(int size) {
					Message msg = new Message();
					msg.what = PROCESSING;
					msg.getData().putInt("size", size);
					handler.sendMessage(msg);
				}
			};

			public void run() {
				try {
					loader = new FileDownloader(getApplicationContext(), path,
							saveDir, 3);
					progressBar.setMax(loader.getFileSize());
					loader.download(downloadProgressListener);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(handler.obtainMessage(FAILURE));
				}
			}
		}
	}

}
