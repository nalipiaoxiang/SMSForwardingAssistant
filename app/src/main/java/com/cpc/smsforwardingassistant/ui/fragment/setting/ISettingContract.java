package com.cpc.smsforwardingassistant.ui.fragment.setting;

import android.content.Context;

import com.cpc.smsforwardingassistant.dao.entity.EmailInfo;
import com.cpc.smsforwardingassistant.dao.room.ShortMessage;

import java.util.List;

public interface ISettingContract {
    /**
     * P层
     */
    interface ISettingPresenter{
        /**
         * 加载本地配置
         */
        void loadLocalConfig();

        /**
         * 保存配置信息到本地
         * @param emailInfo
         */
        void saveUIConfigToLocal(EmailInfo emailInfo);


    }

    /**
     * M层
     */
    interface ISettingModel{
        /**
         * 读取本地配置信息
         * @param context
         * @return
         */
        EmailInfo readLocalConfig(Context context);

        /**
         * 保存配置信息到本地
         * @param emailInfo
         * @return
         */
        boolean writeConfigToLocal(Context context,EmailInfo emailInfo);

        /**
         * 发送测试邮箱
         * @param emailInfo
         */
        boolean sendTestEmail(EmailInfo emailInfo,Context context);


        /**
         * 保存推送信息
         * @param shortMessage 短信模板
         */
        void insertPushInfo(ShortMessage shortMessage);
    }

    /**
     * V层
     */
    interface ISettingView{
        /**
         * 读取到的配置信息加载到UI上
         * @param emailInfo
         */
        void loadLocalConfigUpdateUI(EmailInfo emailInfo);

        /**
         * 通知用户邮箱信息保存的信息
         * @param msg
         */
        void noticeUserEmailConfigSaveInfo(String msg);
    }
}
