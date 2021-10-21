package com.cpc.smsforwardingassistant.dao.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * 数据库关联之前的表
 */
@Database(entities = {ShortMessage.class},version = 1,exportSchema = false)
public abstract class ShortMessageDatabase extends RoomDatabase {

    //暴露dao给用户
    public abstract ShortMessageDao getShortMessageDao();

    private static ShortMessageDatabase instance;

    public static synchronized ShortMessageDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ShortMessageDatabase.class, "sms_short_message").enableMultiInstanceInvalidation()
                    //强制开启主线程仅测试使用
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
