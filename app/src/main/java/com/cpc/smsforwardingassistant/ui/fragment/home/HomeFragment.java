package com.cpc.smsforwardingassistant.ui.fragment.home;


import android.os.Bundle;


import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.cpc.smsforwardingassistant.R;
import com.cpc.smsforwardingassistant.adapter.HomeRecycleViewAdapter;
import com.cpc.smsforwardingassistant.dao.room.ShortMessage;
import com.cpc.smsforwardingassistant.ui.fragment.BaseFragment;
import com.cpc.smsforwardingassistant.util.MiuiUtils;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, HomeContract.IHomeView, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private static final String TAG = "首页";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private List<ShortMessage> data = new ArrayList<>();
    //下拉刷新
    private SwipeRefreshLayout homeSwiperefresh;
    private RecyclerView homeRecycleview;
    private HomeRecycleViewAdapter homeRecycleViewAdapter;
    private HomeContract.IHomePresenter homePresenter;
    private SwitchMaterial statusSwitch;
    private Button btnGoToSetting;

    public HomeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        }
        //渲染布局在onCreatView里面,所以调用页面组件只能在onCreateView之内或之后执行
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initViews() {
        statusSwitch = find(R.id.switch_status);
        statusSwitch.setOnCheckedChangeListener(this);
        btnGoToSetting = find(R.id.btn_goto_setting);
        btnGoToSetting.setOnClickListener(this);
        //下拉刷新
        homeSwiperefresh = find(R.id.home_swiperefresh);
        homeSwiperefresh.setOnRefreshListener(this);
        //展示列表
        homeRecycleview = find(R.id.home_recycleview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        homeRecycleview.setLayoutManager(gridLayoutManager);
        homeRecycleViewAdapter = new HomeRecycleViewAdapter(getActivity(), data);
        homeRecycleview.setAdapter(homeRecycleViewAdapter);

        homePresenter = new HomePresenter(this, getCT());
        //初始化获取一次数据
        Log.d(TAG, "初始化加载数据");
        homePresenter.getData();
        //检查总开关状态
        homePresenter.checkSwhichStatus();
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     * 下拉刷新监听
     */
    @Override
    public void onRefresh() {
        Log.d(TAG, "调用下拉刷新");
        homePresenter.getData();
        homeSwiperefresh.setRefreshing(false);
    }

    /**
     * 设置布局id
     *
     * @return 布局id
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void getDataSuccess(List<ShortMessage> shortMessages) {
        homeRecycleViewAdapter.setShortMessage(shortMessages);
    }

    @Override
    public void getDataFailed(Throwable throwable) {
        Toast.makeText(getCT(), "获取数据失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 打开开关
     */
    @Override
    public void openSwhich() {
        statusSwitch.setChecked(true);
    }


    /**
     * Switch开关
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "onCheckedChanged");
        if (isChecked) {
            Toast.makeText(getCT(), "开关打开", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "开关打开" + isChecked);
            boolean emailStatus = homePresenter.checkEmailStatus(getCT());
            Log.d(TAG, "获取到邮箱状态" + emailStatus);
            if (!emailStatus) {
                Log.d(TAG, "邮箱状态为不可用,开关关闭");
                //邮箱状态不转发
                statusSwitch.setChecked(false);
                Toast.makeText(getCT(), "邮箱不可用,请检查配置", Toast.LENGTH_SHORT).show();
            } else {
                //如果邮箱状态正常可以开启转发
                Log.d(TAG, "邮箱状态为可用,开启转发");
                homePresenter.openEmailForward();
            }
        } else {
            Toast.makeText(getCT(), "开关关闭", Toast.LENGTH_SHORT).show();
            //关闭转发
            Log.d(TAG, "开关关闭,关闭转发");
            homePresenter.offEmailForward();
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Toast.makeText(this.getCT(), "去开启读取短信的权限吧,小米是通知类短信", Toast.LENGTH_LONG).show();
        MiuiUtils.getMiuiSettingIntent(this.getActivity());
    }
}