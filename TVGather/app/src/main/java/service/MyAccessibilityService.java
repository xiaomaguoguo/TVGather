package service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by MaZhihua on 2017/8/3.
 * 辅助服务测试实现类
 */
public class MyAccessibilityService extends AccessibilityService {

    public static final String TAG = MyAccessibilityService.class.getSimpleName();

    private boolean isClick = false ;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo accessibilityNodeInfo = event.getSource();
        Log.d(TAG,"onAccessibilityEvent");
        if(accessibilityNodeInfo != null){
            List<AccessibilityNodeInfo> accessibilityNodeInfos = accessibilityNodeInfo.findAccessibilityNodeInfosByText("播 放");
            if(accessibilityNodeInfos != null && !accessibilityNodeInfos.isEmpty()){
                Log.d(TAG,"accessibilityNodeInfos != null");
                AccessibilityNodeInfo needToClickNode = accessibilityNodeInfos.get(0);
                if(needToClickNode != null && !isClick){
                    accessibilityNodeInfos.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    isClick = true;
                    needToClickNode.recycle();
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
