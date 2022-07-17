package com.mikel.projectdemo.application;

import android.app.Application;

import com.mikel.baselib.utils.AppUtil;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.setApplicationContext(getApplicationContext());
    }
}
