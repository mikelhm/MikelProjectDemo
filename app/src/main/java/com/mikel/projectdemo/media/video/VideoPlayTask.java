package com.mikel.projectdemo.media.video;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public class VideoPlayTask {
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private String mVideoUrl;

    public VideoPlayTask(SimpleExoPlayerView simpleExoPlayerView, String uri) {
        this.mSimpleExoPlayerView = simpleExoPlayerView;
        this.mVideoUrl = uri;
    }

    public SimpleExoPlayerView getSimpleExoPlayerView() {
        return mSimpleExoPlayerView;
    }

    public void setSimpleExoPlayerView(SimpleExoPlayerView mSimpleExoPlayerView) {
        this.mSimpleExoPlayerView = mSimpleExoPlayerView;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.mVideoUrl = videoUrl;
    }
}
