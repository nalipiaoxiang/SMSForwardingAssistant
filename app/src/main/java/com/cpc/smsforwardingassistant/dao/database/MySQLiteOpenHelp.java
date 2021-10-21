package com.cpc.smsforwardingassistant.dao.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import cn.hutool.core.util.ObjectUtil;

/**
 * 操作数据库的工具类    单例模式(1.构造函数私有化.2.对外提供方法)
 */
public class MySQLiteOpenHelp extends SQLiteOpenHelper {

    private static MySQLiteOpenHelp instance;

    public static synchronized MySQLiteOpenHelp getInstance(Context context){
        if (ObjectUtil.isNull(instance)){
            //以后想要数据库升级修改版本号2
            instance = new MySQLiteOpenHelp(context, "sms.db", null, 1);
        }
            return instance;
    }


    private MySQLiteOpenHelp(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //数据库初始化用
    //创建表 表数据初始化 数据库第一次创建时调用,第二次发现有了就不会重复创建
    @Override
    public void onCreate(SQLiteDatabase db) {
        //主键必须用_id integer类型 必须唯一
        //autoincrement 自动增重
        String sql = "create table short_messages(_id integer primary key autoincrement,phone_number text, title text,content text,date text)";
        db.execSQL(sql);
        System.out.println("初始化完成");
    }

    //数据库升级使用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
