package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.utils.LogUtils;
import com.example.myapplication.volley.RequestManager;

/**
 * Created by 123 on 2016/4/13.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        LogUtils.d("MyApplication app");
        super.onCreate();
        init();
    }

    private void init() {
        RequestManager.init(this);
    }
}
