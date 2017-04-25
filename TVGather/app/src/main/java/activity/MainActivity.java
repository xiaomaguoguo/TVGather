package activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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

    private Button btnFocus,btnTimeCount,btnRecycle,btnSoundPool,btnCate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        btnFocus = (Button) findViewById(R.id.btnFocus);
        btnTimeCount = (Button) findViewById(R.id.btnTimeCount);
        btnRecycle = (Button) findViewById(R.id.btnRecycle);
        btnSoundPool = (Button) findViewById(R.id.btnSoundPool);
        btnCate = (Button) findViewById(R.id.btnCate);

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
}
