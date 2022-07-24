package com.mikel.projectdemo.application;

import android.util.Log;

public class AppRuntimeForVideo extends AppRuntime {

    public AppRuntimeForVideo(BaseApplication app) {
        super(app);
    }

    @Override
    public void initAppRuntime() {
        super.initAppRuntime();
        Log.d(TAG, " video process runtime init");
    }
}