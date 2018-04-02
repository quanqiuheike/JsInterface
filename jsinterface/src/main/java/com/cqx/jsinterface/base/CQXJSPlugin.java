package com.cqx.jsinterface.base;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;

/**
 * Created by chengqiuxia on 2018/4/2.
 *
 * 自定义js插件需要实现该接口
 */

public interface CQXJSPlugin {

    void exec(Activity activityContext, WebView wv, int requestCode, String config);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
