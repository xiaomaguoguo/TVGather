package activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bftv.knothing.firsttv.R;

import fragment.CateFragment;
import fragment.ConstraintLayoutFragment;
import fragment.SoundPoolFragment;
import fragment.TimeCountFragment;
import service.MyIntentService;

/**
 * Created by KNothing on 2017/4/14.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener, TimeCountFragment.OnFragmentInteractionListener{

    public static final String TAG = MainActivity.class.getSimpleName();

    private Button btnAccessClick,btnDownload,btnMultiService,btnConstraint,btnJs,btnOpenXiaoBanL,btnKeyEvent,btnFocus,btnTimeCount,btnRecycle,btnSoundPool,btnCate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        btnAccessClick = (Button) findViewById(R.id.btnAccessClick);
        btnDownload = (Button) findViewById(R.id.btnDownload);
        btnMultiService = (Button) findViewById(R.id.btnMultiService);
        btnConstraint =  (Button) findViewById(R.id.btnConstraint);
        btnJs =  (Button) findViewById(R.id.btnJs);
        btnOpenXiaoBanL =  (Button) findViewById(R.id.btnOpenXiaoBanL);
        btnKeyEvent =  (Button) findViewById(R.id.btnKeyEvent);
        btnFocus =  (Button) findViewById(R.id.btnFocus);
        btnTimeCount =  (Button) findViewById(R.id.btnTimeCount);
        btnRecycle =  (Button) findViewById(R.id.btnRecycle);
        btnSoundPool =  (Button) findViewById(R.id.btnSoundPool);
        btnCate =  (Button) findViewById(R.id.btnCate);

        btnAccessClick.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btnMultiService.setOnClickListener(this);
        btnConstraint.setOnClickListener(this);
        btnJs.setOnClickListener(this);
        btnOpenXiaoBanL.setOnClickListener(this);
        btnKeyEvent.setOnClickListener(this);
        btnFocus.setOnClickListener(this);
        btnTimeCount.setOnClickListener(this);
        btnRecycle.setOnClickListener(this);
        btnSoundPool.setOnClickListener(this);
        btnCate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnAccessClick: //辅助点击
                Toast.makeText(this, "被点击了", Toast.LENGTH_SHORT).show();
//                Intent intent =  new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//                startActivity(intent);
                enable(this);
                break;

            case R.id.btnDownload: // 断点下载
                startActivity(new Intent(this,DownloadActivity.class));
                break;

            case R.id.btnMultiService: // 一次启动多个服务
                for(int i=0;i<100;i++){
                    Intent intentservice = new Intent(this,MyIntentService.class);
                    intentservice.putExtra("mReceiver",new MyResultReceiver(new Handler()));
                    startService(intentservice);
                }
                break;

            case R.id.btnConstraint: // 约束布局
                commitFragment(ConstraintLayoutFragment.newInstance());
                break;

            case R.id.btnJs: // js互调
                Intent javaJsIntent = new Intent(this,JavaJsActivity.class);
                startActivity(javaJsIntent);
                break;

            case R.id.btnOpenXiaoBanL:
                PackageManager pm = getPackageManager();
                Intent xiaobanlong = pm.getLaunchIntentForPackage("com.xiaobanlong.main");
                if(xiaobanlong != null){
                    startActivity(xiaobanlong);
                }
                break;

            case R.id.btnKeyEvent: // keyevent测试
                break;

            case R.id.btnCate: // 美食
                commitFragment(CateFragment.newInstance());
                break;

            case R.id.btnSoundPool: // SoundPool测试
                commitFragment(SoundPoolFragment.newInstance());
                break;

            case R.id.btnFocus: //焦点测试
                Intent btnFocus = new Intent(this, FocusActivity.class);
                startActivity(btnFocus);
                break;

            case R.id.btnTimeCount: // 倒计时
                commitFragment(TimeCountFragment.newInstance(null));
                break;

            case R.id.btnRecycle: // RecycleView相关
                Intent recyclev = new Intent(this, RecycleViewActivity.class);
                startActivity(recyclev);
                break;

        }
    }

    public static void enable(Context context) {
        Settings.Secure.putString(context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
                context.getPackageName() + "/service.MyAccessibilityService");
        Settings.Secure.putString(context.getContentResolver(),
                Settings.Secure.ACCESSIBILITY_ENABLED,
                "1");
    }


    private void commitFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        ft.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG,"fragment识别到了");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){

            case KeyEvent.KEYCODE_DPAD_UP:
                Log.i(TAG,"上");
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:
                Log.i(TAG,"下");
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                Log.i(TAG,"左");
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                Log.i(TAG,"右");
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyResultReceiver extends ResultReceiver{

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            btnMultiService.setText(resultData.getString("count"));
        }
    }
}
