package activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.WindowManager;

import com.bftv.knothing.firsttv.R;

import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * Created by MaZhihua on 2017/9/4.
 */

public class LoadingDexActivity extends Activity {

    public static final String TAG = LoadingDexActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN );
        overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
        setContentView(R.layout.wait_optdex_layout);
        new LoadDexTask().execute();
    }

    class LoadDexTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                MultiDex.install(LoadingDexActivity.this);
                Log.d(TAG,"加载非主Dex工作完成");
                SharedPreferences sp = getSharedPreferences(getPackageInfo().versionName, Context.MODE_MULTI_PROCESS);
                sp.edit().putString(MyApplication.KEY_DEX2_SHA1,get2thDexSHA1()).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            Log.d(TAG,"get install finish");
            finish();
        }
    }

    /**
     * Get classes.dex file signature
     * @return
     */
    private String get2thDexSHA1() {
        ApplicationInfo ai = getApplicationInfo();
        String source = ai.sourceDir;
        try {
            JarFile jar = new JarFile(source);
            java.util.jar.Manifest mf = jar.getManifest();
            Map<String, Attributes> map = mf.getEntries();
            Attributes a = map.get("classes2.dex");
            return a.getValue("SHA1-Digest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    public PackageInfo getPackageInfo(){
        PackageManager pm = getPackageManager();
        try {
            return pm.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  new PackageInfo();
    }

}
