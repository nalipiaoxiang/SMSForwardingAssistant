package com.cpc.smsforwardingassistant.dao.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * 用处操作数据库的增删改查
 */
@Dao
public interface ShortMessageDao {

    @Insert
    void insertShortMessage(ShortMessage ... shortMessages);

    @Update
    void updateShortMessage(ShortMessage ... shortMessages);

    @Delete
    void deleteShortMessage(ShortMessage ... shortMessages);

    @Query("DELETE FROM ShortMessage")
    void deleteAllShortMessage();

    @Query("SELECT * FROM ShortMessage ORDER BY ID DESC")
    List<ShortMessage> findAllShortMessage();
}
