package activity;

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
import com.bftv.knothing.firsttv.R;

public class DownloadActivity2 extends Activity {
	private static final int PROCESSING = 1;
	private static final int FAILURE = -1;

	private TextView resultView;
	private Button btn_start;
	private Button btn_pause;
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
					Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_LONG).show();
				}
				break;
			case FAILURE:
				Toast.makeText(getApplicationContext(), "失败",Toast.LENGTH_LONG).show();
				break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download);
		resultView = (TextView) findViewById(R.id.tv_resouce_name);
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_pause = (Button) findViewById(R.id.btn_pause);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		ButtonClickListener listener = new ButtonClickListener();
		btn_start.setOnClickListener(listener);
		btn_pause.setOnClickListener(listener);
	}

	private final class ButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_start:
				String path = "http://dlied5.myapp.com/myapp/1104466820/sgame/2017_com.tencent.tmgp.sgame_h100_1.20.1.21.apk";
				String filename = path.substring(path.lastIndexOf('/') + 1);

				try {
					filename = URLEncoder.encode(filename, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				path = path.substring(0, path.lastIndexOf("/") + 1) + filename;
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// File savDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
					File savDir = Environment.getExternalStorageDirectory();
					download(path, savDir);
				} else {
					Toast.makeText(getApplicationContext(), "sdcarderror", Toast.LENGTH_LONG).show();
				}
				btn_start.setEnabled(false);
				btn_pause.setEnabled(true);
				break;

			case R.id.btn_pause:
				exit();
				Toast.makeText(getApplicationContext(),"Now thread is Stopping!!", Toast.LENGTH_LONG).show();
				btn_start.setEnabled(true);
				btn_pause.setEnabled(false);
				break;
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
					loader = new FileDownloader(getApplicationContext(), path,saveDir, 3);
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
