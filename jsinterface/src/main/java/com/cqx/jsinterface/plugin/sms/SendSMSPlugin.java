package com.cqx.jsinterface.plugin.sms;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.Toast;

import com.cqx.jsinterface.base.BasePermissionActivity;
import com.cqx.jsinterface.base.CQXJSPlugin;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chengqiuxia on 2018/4/2.
 */

public class SendSMSPlugin implements CQXJSPlugin {

    private Activity activityContext;

    private WebView wv;

    private String action;

    private String phoneNum;

    private String smsContent;

    private int requestCode;

    @Override
    public void exec(Activity activityContext, WebView wv, int requestCode, String config) {

        try {
            /**
             * 配置信息
             {
             //调用发送短信接口，action必须配置为sendSMS
             action: "sendSMS",
             //phoneNum必填，smsContent非必填。
             parameter: {
             phoneNum:"目标电话号码，如果要群发，多个电话号码用逗号隔开",
             smsContent:"短信内容"
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
            smsContent = parameterJO.optString("smsContent");

            //判断权限
            if (activityContext instanceof BasePermissionActivity) {
                if (((BasePermissionActivity) activityContext).permissionCheck(new String[]{Manifest.permission.SEND_SMS})) {
                    handleSendSMS();
                }

            }
        } catch (SecurityException e1) {
            e1.printStackTrace();
            Toast.makeText(activityContext, "为保证您正常、安全的使用客户端，需要获取您的发送短信权限，请您到 手机－设置－应用管理－权限管理 中开启。", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activityContext, "Native接口层异常：" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }

    public void handleSendSMS() {
        String target = "";
        String[] phones = phoneNum.split(",");
        for (int i = 0; i < phones.length; i++) {
            target = target + phones[i] + ";";
        }
        if (!TextUtils.isEmpty(target)) {
            target = target.substring(0, target.length() - 1);
        }
        Uri uri = Uri.parse("smsto:" + target);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", smsContent);

        activityContext.startActivity(intent);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
