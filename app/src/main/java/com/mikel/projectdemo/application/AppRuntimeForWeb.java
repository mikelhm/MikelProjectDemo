package com.mikel.projectdemo.application;

import android.util.Log;

public class AppRuntimeForWeb extends AppRuntime{

    public AppRuntimeForWeb(BaseApplication app) {
        super(app);
    }

    @Override
    public void initAppRuntime() {
        super.initAppRuntime();
        Log.d(TAG, " web process runtime init");
        //todo web 进程初始化需要做的事情
    }
}
