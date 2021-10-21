package com.cpc.smsforwardingassistant.dao.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 短信
 */
public class ShortMessage implements Serializable {

    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 短信内容
     */
    private String content;

    public ShortMessage() {
    }

    public ShortMessage(String phoneNumber, String content) {
        this.phoneNumber = phoneNumber;
        this.content = content;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortMessage that = (ShortMessage) o;
        return phoneNumber.equals(that.phoneNumber) && content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, content);
    }

    @Override
    public String toString() {
        return "ShortMessage{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
