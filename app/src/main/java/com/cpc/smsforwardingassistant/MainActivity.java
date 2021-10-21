package com.cpc.smsforwardingassistant;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.cpc.smsforwardingassistant.ui.activity.BaseActivity;
import com.cpc.smsforwardingassistant.adapter.HomeFragmentsAdapter;
//import com.cpc.smsforwardingassistant.broadcast.SMSReceiver;
import com.cpc.smsforwardingassistant.config.AppConfig;
import com.cpc.smsforwardingassistant.ui.fragment.AboutFragment;
import com.cpc.smsforwardingassistant.ui.fragment.home.HomeFragment;
import com.cpc.smsforwardingassistant.ui.fragment.setting.SettingFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Context context;

    public static final int REQ_CODE_CONTACT = 1;

    private TabLayout myTab;
    private ViewPager2 myViewPager;

    private List<String> tittles = new ArrayList<>();
    private List<Integer> icons = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //取消启动背景图
        setTheme(R.style.Theme_SMSForwardingAssistant);
        context = this;
        super.onCreate(savedInstanceState);
        initData();
        initAdapter();
        initEvent();
        checkSMSPermission(context);
    }

    /**
     * 检查SMS权限
     */
    private void checkSMSPermission(Context context) {
        //判断是否以授权接收短信
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS)) {
            System.out.println("****************未取得接收短信权限");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS},AppConfig.HAVE_RECEIVE_SMS );
            System.out.println("申请接收短信权限");
        }
        //判断是否以授权读取短信
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)) {
            System.out.println("*****************未取得读取短信权限");
        }
        //判断是否以授权接收广播短信
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.BROADCAST_SMS)) {
            System.out.println("*****************未取得接收广播短信权限");
        }

    }



    /**
     * 初始化事件
     */
    private void initEvent() {
        /**
         * Tab布局中间类设置Tab策略
         */
        new TabLayoutMediator(myTab, myViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tittles.get(position));
                tab.setIcon(icons.get(position));
            }
        }).attach();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        //实例适配器
        HomeFragmentsAdapter homeAdapter = new HomeFragmentsAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        //设置适配器
        myViewPager.setAdapter(homeAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tittles.add("首页");
        tittles.add("设置");
        tittles.add("关于");
        icons.add(R.drawable.ic_home);
        icons.add(R.drawable.ic_settings);
        icons.add(R.drawable.ic_about);
        fragments.add(new HomeFragment());
        fragments.add(new SettingFragment());
        fragments.add(new AboutFragment());
    }


    /**
     * 设置布局
     *
     * @return 布局id
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initViews() {
        myViewPager =  find(R.id.viewPager);
        myTab = find(R.id.tabLayout);
        myTab.setTabMode(TabLayout.MODE_FIXED);
    }


}