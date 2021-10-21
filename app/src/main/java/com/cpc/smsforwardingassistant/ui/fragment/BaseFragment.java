package com.cpc.smsforwardingassistant.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    private View rootView;

    /**
     * 初始化视图
     */
    protected abstract void initViews();

    /**
     * 设置布局id
     * @return  布局id
     */
    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //解决输入法弹出时布局变形
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        rootView = inflater.inflate(getLayoutId(), container, false);
        initViews();
        return rootView;
    }

    /**
     * 简化
     * @param id findViewById
     * @param <T>
     * @return
     */
    protected <T extends View> T find(@IdRes int id){
        return rootView.findViewById(id);
    }

    /**
     * 获取context
     * @return
     */
    protected Context getCT(){
        return rootView.getContext();
    }

}
