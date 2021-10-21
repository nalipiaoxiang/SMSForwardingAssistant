package com.cpc.smsforwardingassistant.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.cpc.smsforwardingassistant.constant.Conf;
import com.cpc.smsforwardingassistant.dao.entity.EmailInfo;
import com.cpc.smsforwardingassistant.dao.room.ShortMessage;
import com.cpc.smsforwardingassistant.ui.fragment.home.HomePresenter;
import com.cpc.smsforwardingassistant.ui.fragment.setting.SettingModel;
import com.cpc.smsforwardingassistant.util.EmailUtils;
import com.cpc.smsforwardingassistant.util.LocalConfigOperationUtils;

import java.util.Date;


public class SmsReceiver extends BroadcastReceiver {


    private static final String TAG = "广播";

    public SmsReceiver() {

    }

    /**
     * 监听短信
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "监听服务启动....");
        ShortMessage shortMessage = getShortMessage(intent);
        //读取短信配置
        EmailInfo emailInfo = LocalConfigOperationUtils.readConfig(context);
        Log.d(TAG, "监听到短信:"+shortMessage.toString());
        Log.d(TAG, "读取邮箱配置:"+emailInfo.toString());
        boolean emailStatus = emailInfo.isEmailStatus();
        if (emailStatus){
            if (!emailInfo.isForward()){
                //如果不是转发状态就终止转发
                return;
            }
            Log.d(TAG, "邮箱状态正常开始调用异步邮件");
            //优化shortMessage
            consummate(shortMessage);
            //邮件需要美化
            emailInfo.setSubject(Conf.TITLE);
            emailInfo.setContent(EmailUtils.getEmail(shortMessage));
            //发送邮件
            SettingModel settingModel = new SettingModel(context);
            boolean isSendSuccess = settingModel.sendTestEmail(emailInfo, context);
            if (isSendSuccess){
                Log.d(TAG, "邮件发送成功,存入数据库");
                settingModel.insertPushInfo(shortMessage);
            }else {
                Log.d(TAG, "邮件发送不成功,存入数据库");
                shortMessage.setType(Conf.EMAIL_FAILED);
                settingModel.insertPushInfo(shortMessage);
            }
            //不管成不成功刷新页面

        }else {
            Log.d(TAG, "邮箱状态不正常取消调用异步邮件");
        }

    }

    /**
     * 完善 shortMessage
     * @param shortMessage
     */
    private void consummate(ShortMessage shortMessage) {
        shortMessage.setDate(new Date().getTime());
        shortMessage.setTitle(Conf.TITLE);
        shortMessage.setType(Conf.SHORT_MESSAGE);
    }

    /**
     * 获取短信
     */
    private ShortMessage getShortMessage(Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage message=SmsMessage.createFromPdu((byte[])pdus[0]);
        System.out.println("message:"+message);
        String phoneNumber = message.getOriginatingAddress();//获取短信手机号
        String content = message.getMessageBody();//获取短信内容
        ShortMessage shortMessage = new ShortMessage(phoneNumber,content);
        Log.v("SmsReceiver", "解析得到短信,内容:"+shortMessage.toString());
        return shortMessage;
    }

}
