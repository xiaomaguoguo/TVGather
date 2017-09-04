package activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import utils.DexoptUtils;

/**
 * Created by MaZhihua on 2017/9/4.
 */

public class MyApplication extends MultiDexApplication {

    public static final String KEY_DEX2_SHA1 = "dex2-SHA1-Digest";

    public static final String TAG = MyApplication.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (!DexoptUtils.quickStart(base) && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//>=5.0的系统默认对dex进行oat优化
            if (DexoptUtils.needWait(base)){
                DexoptUtils.waitForDexopt(base);
            }
            MultiDex.install (base );
        } else {
            return;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (DexoptUtils.quickStart(this)) {
            return;
        }
    }

}
