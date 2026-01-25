package com.mikel.projectdemo.audio

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.media3.common.*
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.*
import com.mikel.projectdemo.MainActivity

@UnstableApi
class PlayerService : MediaSessionService() {

  private var mediaSession: MediaSession? = null
  private lateinit var player: ExoPlayer

  override fun onCreate() {
    super.onCreate()
    player = ExoPlayer.Builder(this)
      .build()
      .apply {
        repeatMode = Player.REPEAT_MODE_OFF
        playWhenReady = true
      }

    // 创建媒体会话
    mediaSession = MediaSession.Builder(this, player).build()

    // 创建通知
    startForeground(NOTIFICATION_ID, createNotification())

    Log.d("AudioPlayer", "Player Service on create ")
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    super.onStartCommand(intent, flags, startId)
    Log.d("AudioPlayer", "onStartCommand play " + intent)
    intent?.action?.let { action ->
      when (action) {
        ACTION_PLAY -> {
          val audioPath = intent.getStringExtra("audio_path")
          val title = intent.getStringExtra("title") ?: "音频"
          if (audioPath != null) {
            playAudio(audioPath, title)
          }
        }
        ACTION_PAUSE -> pauseAudio()
        ACTION_STOP -> stopAudio()
      }
    }
    return START_STICKY
  }

  override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
    return mediaSession
  }

  override fun onDestroy() {
    player.release()
    mediaSession?.release()
    super.onDestroy()
  }

  fun playAudio(url: String, title: String = "音频") {
    val mediaItem = MediaItem.Builder()
      .setUri(url)
      .setMediaMetadata(
        MediaMetadata.Builder()
          .setTitle(title)
          .build()
      )
      .build()

    player.setMediaItem(mediaItem)
    player.prepare()
    player.play()

    // 更新通知
    updateNotification(title, "正在播放")
  }

  private fun pauseAudio() {
    if (player.isPlaying) {
      player.pause()
      updateNotification("音频播放器", "已暂停")
    }
  }

  private fun stopAudio() {
    player.stop()
    stopForeground(true)
    stopSelf()
  }

  private fun createNotification(): Notification {
    val intent = Intent(this, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
      this,
      0,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    return NotificationCompat.Builder(this, CHANNEL_ID)
      .setContentTitle("音频播放器")
      .setContentText("准备播放")
      .setSmallIcon(android.R.drawable.ic_media_play)
      .setContentIntent(pendingIntent)
      .setOngoing(true)
      .build()
  }

  private fun updateNotification(title: String, text: String) {
    val intent = Intent(this, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
      this,
      0,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(this, CHANNEL_ID)
      .setContentTitle(title)
      .setContentText(text)
      .setSmallIcon(android.R.drawable.ic_media_play)
      .setContentIntent(pendingIntent)
      .setOngoing(true)
      .build()

    // 更新通知
    val notificationManager = getSystemService(android.app.NotificationManager::class.java)
    notificationManager.notify(NOTIFICATION_ID, notification)
  }

  companion object {
    const val NOTIFICATION_ID = 1001
    const val CHANNEL_ID = "audio_player_channel"
    const val ACTION_PLAY = "com.mikel.projectdemo.ACTION_PLAY"
    const val ACTION_PAUSE = "com.mikel.projectdemo.ACTION_PAUSE"
    const val ACTION_STOP = "com.mikel.projectdemo.ACTION_STOP"
  }
}