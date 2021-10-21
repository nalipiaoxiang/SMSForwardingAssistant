package com.cpc.smsforwardingassistant.ui.fragment.setting;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cpc.smsforwardingassistant.config.AppConfig;
import com.cpc.smsforwardingassistant.config.Info;
import com.cpc.smsforwardingassistant.constant.Conf;
import com.cpc.smsforwardingassistant.dao.entity.EmailInfo;
import com.cpc.smsforwardingassistant.dao.room.ShortMessage;
import com.cpc.smsforwardingassistant.ui.fragment.home.HomeContract;
import com.cpc.smsforwardingassistant.ui.fragment.home.HomeModel;
import com.cpc.smsforwardingassistant.util.CalibrationUtils;
import com.cpc.smsforwardingassistant.util.DataUtils;
import com.cpc.smsforwardingassistant.util.EmailUtils;
import com.cpc.smsforwardingassistant.util.LocalConfigOperationUtils;
import com.cpc.smsforwardingassistant.util.TaostUtils;

import java.util.Date;

import cn.hutool.core.util.StrUtil;

public class SettingPersistent implements ISettingContract.ISettingPresenter {

    private static final String TAG = "设置页面";
    private ISettingContract.ISettingView settingView;
    private Context context;
    private SettingModel settingModel;

    public SettingPersistent(ISettingContract.ISettingView settingView, Context context) {
        this.settingView = settingView;
        this.context = context;
        settingModel = new SettingModel(this,context);
    }

    /**
     * 加载本地配置
     */
    @Override
    public void loadLocalConfig() {
        EmailInfo emailInfo = settingModel.readLocalConfig(context);
        settingView.loadLocalConfigUpdateUI(emailInfo);
    }

    @Override
    public void saveUIConfigToLocal(EmailInfo emailInfo) {
        //默认邮箱不可用,只有通过检查才可用
        emailInfo.setEmailStatus(false);
        String emailUser = emailInfo.getUser();
        String emailPassword = emailInfo.getPassword();
        String emailReceiver = emailInfo.getReceiver();
        //保存前需要校验和验证邮箱
        //校验字符串
        boolean isEmail = true;
        if (StrUtil.isBlank(emailUser)) {
            settingView.noticeUserEmailConfigSaveInfo(Info.T_002);
            return;
        }
        emailUser = emailUser.trim();
        isEmail = CalibrationUtils.isEmail(emailUser);
        if (!isEmail) {
            settingView.noticeUserEmailConfigSaveInfo(Info.T_003);
            return;
        }
        if (StrUtil.isBlank(emailPassword)) {
            settingView.noticeUserEmailConfigSaveInfo(Info.T_004);
            return;
        }
        emailPassword = emailPassword.trim();
        if (StrUtil.isBlank(emailReceiver)) {
            settingView.noticeUserEmailConfigSaveInfo(Info.T_005);
            return;
        }
        emailReceiver = emailReceiver.trim();
        isEmail = CalibrationUtils.isEmail(emailReceiver);
        if (!isEmail) {
            settingView.noticeUserEmailConfigSaveInfo(Info.T_006);
            return;
        }
        Log.v("设置页面", "获取到emailUser:" + emailUser + ",emailPassword:" + emailPassword + ",emailReceiver:" + emailReceiver);
        emailInfo.setSubject(Conf.TITLE);
        emailInfo.setContent(Conf.CONTENT_DEFAULT);
        //发送校验邮件
        boolean isSuccess = settingModel.sendTestEmail(emailInfo, context);
        if (isSuccess) {
            //测试通过保存邮箱数据
            emailInfo.setEmailStatus(true);
            settingModel.writeConfigToLocal(context, emailInfo);
            //推送信息入库
            settingModel.insertPushInfo(new ShortMessage(new Date().getTime(), Conf.EMAIL_SUCCESS,"发送测试邮件成功"));
            settingView.noticeUserEmailConfigSaveInfo("保存成功");

        } else {
            settingModel.insertPushInfo(new ShortMessage(new Date().getTime(), Conf.EMAIL_FAILED,"发送测试邮件失败请检查配置"));
            settingView.noticeUserEmailConfigSaveInfo("邮件发送失败,请检查设置");
        }
    }


}
