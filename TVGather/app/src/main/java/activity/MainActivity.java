package activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.bftv.knothing.firsttv.R;

import fragment.CateFragment;
import fragment.SoundPoolFragment;
import fragment.TimeCountFragment;

/**
 * Created by KNothing on 2017/4/14.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener, TimeCountFragment.OnFragmentInteractionListener{

    public static final String TAG = MainActivity.class.getSimpleName();

    private Button btnOpenXiaoBanL,btnKeyEvent,btnFocus,btnTimeCount,btnRecycle,btnSoundPool,btnCate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        btnOpenXiaoBanL = (Button) findViewById(R.id.btnOpenXiaoBanL);
        btnKeyEvent = (Button) findViewById(R.id.btnKeyEvent);
        btnFocus = (Button) findViewById(R.id.btnFocus);
        btnTimeCount = (Button) findViewById(R.id.btnTimeCount);
        btnRecycle = (Button) findViewById(R.id.btnRecycle);
        btnSoundPool = (Button) findViewById(R.id.btnSoundPool);
        btnCate = (Button) findViewById(R.id.btnCate);

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

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        switch (v.getId()){

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
                ft.replace(R.id.container, CateFragment.newInstance());
                ft.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                ft.commit();
                break;

            case R.id.btnSoundPool: // SoundPool测试
                ft.replace(R.id.container, SoundPoolFragment.newInstance());
                ft.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                ft.commit();
                break;

            case R.id.btnFocus: //焦点测试
                Intent btnFocus = new Intent(this, FocusActivity.class);
                startActivity(btnFocus);
                break;

            case R.id.btnTimeCount: // 倒计时
                ft.replace(R.id.container, TimeCountFragment.newInstance(null));
                ft.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                ft.commit();
                break;

            case R.id.btnRecycle: // RecycleView相关
                Intent recyclev = new Intent(this, RecycleViewActivity.class);
                startActivity(recyclev);
                break;

        }
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

}
