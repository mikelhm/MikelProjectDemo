package com.mikel.baselib.utils;

import android.content.Context;

public class AppUtil {
    private static Context mApplicationContext;
    public static void setApplicationContext(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    public static Context getApplicationContext() {
        return mApplicationContext;
    }
}
