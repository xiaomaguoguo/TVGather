package fragment;

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

    public static void onFocusChangeStill(View view, boolean b, RecyclerView.ViewHolder holder) {




        if(b){
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.08f,
                    Animation.RELATIVE_TO_PARENT, 0.5f,
                    Animation.RELATIVE_TO_PARENT, 0.5f);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setDuration(300);
            scaleAnimation.setInterpolator(new DecelerateInterpolator());
            view.startAnimation(scaleAnimation);
//            ((VideoDetailStillsAndTrailersViewHolder) holder).fram_cover.startAnimation(scaleAnimation);
        }else {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.08f, 1.0f,
                    Animation.RELATIVE_TO_PARENT, 0.5f,
                    Animation.RELATIVE_TO_PARENT, 0.5f);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setDuration(300);
            scaleAnimation.setInterpolator(new DecelerateInterpolator());
            view.startAnimation(scaleAnimation);
//            ((VideoDetailStillsAndTrailersViewHolder) holder).fram_cover.startAnimation(scaleAnimation);
        }

    }

    public static void showOrHidden(View view,boolean b){
        view.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
        AlphaAnimation show = new AlphaAnimation( b ? 0.0f : 1.0f , b ? 1.0f : 0.0f);
        show.setFillAfter(true);
        show.setDuration(300);
        show.setInterpolator(new DecelerateInterpolator());
        view.startAnimation(show);
    }

}
