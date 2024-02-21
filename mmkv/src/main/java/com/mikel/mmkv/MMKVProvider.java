package com.mikel.mmkv;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.ConcurrentHashMap;

public class MMKVProvider {
  private static volatile MMKVProvider INSTANCE;
  public static MMKVProvider getInstance() {
    if (INSTANCE == null) {
      synchronized (MMKVProvider.class) {
        if (INSTANCE == null) {
          INSTANCE = new MMKVProvider();
        }
      }
    }
    return INSTANCE;
  }

  private ConcurrentHashMap<String, MMKVSP> instanceMap = new ConcurrentHashMap<>();

  public SharedPreferences getSharedPreferences(Context context, String name, int mode) {
    return getMMKVPreferences(context, name, mode);
  }


  private MMKVSP getMMKVPreferences(Context context, String name,  int mode) {
    MMKVSP mmkvPreferences = instanceMap.get(name);
    if (mmkvPreferences != null) {
      return mmkvPreferences;
    }

    mmkvPreferences = new MMKVSP(context, name, mode == Context.MODE_MULTI_PROCESS);
    instanceMap.put(name, mmkvPreferences);
    return mmkvPreferences;
  }
}

