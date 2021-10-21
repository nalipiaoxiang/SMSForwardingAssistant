package com.cpc.smsforwardingassistant.ui.fragment.home;

import android.content.Context;
import android.util.Log;

import com.cpc.smsforwardingassistant.dao.db.DBEngine;
import com.cpc.smsforwardingassistant.dao.room.ShortMessage;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

public class HomeModel implements HomeContract.IHomeModel {


    private static final String TAG = "M层";

    @Override
    public List<ShortMessage> getData(Context context) {
        //获取DBEngine
        DBEngine dbEngine = DBEngine.getInstance(context);
        List<ShortMessage> shortMessages = dbEngine.queryAllShortMessages();
        if (CollectionUtil.isEmpty(shortMessages)) {
            shortMessages = new ArrayList<>();
        }
        return shortMessages;
    }




}
