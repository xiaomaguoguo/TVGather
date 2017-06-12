package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bftv.knothing.firsttv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaZhihua on 2017/6/6.
 * JavaJs互调页面测试
 */
public class JavaJsActivity extends Activity {

    private WebView mWebView;
    private List<String> list;
    private int mkeyCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_javajs);
        mWebView = (WebView) findViewById(R.id.webView);
        initData();

        WebSettings webSettings = mWebView.getSettings();

        // 是否允许在webview中执行javascript
        webSettings.setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(this, "javatojs");
        //加载网页
//        mWebView.loadUrl("file:///android_asset/index.html");

        mWebView.addJavascriptInterface(new JsObject(), "injectedObject");

        mWebView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWebView.loadData("", "text/html", null);
//                mWebView.loadUrl("javascript:alert(injectedObject.toString())");
                mWebView.loadUrl("file:///android_asset/index.html");
            }
        },3000);


    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        mkeyCode = keyCode;
        Log.i("AA","keyCode="+keyCode);
        mWebView.loadUrl("javascript: OnKeyUp()");
        return super.onKeyUp(keyCode, event);
    }

    @JavascriptInterface
    public int getKeyCode(){
        return mkeyCode;
    }

    void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add("我是List中的第" + (i + 1) + "行");
        }
    }

    /**
     * 该方法将在js脚本中，通过window.javatojs.....()进行调用
     *
     * @return
     */
    @JavascriptInterface
    public Object getObject(int index) {
        Log.i("A","getObject");
        return list.get(index);
    }

    @JavascriptInterface
    public int getSize() {
        Log.i("A","getSize");
        return list.size();
    }

    @JavascriptInterface
    public void Callfunction() {
        Log.i("A","Callfunction");
        mWebView.loadUrl("javascript: GetList()");
    }

    @JavascriptInterface
    public void printStr(String str){
        Log.i("A","GetList:" + str);
    }

    final class MyWebChromeClient extends WebChromeClient{
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }


    class JsObject {

        @JavascriptInterface
        public String toString() {
            Log.i("KKK","调用到了toString方法");
            return "KNothing Day's";
        }

    }




}
