package com.mikel.projectdemo.media.video

import android.content.Context
import androidx.media3.datasource.cache.CacheEvictor
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.database.StandaloneDatabaseProvider
import java.io.File

object VideoCacheManager {
  private var cache: SimpleCache? = null
  private var databaseProvider: StandaloneDatabaseProvider? = null

  @Synchronized
  fun getCache(context: Context): SimpleCache {
    if (cache == null) {
      val cacheDir = File(context.externalCacheDir, "media3_video_cache")
      if (!cacheDir.exists()) {
        cacheDir.mkdirs()
      }

      // 使用LRU策略，最大缓存512MB
      val cacheEvictor: CacheEvictor = LeastRecentlyUsedCacheEvictor(512 * 1024 * 1024)
      databaseProvider = StandaloneDatabaseProvider(context)

      cache = SimpleCache(
        cacheDir,
        cacheEvictor,
        databaseProvider!!
      )
    }
    return cache!!
  }

  @Synchronized
  fun releaseCache() {
    cache?.release()
    cache = null
  }

  // 获取缓存大小（字节）
  fun getCacheSize(): Long {
    return cache?.cacheSpace ?: 0
  }

  // 清空缓存
  fun clearCache() {
    cache?.apply {
      val keys = keys
      for (key in keys) {
        removeResource(key)
      }
    }
  }
}