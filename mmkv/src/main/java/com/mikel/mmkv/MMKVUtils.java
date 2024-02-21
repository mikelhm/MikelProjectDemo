package com.mikel.mmkv;

import android.content.Context;
import android.content.SharedPreferences;

public class MMKVUtils {
  public static SharedPreferences getDefaultSharedPreferences(Context context, int mode)  {
    return MMKVProvider.getInstance().getSharedPreferences(context,
            getDefaultSharedPreferencesName(context), mode);
  }

  public static String getDefaultSharedPreferencesName(Context context) {
    return context.getPackageName() + "_preferences";
  }

  public static SharedPreferences getSharedPreferences(Context context, String name, int mode)  {
    return MMKVProvider.getInstance().getSharedPreferences(context, name, mode);
  }
}
