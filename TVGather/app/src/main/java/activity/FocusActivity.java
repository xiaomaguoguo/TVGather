package activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.bftv.knothing.firsttv.R;

import fragment.FocusUtils;

/**
 * Created by KNothing on 2017/4/21.
 */

public class FocusActivity extends Activity {

    boolean show = true;

    View focusBorder ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus_layout);


        focusBorder = findViewById(R.id.focusBorder);
        Button btnFocus = (Button) findViewById(R.id.btnFocus);
        btnFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FocusUtils.showOrHidden(focusBorder,show);
                show = !show;
            }
        });
    }
}
