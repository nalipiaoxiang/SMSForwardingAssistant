package com.cpc.smsforwardingassistant.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cpc.smsforwardingassistant.config.AppConfig;
import com.cpc.smsforwardingassistant.dao.entity.EmailInfo;

import cn.hutool.core.util.ObjectUtil;

/**
 * 操作一些简单设置的读取和写入
 */
public class LocalConfigOperationUtils {

    /**
     * 写入配置(json字符串)
     * @param context
     * @param appConfig
     * @return
     */
    public static boolean writeConfig(Context context, String appConfig){
        SharedPreferences shp = context.getSharedPreferences(AppConfig.LOCAL_CONFIG_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putString(AppConfig.APP_CONFIG, appConfig);
        boolean isSaveSuccess = editor.commit();
        if (!isSaveSuccess) {
            return false;
        }
        return true;
    }

    /**
     * 读取配置(json字符串)
     * @param context
     * @return
     */
    public static EmailInfo readConfig(Context context){
        Log.v("本地配置工具", "读取配置...");
        SharedPreferences shp = context.getSharedPreferences(AppConfig.LOCAL_CONFIG_NAME, Context.MODE_PRIVATE);
        /**
         * 参数1: 键,参数2: 查询不到返回的默认
         */
        String config = shp.getString(AppConfig.APP_CONFIG, "{}");
        Log.v("本地配置工具", "读取到配置:"+config);
        EmailInfo emailInfo = JSON.parseObject(config, EmailInfo.class);
        Log.v("本地配置工具", "转化为emailInfo:"+emailInfo);
        if (ObjectUtil.isEmpty(emailInfo)){
            Log.v("本地配置工具", "emailInfo为空");
        }
        return emailInfo;
    }
}
