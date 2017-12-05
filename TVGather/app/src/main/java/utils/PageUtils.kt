package utils

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

/**
 * Created by MaZhihua on 2017/12/5.
 * 翻页工具
 */
object PageUtils {

    val DEBUG = false

    val TAG = PageUtils::class.java.simpleName

    /**
     * 下一页实现
     */
    fun nextPage(mRecycleView: RecyclerView) {
        val llm = mRecycleView.layoutManager as LinearLayoutManager
        val lastItemCompletelyPosition = llm.findLastCompletelyVisibleItemPosition()
        val lastItemPosition = llm.findLastVisibleItemPosition()
        if (lastItemCompletelyPosition == mRecycleView.childCount) {
            if (DEBUG) {
                Log.d(TAG, "已经到最后一个item了，直接return")
            }
            return
        }
        if (DEBUG) {
            Log.d(TAG, "lastItemPosition = $lastItemPosition; lastItemCompletelyPosition = $lastItemCompletelyPosition")
        }
        val completeView = llm.findViewByPosition(if (lastItemPosition != lastItemCompletelyPosition) lastItemCompletelyPosition else lastItemPosition)
        val outLocation = IntArray(2)
        completeView.getLocationInWindow(outLocation)
        if (mRecycleView.layoutManager is GridLayoutManager) {
            mRecycleView.smoothScrollBy(0, outLocation[1] + completeView.height)
        } else if (mRecycleView.layoutManager is LinearLayoutManager) {
            mRecycleView.smoothScrollBy(outLocation[0] + completeView.width, 0)
        }
    }

    /**
     * 上一页实现
     */
    fun prePage(mRecycleView: RecyclerView) {
        val llm = mRecycleView.layoutManager as LinearLayoutManager
        val firstItemCompletePosition = llm.findFirstCompletelyVisibleItemPosition()
        if (firstItemCompletePosition == 0) {
            if (DEBUG) {
                Log.d(TAG, "已经到最前面了，直接return")
            }
            return
        }
        val firstItemPosition = llm.findFirstVisibleItemPosition()
        if (DEBUG) {
            Log.d(TAG, "firstItemPosition2 = $firstItemPosition; firstItemCompletePosition2 = $firstItemCompletePosition")
        }
        val completeView = llm.findViewByPosition(if (firstItemPosition != firstItemCompletePosition) firstItemCompletePosition else firstItemPosition)
        val outLocation = IntArray(2)
        completeView.getLocationInWindow(outLocation)
        if (mRecycleView.layoutManager is GridLayoutManager) {
            mRecycleView.smoothScrollBy(0, -(outLocation[1] + completeView.height * 2))
        } else if (mRecycleView.layoutManager is LinearLayoutManager) {
            mRecycleView.smoothScrollBy(outLocation[0] - mRecycleView.width, 0)
        }
    }

}
