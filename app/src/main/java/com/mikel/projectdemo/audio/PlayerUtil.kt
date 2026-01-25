package com.mikel.projectdemo.audio

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi

object PlayerUtil {
  fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = android.app.NotificationChannel(
        PlayerService.CHANNEL_ID,
        "音频播放",
        android.app.NotificationManager.IMPORTANCE_LOW
      ).apply {
        description = "音频播放通知"
        setSound(null, null)
        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
      }

      val notificationManager = context.getSystemService(
        android.app.NotificationManager::class.java
      )
      notificationManager.createNotificationChannel(channel)
    }
  }

  @OptIn(UnstableApi::class)
  fun playAudio(context: Context, audioPath: String, title: String = "音频") {
    val intent = Intent(context, PlayerService::class.java)
    intent.action = PlayerService.ACTION_PLAY
    intent.putExtra("audio_path", audioPath)
    intent.putExtra("title", title)

    Log.d("AudioPlayer", " play audio path = " + audioPath + ", title = " +  title)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      context.startForegroundService(intent)
    } else {
      context.startService(intent)
    }
  }

  @OptIn(UnstableApi::class)
  fun pauseAudio(context: Context) {
    val intent = Intent(context, PlayerService::class.java)
    intent.action = PlayerService.ACTION_PAUSE
    context.startService(intent)
  }

  @OptIn(UnstableApi::class)
  fun stopAudio(context: Context) {
    val intent = Intent(context, PlayerService::class.java)
    intent.action = PlayerService.ACTION_STOP
    context.startService(intent)
  }

  @OptIn(UnstableApi::class)
  fun playNetworkAudio(context: Context, url: String, title: String = "音频") {
    playAudio(context, url, title)
  }

  @OptIn(UnstableApi::class)
  fun playAssetAudio(context: Context, assetPath: String, title: String = "音频") {
    val assetUri = "asset:///$assetPath"
    playAudio(context, assetUri, title)
  }

  @OptIn(UnstableApi::class)
  fun playRawAudio(context: Context, rawResId: Int, title: String = "音频") {
    // 格式: "android.resource://包名/raw资源ID"
    val packageName = context.packageName
    val rawUri = "android.resource://$packageName/$rawResId"
    playAudio(context, rawUri, title)
  }
}