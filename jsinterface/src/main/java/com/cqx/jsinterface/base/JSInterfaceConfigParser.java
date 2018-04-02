package com.cqx.jsinterface.base;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengqiuxia on 2018/4/2.
 * <p>
 * 解析JS插件配置文件，提供插件管理
 */

public class JSInterfaceConfigParser {
    private static String TAG = "JSInterfaceConfigParser";
    private boolean insideFeature = false;
    private String actionName = "";
    private String pluginClass = "";
    private static int index = 7000;
    private Map<String, CQXJSPlugin> pluginMap = new HashMap<>();
    private Map<String, Integer> pluginRequestCodeMap = new HashMap<>();

    /**
     * key是actionName,value是具体插件对象
     *
     * @return
     */
    public Map<String, CQXJSPlugin> getPluginMap() {
        return pluginMap;
    }

    /**
     * key是actionName,value是requestCode
     *
     * @return
     */
    public Map<String, Integer> getPluginRequestCodeMap() {
        return pluginRequestCodeMap;
    }


    public void parse(Context context) {
        try {
            // First checking the class namespace for config.xml
            int id = context.getResources().getIdentifier("cqxjsinterface_config", "xml", context.getClass().getPackage().getName());
            if (id == 0) {
                // If we couldn't find config.xml there, we'll look in the namespace from AndroidManifest.xml
                id = context.getResources().getIdentifier("cqxjsiinterface_config", "xml", context.getPackageName());
                if (id == 0) {
                    throw new RuntimeException("res/xml/cqxjsinterface.xml is missing！");
                }

            }
            parse(context.getResources().getXml(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parse(XmlPullParser xml) throws Exception {
        int eventType = -1;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                handleStartTag(xml);

            } else if (eventType == XmlPullParser.END_TAG) {
                handleEndTag(xml);

            }
            eventType = xml.next();
        }
    }

    private void handleStartTag(XmlPullParser xml) {
        String strNode = xml.getName();
        if (strNode.equals("feature")) {
            //Check for supported feature sets  aka. plugins (Accelerometer, Geolocation, etc)
            //Set the bit for reading params
            insideFeature = true;
            actionName = xml.getAttributeValue(null, "name");
        } else if (insideFeature && strNode.equals("param")) {
            pluginClass = xml.getAttributeValue(null, "value");
        }

    }

    private void handleEndTag(XmlPullParser xml) throws Exception {
        String strNode = xml.getName();
        if (strNode.equals("feature")) {
            Class onwClass = Class.forName(pluginClass);
            Object object = onwClass.newInstance();
            if (object != null && (object instanceof CQXJSPlugin)) {
                //key是actionName,value是具体的插件对象
                CQXJSPlugin plugin = (CQXJSPlugin) object;
                pluginMap.put(actionName, plugin);
                pluginRequestCodeMap.put(actionName, index++);
            }
            //清楚信息,准备解析下一个插件节点
            actionName = "";
            pluginClass = "";
            insideFeature = false;
        }
    }

}
