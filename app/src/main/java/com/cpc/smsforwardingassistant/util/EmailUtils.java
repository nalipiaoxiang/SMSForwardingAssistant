package com.cpc.smsforwardingassistant.util;

import android.util.Log;

import com.cpc.smsforwardingassistant.dao.entity.EmailInfo;
import com.cpc.smsforwardingassistant.dao.room.ShortMessage;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

public class EmailUtils {


    /**
     * 发送邮件(发送方只支持163的smtp,因为只有163的比较好用)
     * @param emailInfo 邮箱设置参数
     */
    @Deprecated
    public static boolean sendMail(EmailInfo emailInfo) {
        Log.v("EmailUtils", "调用邮件发送服务,参数:"+emailInfo.toString());
        String receiver = emailInfo.getReceiver();
        String subject = emailInfo.getSubject();
        Date sentDate = emailInfo.getSentDate();
        String content = emailInfo.getContent();
        String user = emailInfo.getUser();
        String password = emailInfo.getPassword();
        Log.v("EmailUtils", "开始调用邮件服务发送邮件");
        if (StrUtil.isEmpty(subject)){
            subject = "This is a default mail subject";
        }
        if (ObjectUtil.isEmpty(sentDate)){
            sentDate = new Date();
        }
        if (StrUtil.isEmpty(content)){
            content = "Hello World";
        }
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");
        Session session = Session.getInstance(props, null);
        MimeMessage msg = new MimeMessage(session);
        try {
            //发件人
            msg.setFrom(user);
            //收件人
            msg.setRecipients(Message.RecipientType.TO,receiver);
            //主题
            msg.setSubject(subject);
            //日期
            msg.setSentDate(sentDate);
            //内容
            msg.setText(content);
            Transport.send(msg, user, password);
            Log.v("EmailUtils", "调用邮件发送成功");
            return true;
        } catch (MessagingException me) {
            me.printStackTrace();
            Log.v("EmailUtils", "调用邮件发送失败,失败异常:"+me.getMessage());
            return false;
        }
    }



    /**
     * 短信模板
     * @param shortMessage
     * @return
     */
    public static String getEmail(ShortMessage shortMessage){
        String temp = "短信转发助手\n" +
                "   通讯号码:"+shortMessage.getPhoneNumber()+"\n" +
                "   接收时间:"+DataUtils.longDateToFormatStr(shortMessage.getDate())+"\n" +
                "   内容:\n" +
                "\t"+shortMessage.getContent() +
                "注意：这是来自邮件助手的转发 \n" +
                "（请注意保护自己信息和隐私的安全，请勿泄漏！) \n" +
                "此为系统邮件，请勿回复\n" +
                "请保管好您的邮箱，避免账号被他人盗用\n" +
                "\n" +
                "邮件转发助手官网"+"https://github.com/nalipiaoxiang/SMSForwardingAssistant";
        return temp;
    }
}
