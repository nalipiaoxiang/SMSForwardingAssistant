package com.cpc.smsforwardingassistant.dao.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * 短信实体
 */
@Entity
public class ShortMessage implements Serializable {

    /**
     * 主键
     * @PrimaryKey 主键
     * autoGenerate = true 自动增长
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    //电话号码
    private String phoneNumber;

    //标题
    private String title;

    //内容
    private String content;

    //时间毫秒
    private Long date;

    //类型 1.短信 2.错误信息 3.其他
    private int type;

    //通知 在type为2的时候显示
    private String notice;

    @Ignore
    public ShortMessage() {
    }
    @Ignore
    public ShortMessage(int id) {
        this.id = id;
    }
    @Ignore
    public ShortMessage(String phoneNumber, String content) {
        this.phoneNumber = phoneNumber;
        this.content = content;
    }
    @Ignore
    public ShortMessage(Long date, int type, String notice) {
        this.date = date;
        this.type = type;
        this.notice = notice;
    }
    @Ignore
    public ShortMessage(String phoneNumber, String title, String content, Long date, int type, String notice) {
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.content = content;
        this.date = date;
        this.type = type;
        this.notice = notice;
    }
    public ShortMessage(int id, String phoneNumber, String title, String content, Long date, int type, String notice) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.content = content;
        this.date = date;
        this.type = type;
        this.notice = notice;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public String toString() {
        return "ShortMessage{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", notice='" + notice + '\'' +
                '}';
    }
}
