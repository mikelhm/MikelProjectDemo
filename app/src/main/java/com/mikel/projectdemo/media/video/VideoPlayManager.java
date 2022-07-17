package com.mikel.projectdemo.media.video;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoPlayManager {
    private volatile static VideoPlayManager mInstance = null;
    private Context mContext;
    private SimpleExoPlayer mSimpleExoPlayer;
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

        //创建带宽对象
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        //根据当前宽带来创建选择磁道工厂对象
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        //传入工厂对象，以便创建选择磁道对象
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
        //设置是否循环播放
        mSimpleExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);

        //配置数据源
        DataSource.Factory mediaDataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, "Exo_Video_Play"));
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        //获取代理url
        String proxyUrl = getProxy().getProxyUrl(mCurVideoPlayTask.getVideoUrl());
        Log.d("Video_Play_TAG", "start play orginal url = " + mCurVideoPlayTask.getVideoUrl() + " , proxy url = " + proxyUrl);
        Uri proxyUri = Uri.parse(proxyUrl);

        //配置数据源
        MediaSource mediaSource = new ExtractorMediaSource(proxyUri, mediaDataSourceFactory, extractorsFactory, null, null);
        mSimpleExoPlayer.prepare(mediaSource);

        //隐藏播放工具
        mCurVideoPlayTask.getSimpleExoPlayerView().setUseController(false);
        //设置播放视频的宽高为Fit模式
        mCurVideoPlayTask.getSimpleExoPlayerView().setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        //绑定player和playerView
        mCurVideoPlayTask.getSimpleExoPlayerView().setPlayer(mSimpleExoPlayer);
        mSimpleExoPlayer.setPlayWhenReady(true);
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
        urls.add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        urls.add("https://vfx.mtime.cn/Video/2019/01/15/mp4/190115161611510728_480.mp4");
        urls.add("http://gslb.miaopai.com/stream/oxX3t3Vm5XPHKUeTS-zbXA__.mp4");
        urls.add("http://vjs.zencdn.net/v/oceans.mp4 ");
        return urls;
    }
}
