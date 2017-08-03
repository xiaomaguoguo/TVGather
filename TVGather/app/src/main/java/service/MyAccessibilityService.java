package service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import java.util.List;

/**
 * Created by MaZhihua on 2017/8/3.
 * 辅助服务测试实现类
 */
public class MyAccessibilityService extends AccessibilityService {

    public static final String TAG = MyAccessibilityService.class.getSimpleName();

//    private boolean isClick = false ;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
//        AccessibilityNodeInfo accessibilityNodeInfo = event.getSource();
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        int eventType = event.getEventType();
        Log.d(TAG,"onAccessibilityEvent");
        if(accessibilityNodeInfo != null){
//            List<AccessibilityNodeInfo> accessibilityNodeInfos = accessibilityNodeInfo.findAccessibilityNodeInfosByText("播 放");
//            List<AccessibilityNodeInfo> accessibilityNodeInfos = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("R.id.ll_full_screen");
            List<AccessibilityNodeInfo> accessibilityNodeInfos = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.pplive.androidxl:id/ll_full_screen");
//            List<AccessibilityNodeInfo> accessibilityNodeInfos = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.pplive.androidxl:id/ll_collection");
            if(accessibilityNodeInfos != null && !accessibilityNodeInfos.isEmpty()){
                Log.d(TAG,"accessibilityNodeInfos != null");
                AccessibilityNodeInfo needToClickNode = accessibilityNodeInfos.get(0);
                if(needToClickNode != null/* && !isClick*/){
                    accessibilityNodeInfos.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                    isClick = true;
                    needToClickNode.recycle();
                }
            }
        }

        switch (eventType){

            case AccessibilityEvent.TYPE_VIEW_CLICKED: // 点击事件
                Log.d(TAG,"click view text = " + event.getText().toString());
                break;

            case AccessibilityEvent.TYPE_VIEW_SELECTED:
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
                Log.d(TAG,"click view text = " + "醉了醉了");
                break;

        }


    }

    @Override
    public void onInterrupt() {

    }
}
