package com.cpc.smsforwardingassistant.util;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import cn.hutool.core.util.ObjectUtil;

public class TaostUtils {
    /**
     * 异步吐司
     * @param msg
     */
    public static void showToastSync(Context context ,String msg) {
        if (ObjectUtil.isEmpty(Looper.myLooper()))
        {
            Looper.prepare();
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        if (ObjectUtil.isNotEmpty(Looper.myLooper())) {
            Looper.loop();
        }
    }
}
