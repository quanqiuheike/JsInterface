package com.cqx.jsinterface.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by chengqiuxia on 2018/4/2.
 */

public class BaseWebActivity extends BasePermissionActivity {
    private final String TAG = "BaseWebActivity";

    private Handler handler;

    private Map<String, CQXJSPlugin> pluginMap;

    private Map<String, Integer> pluginRequestCodeMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };
        JSInterfaceConfigParser parser = new JSInterfaceConfigParser();
        parser.parse(this);
        pluginMap = parser.getPluginMap();
        pluginRequestCodeMap = parser.getPluginRequestCodeMap();

    }

    /**
     * 注册js接口对象
     *
     * @param wv
     */
    public void addJavascriptInterface(WebView wv) {
        wv.addJavascriptInterface(new CQXJSInterface(wv), "CQXJSInterface");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Iterator<Map.Entry<String, Integer>> iterator = pluginRequestCodeMap.entrySet().iterator();
        String targetAction = "";
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue().intValue() == requestCode) {
                targetAction = entry.getKey();
                break;
            }

        }
        CQXJSPlugin plugin = pluginMap.get(targetAction);
        if (plugin != null) {
            plugin.onActivityResult(requestCode, resultCode, data);

        }
    }

    class CQXJSInterface {
        private WebView wv;

        public CQXJSInterface(WebView wv) {
            this.wv = wv;
        }

        @JavascriptInterface
        public void exec(final String config) {
            handler.post(new Runnable() {
                @Override
                public void run() {

                    try {
                        /**
                         * {
                         action: "动作名称",
                         callback: "回调函数名称",
                         parameter: {
                         //根据需要，配置不同的入参。
                         }
                         };
                         */
                        JSONObject jsonObject = new JSONObject(config);
                        String action = jsonObject.optString("action");
                        CQXJSPlugin plugin = pluginMap.get(action);
                        int requestCode = pluginRequestCodeMap.get(action);

                        plugin.exec(BaseWebActivity.this, wv, requestCode, config);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "H5层调用Native层配置的action名称不正确，找不到对应的插件处理！ ");
                    }

                }
            });

        }

    }
}
