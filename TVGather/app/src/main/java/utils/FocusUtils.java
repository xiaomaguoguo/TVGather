package utils;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

/**
 * Created by KNothing on 2017/4/19.
 * 获得焦点，失去焦点时工具类
 */

public class FocusUtils {

    public static void kkkk(View view,boolean b){
        ObjectAnimator zoomOut = ObjectAnimator.ofFloat(view,View.SCALE_X,1.3f,1.2f,1.1f,1.0f);
        zoomOut.setInterpolator(new DecelerateInterpolator());//设置动画插入器，减速
        zoomOut.setDuration(30);//设置动画时间

        ObjectAnimator zoomIn = ObjectAnimator.ofFloat(view,View.SCALE_X,1.0f,1.1f,1.2f,1.3f);
        zoomIn.setDuration(300);//设置动画时间
        zoomIn.setInterpolator(new DecelerateInterpolator());//设置动画插入器，减速

        if(b){
            zoomIn.start();
        }else{
            zoomOut.start();
        }



    }

    public static void onFocusChangeStill(View view, boolean b) {
        if(b){
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setDuration(300);
            scaleAnimation.setInterpolator(new DecelerateInterpolator());
            view.startAnimation(scaleAnimation);
        }else {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setDuration(100);
            scaleAnimation.setInterpolator(new DecelerateInterpolator());
            view.startAnimation(scaleAnimation);
        }
    }

    public static void showOrHidden(View view,boolean b){
        view.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
        AlphaAnimation show = new AlphaAnimation( b ? 0.0f : 1.0f , b ? 1.0f : 0.0f);
        show.setFillAfter(true);
        show.setDuration(b ? 300 : 100);
        show.setInterpolator(new DecelerateInterpolator());
        view.startAnimation(show);
    }

}
