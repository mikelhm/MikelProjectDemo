package com.mikel.projectdemo.utils;

import android.util.Log;

import java.io.RandomAccessFile;

public class CPUUtil {

    private static final String TAG = "CPUUtil";
    public static double getProcessCpuRate(int processId) {
        long start = System.currentTimeMillis();
        long cpuTime = 0L;
        long appTime = 0L;
        double cpuRate = 0.0D;
        RandomAccessFile procStatFile = null;
        RandomAccessFile appStatFile = null;

        try {
            procStatFile = new RandomAccessFile("/proc/stat", "r");
            String procStatString = procStatFile.readLine();
            String[] procStats = procStatString.split(" ");
            cpuTime = Long.parseLong(procStats[2]) + Long.parseLong(procStats[3])
                    + Long.parseLong(procStats[4]) + Long.parseLong(procStats[5])
                    + Long.parseLong(procStats[6]) + Long.parseLong(procStats[7])
                    + Long.parseLong(procStats[8]);

        } catch (Exception e) {
            Log.i(TAG, "RandomAccessFile(Process Stat) reader fail, error: e =" + e.toString());
        } finally {
            try {
                if (null != procStatFile) {
                    procStatFile.close();
                }

            } catch (Exception e) {
                Log.i(TAG, "close process reader e =" + e.toString());
            }
        }

        try {
            appStatFile = new RandomAccessFile("/proc/" + processId + "/stat", "r");
            String appStatString = appStatFile.readLine();
            String[] appStats = appStatString.split(" ");
            appTime = Long.parseLong(appStats[13]) + Long.parseLong(appStats[14]);
        } catch (Exception e) {
            Log.i(TAG, "RandomAccessFile(App Stat) reader fail, error: " + e.toString());
        } finally {
            try {
                if (null != appStatFile) {
                    appStatFile.close();
                }
            } catch (Exception e) {
                Log.i(TAG, "close app reader  " + e.toString());
            }
        }

        if (0 != cpuTime) {
            cpuRate = ((double) (appTime) / (double) (cpuTime)) * 100D;
        }

        Log.i(TAG, "getAppCpuRate cost:" + (System.currentTimeMillis() - start) + ",rate:" + cpuRate);
        return cpuRate;
    }
}
