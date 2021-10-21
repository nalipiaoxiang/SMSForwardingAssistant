package com.cpc.smsforwardingassistant.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataUtils {

    /**
     * 时间戳转格式化字符串
     * 格式化类型 "yyyy-MM-dd HH:mm:ss"
     * @param date 时间戳
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String longDateToFormatStr(Long date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String format = sdf.format(new Date(date));
        return format;
    }
}
