package com.cqx.github;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cqx.jsinterface.base.BaseWebActivity;
import com.cqx.jsinterface.base.BaseWebView;

/**
 * Created by chengqiuxia on 2018/4/2.
 */

public class WebViewActivity extends BaseWebActivity {
    private BaseWebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        mWebView = findViewById(R.id.webview);
//        mWebView.loadUrl("http://www.baidu.com");
        //添加JS接口对象
        addJavascriptInterface(mWebView);
        mWebView.loadUrl("file:///android_asset/www/" + "index.html");

    }
}
