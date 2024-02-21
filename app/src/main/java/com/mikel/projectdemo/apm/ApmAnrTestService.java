package com.mikel.projectdemo.apm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ApmAnrTestService extends Service {
  @Override
  public void onCreate() {
    super.onCreate();
  }


  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    try {
      Thread.sleep(21000);
    } catch (Exception e) {

    }
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
