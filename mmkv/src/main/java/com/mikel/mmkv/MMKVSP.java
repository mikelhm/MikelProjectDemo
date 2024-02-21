package com.mikel.mmkv;

import android.content.Context;
import android.content.SharedPreferences;

import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MMKVSP implements SharedPreferences.Editor, SharedPreferences {

  private MMKV mmkv;
  private Context mContext;
  private boolean isMultiprocess;
  private ArrayList<OnSharedPreferenceChangeListener> listeners;
  private final Object listenerLock = new Object();
  public static void initialize(Context context) {
    MMKV.initialize(context);
  }

  public MMKVSP(Context context, String name, boolean multiProcess) {
    mContext = context;
    isMultiprocess = multiProcess;
    mmkv = MMKV.mmkvWithID(name,
            (isMultiprocess? Context.MODE_MULTI_PROCESS :  Context.MODE_PRIVATE));
    listeners = new ArrayList<>();
    importFromAndroidSp(name);
  }

  private void importFromAndroidSp(String fileName) {
    MMKV migrateSpRecord = MMKV.mmkvWithID("migrate_sp_record");
    boolean isImported = migrateSpRecord.getBoolean(fileName, false);
    SharedPreferences sharedPreferences = null;
    if (!isImported) {
      if(mContext instanceof IMMKVProvider) {
        sharedPreferences = ((IMMKVProvider)mContext).getSuperSharedPreferences(fileName,
                (isMultiprocess? Context.MODE_MULTI_PROCESS :  Context.MODE_PRIVATE));
      } else {
        sharedPreferences = mContext.getSharedPreferences(fileName,
                (isMultiprocess? Context.MODE_MULTI_PROCESS :  Context.MODE_PRIVATE));
      }
      Set<? extends Map.Entry<String, ?>> entrySet = sharedPreferences.getAll().entrySet();
      for(Map.Entry<String, ?> entry : entrySet) {
        String key = entry.getKey();
        if (entry.getValue() instanceof Set<?>) {
          mmkv.putString(key + "#type", "Set<String>");
        } else if (entry.getValue() instanceof String) {
          mmkv.putString(key + "#type", String.class.getName());
        } else if (entry.getValue() instanceof Integer) {
          mmkv.putString(key + "#type", Integer.class.getName());
        } else if (entry.getValue() instanceof Boolean) {
          mmkv.putString(key + "#type", Boolean.class.getName());
        } else if (entry.getValue() instanceof Float) {
          mmkv.putString(key + "#type", Float.class.getName());
        } else if (entry.getValue() instanceof Long) {
          mmkv.putString(key + "#type", Long.class.getName());
        }
      }
      mmkv.importFromSharedPreferences(sharedPreferences);
      migrateSpRecord.putBoolean(fileName, true);
      sharedPreferences.edit().clear().apply();
    }
  }

  @Override
  public SharedPreferences.Editor edit() {
    return this;
  }

  @Override
  public boolean contains(String key) {
    return mmkv.containsKey(key);
  }

  @Override
  public boolean getBoolean(String key, boolean defValue) {
    return mmkv.getBoolean(key, defValue);
  }

  @Override
  public int getInt(String key, int defValue) {
    return mmkv.getInt(key, defValue);
  }

  @Override
  public long getLong(String key, long defValue) {
    return mmkv.getLong(key, defValue);
  }

  @Override
  public float getFloat(String key, float defValue) {
    return mmkv.getFloat(key, defValue);
  }

  @Override
  public String getString(String key, String defValue) {
    return mmkv.getString(key, defValue);
  }

  @Override
  public Set<String> getStringSet(String key, Set<String> defValues) {
    return mmkv.getStringSet(key, defValues);
  }

  @Override
  public SharedPreferences.Editor putLong(String key, long value) {
    if (mmkv.containsKey(key)) {
      long storageValue = mmkv.getLong(key, 0L);
      if (storageValue == value)
        return this;
    }
    mmkv.putLong(key, value);
    mmkv.putString(key + "#type", Long.class.getName());
    notifyListeners(key);
    return this;
  }

  @Override
  public SharedPreferences.Editor putInt(String key, int value){
    if (mmkv.containsKey(key)) {
      int storageValue = mmkv.getInt(key, 0);
      if (storageValue == value)
        return this;
    }
    mmkv.putInt(key, value);
    mmkv.putString(key + "#type", Integer.class.getName());
    notifyListeners(key);
    return this;
  }

  @Override
  public SharedPreferences.Editor putBoolean(String key, boolean value) {
    if (mmkv.containsKey(key)) {
      boolean storageValue = mmkv.getBoolean(key, false);
      if (storageValue == value)
        return this;
    }
    mmkv.putBoolean(key, value);
    mmkv.putString(key + "#type", Boolean.class.getName());
    notifyListeners(key);
    return this;
  }

  @Override
  public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
    if (mmkv.containsKey(key)) {
      Set<String> storageValue = mmkv.getStringSet(key, new HashSet<>());
      if (storageValue == values)
        return this;
    }
    mmkv.putStringSet(key, (values != null? values : new HashSet<>()));
    mmkv.putString(key + "#type", "Set<String>");
    notifyListeners(key);
    return this;
  }

  @Override
  public SharedPreferences.Editor putFloat(String key, float value) {
    if (mmkv.containsKey(key)) {
      float storageValue = mmkv.getFloat(key, 0f);
      if (storageValue == value)
        return this;
    }
    mmkv.putFloat(key, value);
    mmkv.putString(key + "#type", Float.class.getName());
    notifyListeners(key);
    return this;
  }

  @Override
  public SharedPreferences.Editor putString(String key, String value) {
    if (mmkv.containsKey(key)) {
      String storageValue = mmkv.getString(key, "");
      if (storageValue.equals(value))
        return this;
    }
    mmkv.putString(key, value);
    mmkv.putString(key + "#type", String.class.getName());
    notifyListeners(key);
    return this;
  }

  @Override
  public SharedPreferences.Editor remove(String key) {
    mmkv.remove(key);
    mmkv.remove(key + "#type");
    notifyListeners(key);
    return this;
  }

  @Override
  public SharedPreferences.Editor clear() {
    mmkv.clear();
    return this;
  }

  @Override
  public void apply() {
    mmkv.apply();
  }

  @Override
  public boolean commit() {
    return mmkv.commit();
  }

  @Override
  public Map<String, ?> getAll() {
    String[] keys = mmkv.allKeys();
    HashMap<String, Object> hashMap = new HashMap<>();
    for (String key: keys) {
      String value = mmkv.getString(key + "#type", "");
      if (String.class.getName().equals(value)) {
        hashMap.put(key, mmkv.getString(key,""));
      } else if (Float.class.getName().equals(value)) {
        hashMap.put(key, mmkv.getFloat(key,0f));
      } else if (Integer.class.getName().equals(value)) {
        hashMap.put(key, mmkv.getInt(key,0));
      } else if (Boolean.class.getName().equals(value)) {
        hashMap.put(key, mmkv.getBoolean(key,false));
      } else if (Long.class.getName().equals(value)) {
        hashMap.put(key, mmkv.getLong(key,0L));
      } else if ("Set<String>".equals(value)) {
        hashMap.put(key, mmkv.getStringSet(key, new HashSet<>()));
      }
    }
    return hashMap;
  }

  private void notifyListeners(String key) {
    ArrayList<OnSharedPreferenceChangeListener> copyListeners;
    synchronized(listenerLock) {
      copyListeners = new ArrayList<>(listeners);
    }
    for(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener : copyListeners) {
      onSharedPreferenceChangeListener.onSharedPreferenceChanged(this, key);
    }
  }

  @Override
  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
    synchronized(listenerLock) {
      listeners.add(listener);
    }
  }

  @Override
  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
    synchronized(listenerLock) {
      listeners.remove(listener);
    }
  }
}
