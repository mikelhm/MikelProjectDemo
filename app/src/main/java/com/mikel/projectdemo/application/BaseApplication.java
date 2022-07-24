package com.mikel.projectdemo.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.mikel.baselib.utils.AppUtil;

import java.util.List;

public class BaseApplication extends Application {
    protected static BaseApplication sBaseApplication;
    protected AppRuntime appRuntime;
    protected String processName;

    /**
     * 进程名字
     */
    public final static String MAPP_PROCESS_NAME = "com.mikel.projectdemo";
    public final static String WEB_PROCESS_NAME = "com.mikel.projectdemo:web";
    public final static String VIDEO_PROCESS_NAME = "com.mikel.projectdemo:video";
    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApplication = this;
        AppUtil.setApplicationContext(getApplicationContext());
        //创建不同的进程
        createAppRuntime();
        appRuntime.initAppRuntime();
    }

    protected void createAppRuntime() {
        String processName = getAppProcessName();
        if (WEB_PROCESS_NAME.equals(processName)) {//web进程
            appRuntime = new AppRuntimeForWeb(sBaseApplication);
        } else if(VIDEO_PROCESS_NAME.equals(processName)) {//视频进程
            appRuntime = new AppRuntimeForVideo(sBaseApplication);
        } else {//主进程
            appRuntime = new AppRuntimeForMain(sBaseApplication);
        }
    }

    public String getAppProcessName() {
        if (processName == null) {
            ActivityManager actMgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appList = actMgr
                    .getRunningAppProcesses();
            if (null != appList) {
                for (ActivityManager.RunningAppProcessInfo info : appList) {
                    if (info.pid == android.os.Process.myPid()) {
                        processName = info.processName;
                        break;
                    }
                }
            }
            if (processName == null) {
                processName = getApplicationInfo().processName;
            }
            if (processName == null) {
                processName = MAPP_PROCESS_NAME;
            }
        }
        return processName;
    }

}
