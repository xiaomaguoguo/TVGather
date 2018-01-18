package widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by KNothing on 2018/1/18.
 */

public class MyFrameLayout extends FrameLayout {

    public MyFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        Log.d("KKK MyFrameLayout","child = "+ child.getClass() + ";focused = " + focused.getClass()+";mFocused = " + getFocusedChild().getClass());
    }
}
