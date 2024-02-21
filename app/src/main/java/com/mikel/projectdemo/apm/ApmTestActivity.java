package com.mikel.projectdemo.apm;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.mikel.baselib.manager.ThreadManager;
import com.mikel.projectdemo.R;
import com.mikel.projectdemo.utils.MemoryUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ApmTestActivity extends Activity {

    private final static int MSG_READ_FILE_MEM =1000;
    private final static int MSG_RUNTIME_MEM = MSG_READ_FILE_MEM + 1;
    private final static int MSG_AM_MEM = MSG_RUNTIME_MEM + 1;
    private final static int MSG_PROCESS_CPU_RATE = MSG_AM_MEM + 1;
    private final static int MSG_GET_MEM = MSG_PROCESS_CPU_RATE + 1;
    private TextView deviceTotalMemTxt;
    private TextView deviceAvailMemTxt;
    private Button testIncreaseMemBtn;
    private TextView processCpuRateTxt;
    private TextView readFileMemTxt;
    private TextView runtimeMemTxt;
    private TextView amMemTxt;
    private List<String> memTestList = new ArrayList<>();

    private Handler mainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_READ_FILE_MEM:
                    long readFileMem = (Long)msg.obj;
                    readFileMemTxt.setText("readFile获取运行时内存（RSS)：" + MemoryUtil.getPrintSize(readFileMem));
                    break;

                case MSG_RUNTIME_MEM:
                    long runtimeMem = (Long)msg.obj;
                    runtimeMemTxt.setText("Runtime获取运行时内存(JVM消耗)：" + MemoryUtil.getPrintSize(runtimeMem));
                    break;

                case MSG_AM_MEM:
                    int amMem = (Integer)msg.obj;//KB单位
                    amMemTxt.setText("ActivityManager获取运行时内存(PSS)：" + amMem/1024 + "MB");
                    break;

                case  MSG_PROCESS_CPU_RATE:
                    double cpuRate = (Double) msg.obj;
                    processCpuRateTxt.setText("进程CPU rate : " + cpuRate);
                    break;

                case MSG_GET_MEM:
                    getProcessRunningMem();
                    break;

                default:
                    break;
            }
        }
    };

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ApmTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apm);
        initUI();
        getProcessRunningMem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ANR_TEST_BR");
        registerReceiver(anrTestReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(anrTestReceiver);
    }

    private void initUI() {
        deviceTotalMemTxt = findViewById(R.id.device_total_mem);
        deviceTotalMemTxt.setText("设备总内存: " + MemoryUtil.getPrintSize(getDeviceTotalMem()));
        deviceAvailMemTxt = findViewById(R.id.device_free_mem);
        deviceAvailMemTxt.setText("设备可用内存：" + MemoryUtil.getPrintSize(getDeviceAvailMem()));
        processCpuRateTxt = findViewById(R.id.process_cpu_rate);
        testIncreaseMemBtn = findViewById(R.id.btn_increase_mem);
        testIncreaseMemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10000; i++) {
                    memTestList.add("cur time = " + System.currentTimeMillis());
                }

            }
        });
        readFileMemTxt = findViewById(R.id.read_file_process_mem);
        runtimeMemTxt = findViewById(R.id.runtime_process_mem);
        amMemTxt = findViewById(R.id.am_process_mem);

        findViewById(R.id.btn_evil_method).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }

            }
        });

        findViewById(R.id.btn_evil_touch_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApmTestActivity.this, ApmTouchEventTestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_evil_idle_handler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                    @Override
                    public boolean queueIdle() {
                        try {
                            Thread.sleep(5000);
                        } catch (Exception e) {

                        }
                        return false;
                    }
                });
            }
        });

        findViewById(R.id.btn_anr_test_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {

                }
            }
        });

        findViewById(R.id.btn_anr_test_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {

                }
            }
        });

        findViewById(R.id.btn_anr_test_br).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("ANR_TEST_BR");
                sendBroadcast(intent);
            }
        });

        findViewById(R.id.btn_anr_test_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApmTestActivity.this, ApmAnrTestService.class);
                startService(intent);
            }
        });

        findViewById(R.id.btn_io_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream fileOutputStream = null;
                try {
                    File file = new File(ApmTestActivity.this.getFilesDir().getAbsolutePath() +
                            File.separator + "testIO.txt");
                    if(!file.exists()) {
                        file.createNewFile();
                    }

                    String text = "hello world.";
                    fileOutputStream = new FileOutputStream(file, true);
                    for(int i = 0; i<1000; i++) {
                        fileOutputStream.write(text.getBytes());
                    }

                } catch (Exception e) {

                } finally {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e) {

                    }

                }

            }
        });
    }

    private long getDeviceTotalMem() {
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(memInfo);
        return memInfo.totalMem;
    }

    private long getDeviceAvailMem() {
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(memInfo);
        return memInfo.availMem;
    }

    public static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    private void getProcessRunningMem() {
        ThreadManager.getInstence().postTask(new Runnable() {
            @Override
            public void run() {
                //read file方式 获取运行时内存
                long memSize = MemoryUtil.getProcessRealMemory();
                Message readFileMemMsg = mainHandler.obtainMessage();
                readFileMemMsg.what = MSG_READ_FILE_MEM;
                readFileMemMsg.obj = memSize;
                mainHandler.sendMessage(readFileMemMsg);

                //Runtime方式获取运行时内存
                long totalRuntimeMem = Runtime.getRuntime().totalMemory();
                long freeRuntimeMem = Runtime.getRuntime().freeMemory();
                long useRuntimeMem = totalRuntimeMem - freeRuntimeMem;
                Message runtimeMemMsg = mainHandler.obtainMessage();
                runtimeMemMsg.what = MSG_RUNTIME_MEM;
                runtimeMemMsg.obj = useRuntimeMem;
                mainHandler.sendMessage(runtimeMemMsg);

                //ActivityManager方式获取运行时内存
                ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                final Debug.MemoryInfo[] memInfo = activityManager.getProcessMemoryInfo(new int[]{android.os.Process.myPid()});
                if (memInfo.length > 0) {
                    final int totalPss = memInfo[0].getTotalPss();
                    Message amMemMsg = mainHandler.obtainMessage();
                    amMemMsg.what = MSG_AM_MEM;
                    amMemMsg.obj = totalPss;
                    mainHandler.sendMessage(amMemMsg);
                }

//                double  cpuRate = CPUUtil.getProcessCpuRate(android.os.Process.myPid());
//                Message cpuRateMsg = mainHandler.obtainMessage();
//                cpuRateMsg.what = MSG_PROCESS_CPU_RATE;
//                cpuRateMsg.obj = cpuRate;
//                mainHandler.sendMessage(cpuRateMsg);

                Message getMemMsg = mainHandler.obtainMessage();
                getMemMsg.what = MSG_GET_MEM;
                mainHandler.sendMessageDelayed(getMemMsg, 1000);

            }
        });
    }


    private ANRTestReceiver anrTestReceiver = new ANRTestReceiver();
    public class ANRTestReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Thread.sleep(12000);
            } catch (Exception e) {

            }
        }
    }
}
