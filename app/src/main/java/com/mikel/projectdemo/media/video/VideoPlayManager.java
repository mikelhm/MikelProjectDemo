package com.mikel.projectdemo.media.video;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.SimpleExoPlayer;
import androidx.media3.ui.AspectRatioFrameLayout;

import com.danikula.videocache.HttpProxyCacheServer;
//import com.google.android.exoplayer2.DefaultLoadControl;
//import com.google.android.exoplayer2.LoadControl;
//import com.google.android.exoplayer2.MediaItem;
//import com.google.android.exoplayer2.Player;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
//import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
//import com.google.android.exoplayer2.trackselection.TrackSelection;
//import com.google.android.exoplayer2.trackselection.TrackSelector;
//import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
//import com.google.android.exoplayer2.upstream.BandwidthMeter;
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.util.Util;
import com.mikel.projectdemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoPlayManager {
    private volatile static VideoPlayManager mInstance = null;
    private Context mContext;
    private ExoPlayer mSimpleExoPlayer;
    private VideoPlayTask mCurVideoPlayTask;
    /**
     * 双重检测
     * @return
     */
    public static VideoPlayManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (VideoPlayManager.class) {
                if(mInstance == null) {
                    mInstance = new VideoPlayManager(context);
                }
            }
        }
        return mInstance;
    }

    public VideoPlayManager(Context context) {
        this.mContext = context;
    }

    /**
     * 开始播放
     */
    public void startPlay() {
        stopPlay();
        if(mCurVideoPlayTask == null) {
            Log.e("Video_Play_TAG", "start play task is null");
            return;
        }

        mSimpleExoPlayer = new ExoPlayer.Builder(mContext).build();
        // 准备要播放的媒体资源
        MediaItem mediaItem = MediaItem.fromUri(mCurVideoPlayTask.getVideoUrl());
        mSimpleExoPlayer.setMediaItem(mediaItem);

        //隐藏播放工具
        mCurVideoPlayTask.getSimpleExoPlayerView().setUseController(false);
        //设置播放视频的宽高为Fit模式
        mCurVideoPlayTask.getSimpleExoPlayerView().setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        //绑定player和playerView
        mCurVideoPlayTask.getSimpleExoPlayerView().setPlayer(mSimpleExoPlayer);
        mSimpleExoPlayer.setPlayWhenReady(true);

        // 准备播放器
        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.play();
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        if(mSimpleExoPlayer != null) {
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    public void resumePlay() {
        if(mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(true);
        } else {
            startPlay();
        }
    }

    public void pausePlay() {
        if(mSimpleExoPlayer != null) {
           mSimpleExoPlayer.setPlayWhenReady(false);
        }
    }

    /********************************************* VideoCache start ***************************************/
    private HttpProxyCacheServer mHttpProxyCacheServer;
    public HttpProxyCacheServer getProxy() {
        if(mHttpProxyCacheServer == null) {
            mHttpProxyCacheServer = newProxy();
        }
        return mHttpProxyCacheServer;
    }

    private HttpProxyCacheServer newProxy() {
        //缓存大小512M,缓存文件20
        return new HttpProxyCacheServer.Builder(mContext.getApplicationContext())
                .maxCacheSize(512 * 1024 * 1024)
                .maxCacheFilesCount(20)
                .fileNameGenerator(new VideoFileNameGenerator())
                .cacheDirectory(new File(mContext.getFilesDir() + "/videoCache/"))
                .build();
    }
    /********************************************* VideoCache end ***************************************/
    public VideoPlayTask getCurVideoPlayTask() {
        return mCurVideoPlayTask;
    }

    public void setCurVideoPlayTask(VideoPlayTask mCurVideoPlayTask) {
        this.mCurVideoPlayTask = mCurVideoPlayTask;
    }

    /**
     * 构建测试数据
     * @return
     */
    public static List<String> buildTestVideoUrls() {
        List<String> urls = new ArrayList<>();
        urls.add("https://vfx.mtime.cn/Video/2019/01/15/mp4/190115161611510728_480.mp4");
        urls.add("https://v-cdn.zjol.com.cn/276984.mp4");
        urls.add("https://v-cdn.zjol.com.cn/276985.mp4");
        urls.add("https://v-cdn.zjol.com.cn/276986.mp4");
        urls.add("https://v-cdn.zjol.com.cn/276987.mp4");
        urls.add("https://v-cdn.zjol.com.cn/276988.mp4");
        return urls;
    }
}
