package com.cpc.smsforwardingassistant.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件实体(封装和邮件参数信息)
 */
public class EmailInfo implements Serializable {
    /**
     * 收件人
     */
    private String receiver;
    /**
     * 主题
     */
    private String subject;
    /**
     * 发送日期
     */
    private Date sentDate;
    /**
     * 邮件正文
     */
    private String content;
    /**
     * 邮箱(第一版只支持163的smtp)
     */
    private String user;
    /**
     * 邮箱密码
     */
    private String password;

    /**
     * 邮件协议(第一版暂时不用)
     */
    private String emailProtocol;

    /**
     * 是否转发
     */
    private boolean isForward;

    /**
     * 邮箱状态,通过检测为true,未通过检测为false
     */
    private boolean emailStatus;

    public EmailInfo() {
    }

    public EmailInfo(String receiver, String user, String password) {
        this.receiver = receiver;
        this.user = user;
        this.password = password;
    }
    public EmailInfo(String receiver, String subject, Date sentDate, String content, String user, String password, String emailProtocol, boolean isForward,boolean emailStatus) {
        this.receiver = receiver;
        this.subject = subject;
        this.sentDate = sentDate;
        this.content = content;
        this.user = user;
        this.password = password;
        this.emailProtocol = emailProtocol;
        this.isForward = isForward;
        this.emailStatus = emailStatus;
    }

    /**
     *  邮箱常用参数构造
     * @param receiver  收件人
     * @param subject   主题
     * @param content   邮件正文
     * @param user      邮箱(本版只支持163邮箱)
     * @param password  密码
     */
    public EmailInfo(String receiver, String subject, String content, String user, String password) {
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.user = user;
        this.password = password;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailProtocol() {
        return emailProtocol;
    }

    public void setEmailProtocol(String emailProtocol) {
        this.emailProtocol = emailProtocol;
    }

    public boolean isForward() {
        return isForward;
    }

    public void setForward(boolean forward) {
        isForward = forward;
    }

    public boolean isEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(boolean emailStatus) {
        this.emailStatus = emailStatus;
    }

    @Override
    public String toString() {
        return "EmailInfo{" +
                "receiver='" + receiver + '\'' +
                ", subject='" + subject + '\'' +
                ", sentDate=" + sentDate +
                ", content='" + content + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", emailProtocol='" + emailProtocol + '\'' +
                ", isForward=" + isForward +
                ", emailStatus=" + emailStatus +
                '}';
    }
}
