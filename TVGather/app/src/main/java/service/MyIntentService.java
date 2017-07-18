package service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by MaZhihua on 2017/7/18.
 */

public class MyIntentService extends IntentService {

    public static final String TAG = MyIntentService.class.getSimpleName();

    private int count = 0;

    private ResultReceiver mReceiver = null;

    private Bundle mBundle = null;

    public MyIntentService(){
        super("default");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBundle = new Bundle();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mReceiver = intent.getParcelableExtra("mReceiver");
        mBundle.clear();
        count++;
        try {
            Thread.sleep(200);
            Log.d(TAG,"count = " + count + " ; thread id = " + Thread.currentThread().getId());
            mBundle.putString("count",count+"");
            mReceiver.send(0,mBundle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
