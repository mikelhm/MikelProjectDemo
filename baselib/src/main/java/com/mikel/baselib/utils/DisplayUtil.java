package com.mikel.baselib.utils;

import android.content.Context;

public class DisplayUtil {
    public static int dp2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
