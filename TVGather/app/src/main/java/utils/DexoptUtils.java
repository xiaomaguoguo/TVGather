package utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import activity.LoadingDexActivity;

/**
 * Created by MaZhihua on 2017/8/12.
 */
public class DexoptUtils {

    public static final String TAG = DexoptUtils.class.getSimpleName();

    public static final String KEY_DEX2_SHA1 = "dex2-SHA1-Digest";

    public static boolean quickStart(Context context) {
        if (getCurProcessName(context).endsWith(":mini")) {
            Log.d(TAG,":mini start!");
            return true;
        }
        return false ;
    }

    //neead wait for dexopt ?
    public static boolean needWait(Context context){
        String flag = get2thDexSHA1(context);
        Log.d(TAG,"dex2-sha1 "+flag);
        SharedPreferences sp = context.getSharedPreferences(
                getPackageInfo(context).versionName, Context.MODE_MULTI_PROCESS);
        String saveValue = sp.getString(KEY_DEX2_SHA1, "");
        return !TextUtils.equals(flag,saveValue);
    }

    public static PackageInfo getPackageInfo(Context context){
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  new PackageInfo();
    }

    /**
     * Get classes.dex file signature
     * @param context
     * @return
     */
    public static String get2thDexSHA1(Context context) {
        ApplicationInfo ai = context.getApplicationInfo();
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

    public static String getCurProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context
                    .getSystemService(Context. ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess. processName;
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return null ;
    }

    public static void waitForDexopt(Context base) {
        Intent intent = new Intent(base, LoadingDexActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        base.startActivity(intent);
        long startWait = System.currentTimeMillis ();
        long waitTime = 10 * 1000 ;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH ) {
            waitTime = 20 * 1000 ;//实测发现某些场景下有些版本有可能10s都不能完成optdex
        }
        while (needWait(base)) {
            try {
                long nowWait = System.currentTimeMillis() - startWait;
                if (nowWait >= waitTime) {
                    return;
                }
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
