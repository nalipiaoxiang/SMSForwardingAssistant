package com.cpc.smsforwardingassistant.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity基类用于相似的配置
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 设置布局
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initViews();

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        getSupportActionBar().hide();
        setContentView(getLayoutId());
        initViews();
    }

    /**
     * 简化
     * @param id findViewById
     * @param <T>
     * @return
     */
    protected <T extends View> T find(@IdRes int id){
        return findViewById(id);
    }


    /**
     * 吐司
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 异步吐司
     * @param msg
     */
    public void showToastSync(String msg) {
        Looper.prepare();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    /**
     * 跳转页面
     * @param cls
     */
    public void navigateTo(Class cls) {
        Intent in = new Intent(context, cls);
        startActivity(in);
    }
}
