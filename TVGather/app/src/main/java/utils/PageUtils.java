package utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by MaZhihua on 2017/12/5.
 * 翻页工具
 */
public class PageUtils {

    /**
     * 下一页实现
     */
    public static void nextPage(RecyclerView mRecycleView){
        LinearLayoutManager llm = (LinearLayoutManager) mRecycleView.getLayoutManager();
        int lastItemCompletelyPosition = llm.findLastCompletelyVisibleItemPosition();
        if(lastItemCompletelyPosition == mRecycleView.getChildCount()){
            Log.d("KKK","已经到最后一个item了，直接return");
            return ;
        }
        int lastItemPosition = llm.findLastVisibleItemPosition();
        Log.d("KKK","lastItemPosition = " + lastItemPosition +"; lastItemCompletelyPosition = " + lastItemCompletelyPosition);

        View completeView = llm.findViewByPosition( lastItemPosition != lastItemCompletelyPosition ? lastItemCompletelyPosition : lastItemPosition);
        int[] outLocation = new int[2];
        completeView.getLocationInWindow(outLocation);
        if(mRecycleView.getLayoutManager() instanceof GridLayoutManager) {
            mRecycleView.smoothScrollBy(0,outLocation[1] + completeView.getHeight());
        }else if (mRecycleView.getLayoutManager() instanceof LinearLayoutManager){
            mRecycleView.smoothScrollBy(outLocation[0] + completeView.getWidth(),0);
        }
    }

    /**
     * 上一页实现
     */
    public static void prePage(RecyclerView mRecycleView){
        LinearLayoutManager llm2 = (LinearLayoutManager) mRecycleView.getLayoutManager();
        int firstItemCompletePosition2 = llm2.findFirstCompletelyVisibleItemPosition();
        if(firstItemCompletePosition2 == 0 ){
            Log.d("KKK","已经到最前面了，直接return");
            return ;
        }
        int firstItemPosition2 = llm2.findFirstVisibleItemPosition();
        Log.d("KKK","firstItemPosition2 = " + firstItemPosition2 + "; firstItemCompletePosition2 = " + firstItemCompletePosition2);

        View completeView2 = llm2.findViewByPosition( firstItemPosition2 != firstItemCompletePosition2 ? firstItemCompletePosition2 : firstItemPosition2);
        int[] outLocation2 = new int[2];
        completeView2.getLocationInWindow(outLocation2);
        if(mRecycleView.getLayoutManager() instanceof GridLayoutManager){
            mRecycleView.smoothScrollBy(0, -(outLocation2[1] + completeView2.getHeight()*2));
        }else if(mRecycleView.getLayoutManager() instanceof LinearLayoutManager){
            mRecycleView.smoothScrollBy(outLocation2[0] - mRecycleView.getWidth(),0);
        }


    }

}
