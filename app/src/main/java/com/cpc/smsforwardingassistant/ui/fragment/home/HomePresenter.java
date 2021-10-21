package com.cpc.smsforwardingassistant.ui.fragment.home;

import android.content.Context;
import android.util.Log;

import com.cpc.smsforwardingassistant.dao.entity.EmailInfo;
import com.cpc.smsforwardingassistant.dao.room.ShortMessage;
import com.cpc.smsforwardingassistant.ui.fragment.setting.SettingModel;

import java.io.Console;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;

/**
 * 注意SettingModel settingModel = new SettingModel(context);
 * 这里的不能调用setting的P和V层的功能
 */
public class HomePresenter implements HomeContract.IHomePresenter {

    private static final String TAG = "P层";
    private HomeContract.IHomeView homeView;
    private HomeContract.IHomeModel homeModel;
    private Context context;

    public HomePresenter(HomeContract.IHomeView homeView, Context context) {
        this.context = context;
        this.homeView = homeView;
        homeModel = new HomeModel();
    }


    @Override
    public void getData() {
        Log.d(TAG, "执行更新数据");
        try {
            List<ShortMessage> shortMessages = homeModel.getData(context);
            Log.d(TAG, "从M层获取到数据" + shortMessages.toString());
            Log.d(TAG, "设置数据到v层");
            homeView.getDataSuccess(shortMessages);
        } catch (Exception e) {
            e.printStackTrace();
            homeView.getDataFailed(e);
        }
    }

    /**
     * 检查邮箱状态
     *
     * @param context
     */
    @Override
    public boolean checkEmailStatus(Context context) {
        SettingModel settingModel = new SettingModel(context);
        EmailInfo emailInfo = settingModel.readLocalConfig(context);
        if (ObjectUtil.isNull(emailInfo)) {
            return false;
        }
        return emailInfo.isEmailStatus();
    }

    /**
     * 开启转发功能
     */
    @Override
    public void openEmailForward() {
        SettingModel settingModel = new SettingModel(context);
        EmailInfo emailInfo = settingModel.readLocalConfig(context);
        Log.d(TAG, "准备开启转发:当前状态:" + emailInfo.isForward());
        emailInfo.setForward(true);
        settingModel.writeConfigToLocal(context, emailInfo);
        Log.d(TAG, "已经开启转发:当前状态:" + emailInfo.isForward());
    }

    /**
     * 关闭转发
     */
    @Override
    public void offEmailForward() {
        SettingModel settingModel = new SettingModel(context);
        EmailInfo emailInfo = settingModel.readLocalConfig(context);
        Log.d(TAG, "准备开启转发:当前状态:" + emailInfo.isForward());
        emailInfo.setForward(false);
        settingModel.writeConfigToLocal(context, emailInfo);
        Log.d(TAG, "已经开启转发:当前状态:" + emailInfo.isForward());
    }

    /**
     * 检查开关状态
     */
    @Override
    public void checkSwhichStatus() {
        SettingModel settingModel = new SettingModel(context);
        EmailInfo emailInfo = settingModel.readLocalConfig(context);
        boolean emailStatus = emailInfo.isEmailStatus();
        boolean forward = emailInfo.isForward();
        Log.d(TAG, "从配置中检查开关状态:,emailStatus:" + emailStatus + ",forward:" + forward);
        if (emailStatus) {
            if (forward) {
                //邮箱状态正常,转发是开启的就通知V层开启开关
                Log.d(TAG, "邮箱状态正常,转发开启,开关将自动打开");
                homeView.openSwhich();
            }
        }
    }

}
