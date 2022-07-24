package com.mikel.apmlib;

import android.app.Application;

import com.tencent.matrix.Matrix;
//import com.tencent.matrix.iocanary.IOCanaryPlugin;
//import com.tencent.matrix.iocanary.config.IOConfig;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;

public class MatrixManager {

    private static volatile MatrixManager INSTANCE;

    private MatrixManager() {
    }

    public static MatrixManager getInstance() {
        if (INSTANCE == null) {
            synchronized (MatrixManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MatrixManager();
                }
            }
        }
        return INSTANCE;
    }

    public void doOnCreate(Application application, String splashActivity) {
        Matrix.Builder builder = new Matrix.Builder(application); // build matrix
        builder.pluginListener(new MatrixPluginListener(application)); // add general pluginListener
        MatrixDynamicConfigImpl matrixDynamicConfig = new MatrixDynamicConfigImpl(); // dynamic config
        boolean fpsEnable = matrixDynamicConfig.isFPSEnable();
        boolean traceEnable = matrixDynamicConfig.isTraceEnable();
        //Trace plugin
        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(matrixDynamicConfig)
                .enableFPS(fpsEnable)//帧率
                .enableEvilMethodTrace(traceEnable)//慢方法
                .enableAnrTrace(traceEnable)//anr
                .enableStartup(traceEnable)//启动速度
                .splashActivities(splashActivity)//首页
                //debug模式
                .isDebug(true)
                //dev环境
                .isDevEnv(false)
                .build();

        TracePlugin tracePlugin = new TracePlugin(traceConfig);
        builder.plugin(tracePlugin);

        // io plugin
//        IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
//                .dynamicConfig(matrixDynamicConfig)
//                .build());
//        builder.plugin(ioCanaryPlugin);

        //init matrix
        Matrix.init(builder.build());
        tracePlugin.start();
//        ioCanaryPlugin.start();
    }
}
