package activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

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

    private Button btnMultiService,btnConstraint,btnJs,btnOpenXiaoBanL,btnKeyEvent,btnFocus,btnTimeCount,btnRecycle,btnSoundPool,btnCate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        btnMultiService = findViewById(R.id.btnMultiService);
        btnConstraint =  findViewById(R.id.btnConstraint);
        btnJs =  findViewById(R.id.btnJs);
        btnOpenXiaoBanL =  findViewById(R.id.btnOpenXiaoBanL);
        btnKeyEvent =  findViewById(R.id.btnKeyEvent);
        btnFocus =  findViewById(R.id.btnFocus);
        btnTimeCount =  findViewById(R.id.btnTimeCount);
        btnRecycle =  findViewById(R.id.btnRecycle);
        btnSoundPool =  findViewById(R.id.btnSoundPool);
        btnCate =  findViewById(R.id.btnCate);

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
