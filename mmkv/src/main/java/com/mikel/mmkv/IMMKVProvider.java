package com.mikel.mmkv;

import android.content.SharedPreferences;

public interface IMMKVProvider {
  SharedPreferences getSuperSharedPreferences(String fileName, int i);
}
