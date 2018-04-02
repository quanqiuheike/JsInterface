package com.cqx.jsinterface.plugin.tel;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.widget.Toast;


import com.cqx.jsinterface.base.BasePermissionActivity;
import com.cqx.jsinterface.base.CQXJSPlugin;

import org.json.JSONObject;

/**
 * Created by chengqiuxia on 2018/4/2.
 * <p>
 * 插件：打电话
 */

public class TelPlugin implements CQXJSPlugin {

    private Activity activityContext;

    private WebView wv;

    private String action;

    private String phoneNum;

    private int requestCode;

    @Override
    public void exec(Activity activityContext, WebView wv, int requestCode, String config) {

        try {
            /**
             * 配置信息
             {
             //调用拨打电话接口，action必须配置为tel
             action: "tel",
             //parameter必填
             parameter: {
             phoneNum:"目标电话号码，只能配置一个号码"
             }
             }
             */

            this.activityContext = activityContext;
            this.wv = wv;
            this.requestCode = requestCode;

            JSONObject jsonObject = new JSONObject(config);
            action = jsonObject.optString("action");

            JSONObject parameterJO = jsonObject.optJSONObject("parameter");

            phoneNum = parameterJO.optString("phoneNum");

            //权限判断
            if (activityContext instanceof BasePermissionActivity) {
                if (((BasePermissionActivity) activityContext).permissionCheck(new String[]{Manifest.permission.CALL_PHONE})) {

                    handleTel();
                }

            }
        } catch (SecurityException e1) {
            e1.printStackTrace();
            Toast.makeText(activityContext, "为保证您正常、安全的使用客户端，需要获取您的拨打电话权限，请您到 手机－设置－应用管理－权限管理 中开启。", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activityContext, "Native接口层异常：" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }


    public void handleTel() {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNum));
        activityContext.startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
