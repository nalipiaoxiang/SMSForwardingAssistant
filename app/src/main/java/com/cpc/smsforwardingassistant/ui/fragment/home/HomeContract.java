package com.cpc.smsforwardingassistant.ui.fragment.home;

import android.content.Context;

import com.cpc.smsforwardingassistant.dao.room.ShortMessage;

import java.util.List;

/**
 * 契约
 */
public interface HomeContract {

    /**
     * P层
     */
    interface IHomePresenter{
        void getData();

        /**
         * 检查邮箱状态
         * @param ct
         */
        boolean checkEmailStatus(Context ct);

        /**
         * 开启转发功能
         */
        void openEmailForward();

        /**
         * 关闭转发
         */
        void offEmailForward();

        /**
         * 检查开关状态
         */
        void checkSwhichStatus();
    }

    /**
     * M层
     */
    interface IHomeModel{
        List<ShortMessage> getData(Context context);

        /**
         * 读取配置检查Email状态
         * @param context
         * @return
         */
    }

    /**
     * V层
     */
    interface IHomeView{
        void getDataSuccess(List<ShortMessage> shortMessages);
        void getDataFailed(Throwable throwable);

        /**
         * 打开开关
         */
        void openSwhich();
    }
}
