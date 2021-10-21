package com.cpc.smsforwardingassistant.ui.fragment.setting;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cpc.smsforwardingassistant.dao.db.DBEngine;
import com.cpc.smsforwardingassistant.dao.entity.EmailInfo;
import com.cpc.smsforwardingassistant.dao.room.ShortMessage;
import com.cpc.smsforwardingassistant.dao.room.ShortMessageDao;
import com.cpc.smsforwardingassistant.dao.room.ShortMessageDatabase;
import com.cpc.smsforwardingassistant.util.LocalConfigOperationUtils;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

public class SettingModel implements ISettingContract.ISettingModel {


    private static final String TAG = "M层";
    private ShortMessageDao shortMessageDao;
    private ISettingContract.ISettingPresenter settingPresenter;

    public SettingModel(ISettingContract.ISettingPresenter settingPresenter,Context context) {
        this.settingPresenter = settingPresenter;
        ShortMessageDatabase database = ShortMessageDatabase.getInstance(context);
        shortMessageDao = database.getShortMessageDao();
    }
    public SettingModel(Context context) {
        ShortMessageDatabase database = ShortMessageDatabase.getInstance(context);
        shortMessageDao = database.getShortMessageDao();
    }

    @Override
    public EmailInfo readLocalConfig(Context context) {
        Log.d(TAG, "开始从本地配置文件中加载配置");
        EmailInfo emailInfo = LocalConfigOperationUtils.readConfig(context);
        Log.d("TAG", "加载到配置信息:" + emailInfo);
        return emailInfo;
    }

    /**
     * 保存配置信息到本地
     *
     * @param emailInfo
     * @return
     */
    @Override
    public boolean writeConfigToLocal(Context context, EmailInfo emailInfo) {
        Log.v("SettingFragment", "开始保存配置....,配置:" + emailInfo.toString());
        String jsonString = JSON.toJSONString(emailInfo);
        Log.v("SettingFragment", "对象转json字符串,结果:" + jsonString);
        return LocalConfigOperationUtils.writeConfig(context, jsonString);
    }

    /**
     * 发送测试邮箱
     *
     * @param emailInfo
     */
    @Override
    public boolean sendTestEmail(EmailInfo emailInfo, Context context) {
        SendEmailAsyncTask task = new SendEmailAsyncTask(emailInfo, settingPresenter);
        task.execute();
        try {
            return task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 保存推送信息
     *
     * @param shortMessage 短信模板
     */
    @Override
    public void insertPushInfo(ShortMessage shortMessage) {
        new InsertAsyncTask(shortMessageDao).execute(shortMessage);
    }

    static class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private ISettingContract.ISettingPresenter settingPresenter;
        private EmailInfo emailInfo;

        public SendEmailAsyncTask(EmailInfo emailInfo, ISettingContract.ISettingPresenter settingPresenter) {
            this.emailInfo = emailInfo;
            this.settingPresenter = settingPresenter;
        }

        /**
         * @return
         */
        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.d(TAG, "调用异步邮件发送服务,参数:" + emailInfo.toString());
            String receiver = emailInfo.getReceiver();
            String subject = emailInfo.getSubject();
            Date sentDate = emailInfo.getSentDate();
            String content = emailInfo.getContent();
            String user = emailInfo.getUser();
            String password = emailInfo.getPassword();
            Log.d(TAG, "开始调用异步邮件服务发送邮件......");
            if (StrUtil.isEmpty(subject)) {
                subject = "This is a default mail subject";
            }
            if (ObjectUtil.isEmpty(sentDate)) {
                sentDate = new Date();
            }
            if (StrUtil.isEmpty(content)) {
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
                msg.setRecipients(Message.RecipientType.TO, receiver);
                //主题
                msg.setSubject(subject);
                //日期
                msg.setSentDate(sentDate);
                //内容
                msg.setText(content);
                Transport.send(msg, user, password);
                Log.d(TAG, "调用邮件异步发送成功");
                return true;
            } catch (MessagingException me) {
                me.printStackTrace();
                Log.d(TAG, "调用邮件异步发送失败,失败异常:" + me.getMessage());
                return false;
            }
        }
    }

    /**
     * 异步操作新增
     */
    static class InsertAsyncTask extends AsyncTask<ShortMessage, Void, Void> {

        private ShortMessageDao dao;

        public InsertAsyncTask(ShortMessageDao shortMessageDao) {
            dao = shortMessageDao;
        }

        @Override
        protected Void doInBackground(ShortMessage... shortMessages) {
            dao.insertShortMessage(shortMessages);
            return null;
        }

    }
}
