package com.cpc.smsforwardingassistant.ui.fragment.setting;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cpc.smsforwardingassistant.R;
import com.cpc.smsforwardingassistant.config.AppConfig;
import com.cpc.smsforwardingassistant.config.Info;
import com.cpc.smsforwardingassistant.dao.entity.EmailInfo;
import com.cpc.smsforwardingassistant.ui.fragment.BaseFragment;
import com.cpc.smsforwardingassistant.util.CalibrationUtils;
import com.cpc.smsforwardingassistant.util.EmailUtils;
import com.cpc.smsforwardingassistant.util.LocalConfigOperationUtils;
import com.cpc.smsforwardingassistant.util.TaostUtils;

import cn.hutool.core.util.StrUtil;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener, ISettingContract.ISettingView {

    private static final String TAG = "V层";
    private Context context;
    private View rootView;

    private EditText user;
    private EditText password;
    private EditText receiver;
    private Button saveConfig;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SettingPersistent settingPersistent;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initViews() {
        user = find(R.id.user);
        password = find(R.id.password);
        receiver = find(R.id.receiver);

        saveConfig = find(R.id.saveConfig);
        saveConfig.setOnClickListener(this);

        settingPersistent = new SettingPersistent(this, getCT());
        settingPersistent.loadLocalConfig();

    }

    /**
     * 设置布局id
     *
     * @return 布局id
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_config;
    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        Log.v("设置页面","用户点击保存");
        String emailUser = user.getText().toString();
        String emailPassword = password.getText().toString();
        String emailReceiver = receiver.getText().toString();
        settingPersistent.saveUIConfigToLocal(new EmailInfo(emailReceiver, emailUser, emailPassword));

    }

    /**
     * 读取到的配置信息加载到UI上
     *
     * @param emailInfo
     */
    @Override
    public void loadLocalConfigUpdateUI(EmailInfo emailInfo) {
        user.setText(emailInfo.getUser());
        password.setText(emailInfo.getPassword());
        receiver.setText(emailInfo.getReceiver());
    }

    /**
     * 通知用户邮箱信息保存的信息
     *
     * @param msg
     */
    @Override
    public void noticeUserEmailConfigSaveInfo(String msg) {
        Log.d(TAG, msg);
        Toast.makeText(getCT(), msg, Toast.LENGTH_SHORT).show();
    }
}