package com.mikel.projectdemo.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.Method;

public abstract class AppRuntime {

    protected static final String TAG = "AppRuntime";
    private Handler mHandler = new Handler(Looper.getMainLooper());
    protected BaseApplication mApplication;
    public AppRuntime(BaseApplication app) {
        mApplication = app;
    }

    public void initAppRuntime() {

    }

    public Handler getHandler() {
        return mHandler;
    }

    public Application getApplication() {
        return mApplication;
    }
}
