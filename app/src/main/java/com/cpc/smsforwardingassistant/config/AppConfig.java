package com.cpc.smsforwardingassistant.config;

import android.content.Intent;

public class AppConfig {
    /**
     * 短信接受者action标记
     */
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    /**
     * 更新UI接受者action标记
     */
    public static final String UPDATE_UI_ACTION = "com.cpc.smsforwardingassistant.UPDATE_UI";

    public static final String UPDATE_UI = "updateUI";

    /**
     * 接收短信权限
     */
    public static final Integer HAVE_RECEIVE_SMS = 0;
    /**
     * 保存本地配置的文件名
     */
    public static final String LOCAL_CONFIG_NAME = "smsfaconfig";
    /**
     * 配置名
     */
    public static final String APP_CONFIG = "appConfig";

    /**
     * 邮件主题
     */
    public static final String EMAIL_SUBJECT = "邮件助手转发";

    /**
     * 邮件内容
     */
    public static final String EMAIL_CONTENT = "这是邮件助手设置时效验设置是否正确所发送,无需回复";
}
