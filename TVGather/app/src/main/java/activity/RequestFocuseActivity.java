package activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bftv.knothing.firsttv.R;

/**
 * Created by KNothing on 2018/1/18.
 */

public class RequestFocuseActivity extends Activity {

    private Button button6 = null;

    private FrameLayout mFrameLayout = null;

    private RelativeLayout mRelativeLayout = null;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_focused);
        initView();
    }

    private void initView() {
        button6 = findViewById(R.id.button6);
        mFrameLayout = findViewById(R.id.frame);
        mRelativeLayout = findViewById(R.id.relative);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button6.requestFocus();
            }
        },1000);

    }
}
