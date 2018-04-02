package com.cqx.jsinterface.base;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by chengqiuxia on 2018/4/2.
 * <p>
 * 统一编写WebView的基本设置，建议统一直接采用BaseWebView
 */

public class BaseWebView extends WebView {
    private Context context;

    /**
     * Constructs a new WebView with a Context object.
     *
     * @param context a Context object used to access application assets
     */
    public BaseWebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    /**
     * Constructs a new WebView with layout parameters.
     *
     * @param context a Context object used to access application assets
     * @param attrs   an AttributeSet passed to our parent
     */
    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();

    }

    private void init() {
        setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        WebSettings setting = getSettings();

        setting.setJavaScriptEnabled(true);//支持js脚本
        setting.setDefaultTextEncodingName("UFT-8");//编码
        setting.setSavePassword(false);//不记住密码
        setting.setBuiltInZoomControls(true);
        setting.setUseWideViewPort(true);
        setting.setDomStorageEnabled(true);
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);//不需要添加缓存
        setting.setLoadWithOverviewMode(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);//支持js调用window.open方法
        setting.setAllowFileAccess(true);//设置允许访问文件数据
        setting.setDatabaseEnabled(true);//开启database storage API功能
        setting.setAppCacheEnabled(true);

        //播放不需要用户手势触发
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            setting.setMediaPlaybackRequiresUserGesture(false);
            setting.setAllowFileAccessFromFileURLs(true);
            setting.setAllowUniversalAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            setting.setDisplayZoomControls(false);
        }

        //HTML5本地存储支持----------start-----------
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            setting.setDomStorageEnabled(true);

            setting.setDomStorageEnabled(true);
            // Set cache size to 8 mb by default. should be more than enough
            setting.setAppCacheMaxSize(1024 * 1024 * 8);
            // This next one is crazy. It's the DEFAULT location for your app's cache
            // But it didn't work for me without this line.
            // UPDATE: no hardcoded path. Thanks to Kevin Hawkins

            String appCachePath = context.getCacheDir().getAbsolutePath();
            setting.setAppCachePath(appCachePath);
            setting.setAllowFileAccess(true);
            setting.setAppCacheEnabled(true);
            setting.setAllowContentAccess(true);
        }

        //-----------------------end------------------

        //支持https混合模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }
    }


}
