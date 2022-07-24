package com.mikel.projectdemo.application;

import android.util.Log;

import com.mikel.apmlib.MatrixManager;

public class AppRuntimeForMain extends AppRuntime{
    public AppRuntimeForMain(BaseApplication app) {
        super(app);
    }

    @Override
    public void initAppRuntime() {
        super.initAppRuntime();
        Log.d(TAG, " main process runtime init");
        //todo 主进程初始化需要做的事情
        MatrixManager.getInstance().doOnCreate(getApplication(),"com.mikel.projectdemo.MainActivity");
    }
}
