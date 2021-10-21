package com.cpc.smsforwardingassistant.util;

import android.content.Context;
import android.content.Intent;

import com.cpc.smsforwardingassistant.config.AppConfig;

import org.json.JSONObject;

import java.util.Map;

import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.json.JSONUtil;

/**
 * 广播发送工具
 */
public class BroadcastUtils {

    /**
     * 发送广播更新UI
     * @param context 上下文
     * @param map   组件和更新内容
     */
    public static void sendUpdateUIBroadcast(Context context, Map<String,String> map){
        Intent in = new Intent();
        in.setAction(AppConfig.UPDATE_UI_ACTION);
        in.putExtra(AppConfig.UPDATE_UI,JSONUtil.toJsonStr(map));
        context.sendBroadcast(in);
    }
}
